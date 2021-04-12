package api.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class ExchangeEnterpriseDTO {

	public ExchangeEnterpriseDTO() {
		super();
	}

	public ExchangeEnterpriseDTO(@NotBlank @Size(max = 14) String cnpj, Long userId) {
		super();
		this.cnpj = cnpj;
		this.userId = userId;
	}

	@NotBlank
	@Size(max =14)
	private String cnpj;
	
   @NotNull
	private Long userId;

	@NotBlank
	@Size(max =50)
	private String companyEmail;
	
	@NotBlank
	@Size(max =80)
	private String  enterpriseName;

	
	@JsonIgnore
	private String owner;
	
	@NotBlank
	@Size(max =20)
	private String phone;
	
	@NotBlank
	@Size(max =30)
	private String  situation;
	
	@NotBlank
	@Size(max =150)
	private String neighborhood;
	
	@NotBlank
	@Size(max =100)
	private String localNumber;
	
	@NotBlank
	@Size(max =150)
	private String city;
	
	@NotBlank
	@Size(max =150)
	private String street;

	@NotBlank
	@Size(max =150)
	private String sizeCompany;
	

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
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
	
	
	
}
