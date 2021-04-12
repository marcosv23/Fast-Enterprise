package api.model.representation;

public class UserModel {
	
	private Long id;
	
	private String name;

    private String email; 
	
	private String login;
	
	private String cpf;
	
	private String phone;
	
	public UserModel() {
		super();
	}

	public UserModel(String email, String login, String cpf, String phone) {
		super();
		this.email = email;
		this.login = login;
		this.cpf = cpf;
		this.phone = phone;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
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
