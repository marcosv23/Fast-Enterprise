package api.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ExchangeUserDTO {

	@NotBlank
	@Size(max = 60)
	private String name;

	@NotBlank
	@Email
	@Size(max = 255)
	private String email;

	@Size(max = 255)
	private String login;

	@NotBlank
	@Size(min = 11, max = 11)
	private String cpf;

	@NotBlank
	@Size(min = 8, max = 20)
	private String phone;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
