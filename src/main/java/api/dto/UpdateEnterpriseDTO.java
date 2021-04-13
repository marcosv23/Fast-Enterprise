package api.dto;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UpdateEnterpriseDTO {

	@Size(max = 14)
	private String cnpj;

	@Size(max = 50)
	private String companyEmail;

	@Size(max = 80)
	private String enterpriseName;

	@JsonIgnore
	private String owner;

	@Size(max = 20)
	private String phone;

	@Size(max = 30)
	private String situation;

	@Size(max = 150)
	private String neighborhood;

	@Size(max = 100)
	private String localNumber;

	@Size(max = 150)
	private String city;

	@Size(max = 150)
	private String street;

	@Size(max = 150)
	private String sizeCompany;

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
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
