package api.resources;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.dto.CreateEnterpriseDTO;
import api.dto.ExchangeEnterpriseDTO;
import api.exceptions.ApiException;
import api.model.domain.Enterprise;
import api.model.representation.EnterpriseModel;
import api.services.EnterpriseService;
import api.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "api/enterprises")
@Api(value = "Fast Enterprise API")
//which domain can access the swagger API
@CrossOrigin(origins = "*")

public class EnterpriseController {

	@Autowired
	EnterpriseService service;

	@Autowired
	UserService userService;

	@Autowired
	ModelMapper modelMapper;

	@GetMapping("/user/{userId}")
	@ApiOperation("Lista todas as empresas que um usuário cadastrou")
	public ResponseEntity<List<EnterpriseModel>> findByUserId(@PathVariable Long userId) {
		var enterprisesEntity = service.findByUserId(userId);

		var enterprisesModel = this.parseToCollectionModel(enterprisesEntity);

		if (enterprisesModel.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
		return ResponseEntity.ok().body(enterprisesModel);

	}

	@GetMapping("/{enterpriseId}")
	@ApiOperation("Lista empresas por Id")
	public ResponseEntity<?> findById(@PathVariable Long enterpriseId) {
		var enterpriseEntity = service.findById(enterpriseId);

		if (enterpriseEntity != null) {
			var enterpriseModel = this.parseToModel(enterpriseEntity);
			return ResponseEntity.ok().body(enterpriseModel);
		}

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

	}

	@GetMapping("/name/{name}")
	@ApiOperation("Busca uma empresa cadastrada por meio do nome")
	public ResponseEntity<?> findByName(@PathVariable String name) {
		var enterprisesModel = this.parseToCollectionModel(service.findByEnterpriseNameContaining(name));

		if (enterprisesModel.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok().body(enterprisesModel);
	}

	@GetMapping("/cnpj/{cnpj}")
	@ApiOperation("Busca uma empresa cadastrada por meio do cnpj")
	public ResponseEntity<?> findBycnpj(@PathVariable String cnpj) {
		Enterprise enterpriseEntity = service.findBycnpj(cnpj);

		if (enterpriseEntity == null) {
			return ResponseEntity.noContent().build();
		}
		var enterpriseModel = this.parseToModel(enterpriseEntity);
		enterpriseModel.parseUserDataModel();

		return ResponseEntity.ok().body(enterpriseModel);
	}

	// Using a sub resource '/user'
	@PostMapping("/create/user/{userId}")
	@ApiOperation("Cria uma nova empresa para o usuário passado via parâmetro")
	public ResponseEntity<?> create(@PathVariable Long userId, @Valid @RequestBody CreateEnterpriseDTO enterpriseDTO)
			throws ParseException {

		var enterpriseByCNPJ = service.findBycnpj(enterpriseDTO.getCnpj());
		var enterpriseEntity = new Enterprise();
		userService.userExists(userId);

		enterpriseDTO.setUserId(userId);

		if (enterpriseByCNPJ == null) {
			enterpriseEntity = service.fillOutWithAPIData(enterpriseDTO);
		} else
			throw new ApiException("Esse CNPJ já existe", HttpStatus.FORBIDDEN);

		service.create(enterpriseEntity);
		EnterpriseModel enterpriseModel = this.parseToModel(enterpriseEntity);
		enterpriseModel.parseUserDataModel();

		return ResponseEntity.ok().body(enterpriseModel);
	}

	@PatchMapping("/update/{enterpriseId}")
	@ApiOperation("Atualiza parcialmente uma empresa, em um ou mais campos")
	public ResponseEntity<?> update(@PathVariable Long enterpriseId,

			@Valid @RequestBody Map<Object, Object> fields) {

		
		// check if exists
		this.service.enterpriseExists(enterpriseId);

		var enterprise = this.service.findById(enterpriseId);
		var enterpriseModel = this.parseToModel(enterprise);

		// check if is allowed to update
		this.service.validatingUpdate(enterprise);

		fields.forEach((k, v) -> {
			Field field = ReflectionUtils.findRequiredField(EnterpriseModel.class, (String) k);
			field.setAccessible(true);
			ReflectionUtils.setField(field,enterpriseModel, v);
		});

		var enterpriseUpdated = service.update(enterpriseId, enterprise);


		return ResponseEntity.ok().body(enterpriseModel);
	}

	@PutMapping("/exchange/{enterpriseId}")
	@ApiOperation("Atualiza,obrigatoriamente, todos os dados de uma empresa")
	public ResponseEntity<?> exchange(@PathVariable Long enterpriseId,

			@Valid @RequestBody ExchangeEnterpriseDTO enterpriseDTO) {

		var enterpriseEntity = this.parseToEntity(enterpriseDTO);

		// check if exists
		this.service.enterpriseExists(enterpriseId);

		// check if is allowed updated
		this.service.validatingUpdate(enterpriseDTO);

		var enterprisesModel = this.parseToModel(service.update(enterpriseId, enterpriseEntity));

		return ResponseEntity.ok().body(enterprisesModel);

	}

	@DeleteMapping("/delete/{enterpriseId}")
	@ApiOperation("Faz soft delete de uma empresa")
	public void delete(@PathVariable Long enterpriseId) {
		var enterprise = service.findById(enterpriseId);

		if (enterprise == null) {
			throw new ApiException("Essa empresa não existe!", HttpStatus.NOT_FOUND);
		}

		service.delete(enterpriseId, enterprise);

	}

	public EnterpriseModel parseToModel(Enterprise enterprise) {
		return modelMapper.map(enterprise, EnterpriseModel.class);
	}

	public Enterprise parseToEntity(Object enterprise) {
		return modelMapper.map(enterprise, Enterprise.class);
	}

	public List<EnterpriseModel> parseToCollectionModel(List<Enterprise> enterprises) {
		return enterprises.stream().map(enterprise -> parseToModel(enterprise)).collect(Collectors.toList());
	}

}
