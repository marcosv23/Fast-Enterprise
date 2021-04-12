package api.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.dto.CreateEnterpriseDTO;
import api.dto.ExchangeEnterpriseDTO;
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
	
	SimpleDateFormat  formatter = new SimpleDateFormat("dd/MM/yyyy");
	
	
	public List<Enterprise>findAll() {
		
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
		 var user= userService.findById(enterpriseDTO.getUserId());
		 
		
		 Enterprise  enterprise = new Enterprise(); 
		 enterprise.setUser(user);
		 enterprise.setCompanyEmail(apiEnterprise.getEmail());
		 enterprise.setCity(apiEnterprise.getMunicipio());
		 enterprise.setCnpj(enterpriseDTO.getCnpj());
		 enterprise.setSizeCompany(apiEnterprise.getPorte());
		 Date formattedFoundation = formatter.parse(apiEnterprise.getData_situacao());
		 enterprise.setFoundation(formattedFoundation); 
		 //enterprise.setCreatedBy(user.getLogin());
		 enterprise.setPhone(apiEnterprise.getTelefone());
		 enterprise.setEnterpriseName(apiEnterprise.getNome());
		 enterprise.setSituation(apiEnterprise.getSituacao());
		 enterprise.setNeighborhood(apiEnterprise.getBairro());
		 enterprise.setLocalNumber(apiEnterprise.getNumero());
		 enterprise.setStreet(apiEnterprise.getLogradouro()); 
		 
		 return enterprise;
	}
	
	
      public Enterprise findBycnpj(String cnpj) {
    	 return  repo.findBycnpj(cnpj);
      }
      
      public List<Enterprise> findByName(String keyword) {
    	  return repo.findByEnterpriseName(keyword);
      }
      
      public List<Enterprise> findByEnterpriseNameContaining(String keyword) {
    	  return repo.findByEnterpriseNameContaining(keyword);
      }
      
	
	  public List<Enterprise> findByUserId(Long user_id){
			return repo.findByUserId(user_id);
	  }
	
	
	  public Enterprise parseDTOToEntity(ExchangeEnterpriseDTO enterprise) {
			return modelMapper.map( enterprise, Enterprise.class);
		}
			

}
