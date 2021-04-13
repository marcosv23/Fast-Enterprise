package api.exceptions;

import org.springframework.http.HttpStatus;

//unchecked Exception
public class ApiException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private String errorMessage;
	private Integer statusCode;
	private HttpStatus status;

	public ApiException(String errorMessage, HttpStatus status) {
		super(errorMessage);
		this.statusCode = status.value();
		this.status = status;
	}

	public ApiException(String errorMessage) {
		super(errorMessage);

	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

}
