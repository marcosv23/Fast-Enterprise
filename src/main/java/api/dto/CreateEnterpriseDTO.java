package api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CreateEnterpriseDTO {

	public CreateEnterpriseDTO() {
		super();
	}

	public CreateEnterpriseDTO(@NotBlank @Size(max = 14) String cnpj, @NotNull Long userId) {
		super();
		this.cnpj = cnpj;
		this.userId = userId;
	}

	@NotBlank
	@Size(max = 14)
	private String cnpj;

	/**
	 * userId 'll not be accepted into Request Body, but as a Route param!
	 */
	@JsonIgnore
	private Long userId;

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

}
