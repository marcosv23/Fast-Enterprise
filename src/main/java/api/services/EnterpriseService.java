package api.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import api.dto.CreateEnterpriseDTO;
import api.dto.ExchangeEnterpriseDTO;
import api.exceptions.ApiException;
import api.external.response.EnterpriseResponse;
import api.model.domain.Enterprise;
import api.repositories.EnterpriseRepository;

@Service
public class EnterpriseService {

	@Autowired
	private EnterpriseRepository repo;

	@Autowired
	private CNPJService cnpjService;

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	public List<Enterprise> findAll() {

		return repo.findAll();
	}

	public Enterprise findById(Long id) {

		var enterprise = repo.findById(id);

		return enterprise.orElse(null);
	}

	public Enterprise create(Enterprise e) {

		var enterprise = repo.findBycnpj(e.getCnpj());
		System.out.println(enterprise);

		return repo.save(e);

	}

	public void delete(Long id, Enterprise e) {
		e.setDeletedAt(OffsetDateTime.now());
		repo.save(e);
	}

	public Enterprise update(Long id, Enterprise e) {
		e.setId(id);
		return repo.save(e);
	}

	public Enterprise exchange(Long id, Enterprise e) {

		e.setId(id);
		return repo.saveAndFlush(e);

	}

	public Enterprise fillOutWithAPIData(CreateEnterpriseDTO enterpriseDTO) throws ParseException {

		EnterpriseResponse apiEnterprise = cnpjService.findCNPJ(enterpriseDTO.getCnpj());
		var user = userService.findById(enterpriseDTO.getUserId());

		Enterprise enterprise = new Enterprise();
		enterprise.setUser(user);
		enterprise.setCompanyEmail(apiEnterprise.getEmail());
		enterprise.setCity(apiEnterprise.getMunicipio());
		enterprise.setCnpj(enterpriseDTO.getCnpj());
		enterprise.setSizeCompany(apiEnterprise.getPorte());
		Date formattedFoundation = formatter.parse(apiEnterprise.getData_situacao());
		enterprise.setFoundation(formattedFoundation);
		enterprise.setPhone(apiEnterprise.getTelefone());
		enterprise.setEnterpriseName(apiEnterprise.getNome());
		enterprise.setSituation(apiEnterprise.getSituacao());
		enterprise.setNeighborhood(apiEnterprise.getBairro());
		enterprise.setLocalNumber(apiEnterprise.getNumero());
		enterprise.setStreet(apiEnterprise.getLogradouro());

		return enterprise;
	}

	public Enterprise findBycnpj(String cnpj) {
		var enterprise = repo.findBycnpj(cnpj);
		return enterprise.orElse(null);

	}

	public Enterprise findByName(String keyword) {
		var enterprise = repo.findByEnterpriseName(keyword);
		return enterprise.orElse(null);

	}

	public List<Enterprise> findByEnterpriseNameContaining(String keyword) {
		var enterprises = repo.findByEnterpriseNameContaining(keyword);
		return enterprises.orElse(null);
	}

	public List<Enterprise> findByUserId(Long user_id) {
		var enterprises = repo.findByUserId(user_id);
		return enterprises.orElse(null);
	}

	public Enterprise parseDTOToEntity(ExchangeEnterpriseDTO enterprise) {
		return modelMapper.map(enterprise, Enterprise.class);
	}
	
	public void enterpriseExists(Long id) {
		var enterpriseExists =this.repo.findById(id)
				.orElseThrow(()-> new ApiException(" Empresa não existe no cadastro!"));
	}

	public void validatingUpdate(ExchangeEnterpriseDTO enterpriseDTO) {
		/**
		 * Into Put and Patch Method will allowed updates with new email and cnpj
		 * through checking that these fields don't belong to any enterprise
		 */

		var companyEmailAlreadyExists = this.repo.findByCompanyEmail(enterpriseDTO.getCompanyEmail());
		var cnpjAlreadyExists = this.repo.findBycnpj(enterpriseDTO.getCnpj());

		if (companyEmailAlreadyExists.isPresent()) {
			if (companyEmailAlreadyExists.get().getCompanyEmail().equals(enterpriseDTO.getCompanyEmail())) {
				throw new ApiException("Email pertence a outra empresa!");
			}

		}

		if (cnpjAlreadyExists.isPresent()) {
			if (cnpjAlreadyExists.get().getCnpj().equals(enterpriseDTO.getCnpj())) {
				throw new ApiException("Esse CNPJ pertence a outra empresa!");
			}

		}

	}
	
	public void validatingUpdate(Enterprise enterprise) {
		/**
		 * Into Put and Patch Method will allowed updates with new email and cnpj
		 * through checking that these fields don't belong to any enterprise
		 */

		var companyEmailAlreadyExists = this.repo.findByCompanyEmail(enterprise.getCompanyEmail());
		var cnpjAlreadyExists = this.repo.findBycnpj(enterprise.getCnpj());

		if (companyEmailAlreadyExists.isPresent()) {
			if (companyEmailAlreadyExists.get().getCompanyEmail() != enterprise.getCompanyEmail()) {
				throw new ApiException("Email pertence a outra empresa!");
			}

		}

		if (cnpjAlreadyExists.isPresent()) {
			if (cnpjAlreadyExists.get().getCnpj() != enterprise.getCnpj()) {
				throw new ApiException("Esse CNPJ pertence a outra empresa!");
			}

		}

	}

}
