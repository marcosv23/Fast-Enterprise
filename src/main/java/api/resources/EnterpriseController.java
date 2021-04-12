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
@RequestMapping(value="api/enterprises")
@Api(value="Fast Enterprise API")
//which domain can access the swagger API
@CrossOrigin(origins="*")

public class EnterpriseController {
	

	@Autowired
	EnterpriseService service;
	
	
	@Autowired
	UserService userService;
	
	@Autowired
	ModelMapper modelMapper;
	
	
	@GetMapping("/user/{id}")
	@ApiOperation("Lista usuários por ID")
	public ResponseEntity<List<EnterpriseModel>> findByUserId(
			@PathVariable 
			Long id){
		var enterprisesEntity =service.findByUserId(id);
		var enterprisesModel = this.parseToCollectionModel(enterprisesEntity);
		
		if(enterprisesModel.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);	
		}
		return ResponseEntity.ok().body(enterprisesModel);
		
	}

	
	@GetMapping("/{id}")
	@ApiOperation("Retorna um usuário  por ID")
	public ResponseEntity<?> findById(
			@PathVariable 
			Long id) {
		var enterpriseEntity =  service.findById(id);
	    
		if(enterpriseEntity!=null) {
			var enterpriseModel =this.parseToModel(enterpriseEntity);
			return ResponseEntity.ok().body(enterpriseModel);
		}
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		
	}
	
	@GetMapping("/name/{name}")
	@ApiOperation("Retorna  um ou mais usuários por nome")
	public ResponseEntity<?> findByName(
			@PathVariable 
			String name){
	   var enterprisesModel= this.parseToCollectionModel(service.findByEnterpriseNameContaining(name));
	   
	   if(enterprisesModel.isEmpty()) {
		   return ResponseEntity.noContent().build();
	   }
	   return ResponseEntity.ok().body(enterprisesModel);
	}
	
	
	@GetMapping("/cnpj/{cnpj}")
	@ApiOperation("Retorna uma empresa quando passado um CPF")
	public ResponseEntity<?> findBycnpj(@PathVariable String cnpj){
	    Enterprise enterpriseEntity= service.findBycnpj(cnpj);
	 
	   if(enterpriseEntity == null) {
		   return ResponseEntity.noContent().build();
	   }
	   var enterpriseModel =this.parseToModel(enterpriseEntity);
	   enterpriseModel.parseUserDataModel();
	   
	   return ResponseEntity.ok().body(enterpriseModel);
	}
	
	
	// Using a sub resource '/user'
	@PostMapping("/create/user/{id}")
	@ApiOperation("Cria um novo usuário")
    public ResponseEntity<?> create( 
    		@PathVariable Long id,
    		@Valid 
    		@RequestBody 
    		CreateEnterpriseDTO enterpriseDTO) throws ParseException{
	
		 
		var enterpriseByCNPJ = service.findBycnpj(enterpriseDTO.getCnpj());
		var enterpriseEntity = new Enterprise();
		var userExists = userService.findById(id);
		enterpriseDTO.setUserId(id);
		
		
		if(userExists ==null) {
			throw new ApiException(
					"O usuário solicitado para registro da empresa não existe!", 
					HttpStatus.NOT_FOUND);
		}
		 
		if(enterpriseByCNPJ==null) {
		 enterpriseEntity =service.fillOutWithAPIData(enterpriseDTO);
		}
		else throw new ApiException("Esse CNPJ já existe", HttpStatus.FORBIDDEN);
		
		service.create(enterpriseEntity);
		EnterpriseModel enterpriseModel = this.parseToModel(enterpriseEntity);
		enterpriseModel.parseUserDataModel();
	    
		return ResponseEntity.ok().body(enterpriseModel);
	}
	
	@PatchMapping("/update/{id}")
	@ApiOperation("Atualiza parcialmente um usuário, em um ou mais campos")
	public ResponseEntity<?> update(
			 @PathVariable
			 Long id,
			 
			 @Valid  
			 @RequestBody 
			 Map<Object, Object> fields) {
	
	var enterprise=service.findById(id);
	
	if(enterprise==null) {
		throw new ApiException("Empresa não está cadastrada", HttpStatus.NOT_FOUND);
	}
	
	fields.forEach((k,v)->{
		// k is key and v is value
		Field field = ReflectionUtils.findRequiredField(Enterprise.class, (String)k);
		field.setAccessible(true);
		ReflectionUtils.setField(field,enterprise, v);
	});
	
	var enterpriseUpdated = service.update(id,enterprise);
	var enterpriseModel = this.parseToModel(enterpriseUpdated);
	
	return ResponseEntity.ok().body(enterpriseModel);	
	}
	
	@PutMapping("/exchange/{id}")
	@ApiOperation("Atualiza,obrigatoriamente, um usuário")
	public ResponseEntity<?> exchange(
			 @PathVariable
			 Long id,
			 
			 @Valid  
			 @RequestBody 
			 ExchangeEnterpriseDTO enterpriseDTO) {
	System.out.println("****");
	System.out.println(enterpriseDTO);
	var enterpriseExists =service.findById(id);
	
	if(enterpriseExists==null) {
		throw new ApiException("Empresa não está cadastrada", HttpStatus.NOT_FOUND);
	}
		
	var enterpriseEntity = this.parseToEntity(enterpriseDTO);
	var enterprisesModel = this.parseToModel( service.update(id, enterpriseEntity));
	
	return ResponseEntity.ok().body(enterprisesModel);
		
	}
	
	@DeleteMapping("/delete/{id}")
	@ApiOperation("Faz soft delete, ou seja, não deleta, mas adiciona um timestamp no banco de dados")
	public void delete(@PathVariable Long id) {
		var enterprise =service.findById(id);
		
		if(enterprise==null) {
			throw new ApiException("Essa empresa não existe!",HttpStatus.NOT_FOUND);
		}
		
		service.delete(id, enterprise);
		
	}
	
	public EnterpriseModel parseToModel(Enterprise enterprise) {
		return modelMapper.map( enterprise, EnterpriseModel.class);
	}
	
	public Enterprise parseToEntity(Object enterprise) {
		return modelMapper.map(enterprise, Enterprise.class);
	}
	
	public List<EnterpriseModel> parseToCollectionModel(List<Enterprise> enterprises){
		return enterprises.stream()
			 .map(enterprise ->  parseToModel(enterprise))
			 .collect(Collectors.toList());
	}
	
	
}
