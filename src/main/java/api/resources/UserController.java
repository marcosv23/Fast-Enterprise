package api.resources;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import api.dto.CreateUserDTO;
import api.dto.ExchangeUserDTO;
import api.exceptions.ApiException;
import api.model.domain.User;
import api.model.representation.UserModel;
import api.services.UserService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "api/users")
public class UserController {

	@Autowired
	UserService service;

	@Autowired
	ModelMapper modelMapper;

	@GetMapping("/{id}")
	@ApiOperation("Lista usuários por Id")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		User userEntity = service.findById(id);

		if (userEntity.equals(null)) {
			throw new ApiException("Esse usuário não existe");

		}

		var userModel = this.parseToModel(userEntity);

		return ResponseEntity.ok().body(userModel);
	}

	@PostMapping("/create")
	@ApiOperation("Cria um novo usuário")
	@ResponseStatus(HttpStatus.CREATED)
	public UserModel createUser(@RequestBody @Valid CreateUserDTO newUser) {

		var userEntity = this.parseToEntity(newUser);
		return parseToModel(service.create(userEntity));

	}

	@PatchMapping("/update/{id}")
	@ApiOperation("Atualiza parcialmente um usuário")
	@ResponseStatus(HttpStatus.OK)
	public UserModel updateUser(

			@RequestBody Map<Object, Object> fields,

			@PathVariable Long id) {

		var user = service.findById(id);

		if (user == null) {
			throw new ApiException("Usuário não existe!", HttpStatus.NOT_FOUND);
		}

		fields.forEach((k, v) -> {
			Field field = ReflectionUtils.findRequiredField(User.class, (String) k);
			field.setAccessible(true);
			ReflectionUtils.setField(field, user, v);
		});

		var userTocheck = this.parseToEntity(fields);
		service.checkIfPatchFieldsIsAllowed(id, userTocheck);
		return parseToModel(service.update(id, user));

	}

	@PutMapping("/exchange/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation("Atualiza um usuário em sua totalidade")
	public UserModel exchangeUser(@RequestBody @Valid ExchangeUserDTO userDTO, @PathVariable Long id) {

		// check if exists else throw exception
		service.userExists(id);

		var userEntity = this.parseToEntity(userDTO);
		service.checkIfPutIsAllowed(id, userEntity);

		/*
		 * If user don't specify a login this will be the email
		 */
		if (userEntity.getLogin() == "") {
			userEntity.setLogin(userEntity.getEmail());
		}

		return parseToModel(service.update(id, userEntity));

	}

	@DeleteMapping("/delete/{id}")
	@ApiOperation("Faz soft delete de um usuário")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		// check if exists else throw exception
		service.userExists(id);

		// soft deleting
		service.delete(id);
		return ResponseEntity.ok().body(null);
	}

	private User parseToEntity(Object user) {
		return modelMapper.map(user, User.class);
	}

	private UserModel parseToModel(Object user) {
		return modelMapper.map(user, UserModel.class);
	}

	public List<UserModel> parseToCollectionModel(List<User> users) {
		return users.stream().map(user -> parseToModel(user)).collect(Collectors.toList());
	}

	@GetMapping()
	@ApiOperation("Lista todos os usuários cadastrados")
	public ResponseEntity<?> findAll() {
		List<User> usersEntity = service.findAll();

		var usersModel = this.parseToCollectionModel(usersEntity);

		return ResponseEntity.ok().body(usersModel);

	}

}
