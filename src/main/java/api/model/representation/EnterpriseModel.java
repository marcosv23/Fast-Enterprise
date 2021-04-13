package api.model.representation;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class EnterpriseModel {

	@JsonIgnore()
	private UserModel user;

	private Long id;

	private String enterpriseName;

	private String cnpj;

	private String enterpriseEmail;

	private Date foundation;

	@JsonInclude(Include.NON_NULL)
	private String owner;

	@JsonInclude(Include.NON_NULL)
	private String phone;

	private String situation;

	private String neighborhood;

	private String localNumber;

	private String city;

	private String street;

	private String sizeCompany;

	@JsonInclude(Include.NON_NULL)
	private Long userId;

	@JsonInclude(Include.NON_NULL)
	private String userCPF;

	@JsonInclude(Include.NON_NULL)
	private String userLogin;

	@JsonInclude(Include.NON_NULL)
	private String userEmail;

	@JsonInclude(Include.NON_NULL)
	private Long createdById;

	@JsonInclude(Include.NON_NULL)
	private ActivityInput activity;

	public void parseUserDataModel() {

		this.setUserLogin(this.user.getLogin());
		this.setUserId(this.user.getId());
		this.setUserEmail(this.user.getEmail());
		this.setUserCPF(this.user.getCpf());
		this.setCreatedById(user);

		this.setUser(null);
	}

	public String getEnterpriseEmail() {
		return enterpriseEmail;
	}

	public void setEnterpriseEmail(String enterpriseEmail) {
		this.enterpriseEmail = enterpriseEmail;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Date getFoundation() {
		return foundation;
	}

	public void setFoundation(Date foundation) {
		this.foundation = foundation;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getLocalNumber() {
		return localNumber;
	}

	public void setLocalNumber(String localNumber) {
		this.localNumber = localNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getSizeCompany() {
		return sizeCompany;
	}

	public void setSizeCompany(String sizeCompany) {
		this.sizeCompany = sizeCompany;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public ActivityInput getActivity() {
		return activity;
	}

	public void setActivity(ActivityInput activity) {
		this.activity = activity;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(UserModel user) {
		this.createdById = user.getId();
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserCPF() {
		return userCPF;
	}

	public void setUserCPF(String userCPF) {
		this.userCPF = userCPF;
	}

	public class ActivityInput {

		String text;

		String code;

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

	}

}
