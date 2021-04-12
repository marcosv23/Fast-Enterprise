package api.exceptions;

import java.time.LocalDateTime;
import java.util.List;
/**
 * This class is to 
 * catching up both
 *  the issues root
 *  and each fields
 * @author marcos
 *
 */
public class ErrorFormat {

	private Integer status;
	private LocalDateTime hour;
	private String customMessage;
	private List<ErrorField> fields;
	
	
	public ErrorFormat() {
		super();
	}
	
	public static class ErrorField{
		private String name;
		private String message;
		
		/** This code is used to form a custom message
		 *  for each incorrect input into DTOs
		 **/
		public ErrorField(String name, String message) {
			super();
			this.name = name;
			this.message = message;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
		
		
	}

	public Integer getStatus() {
		return status;
	}




	public void setStatus(Integer status) {
		this.status = status;
	}




	public LocalDateTime getHour() {
		return hour;
	}




	public void setHour(LocalDateTime hour) {
		this.hour = hour;
	}




	public String getCustomMessage() {
		return customMessage;
	}




	public void setCustomMessage(String customMessage) {
		this.customMessage = customMessage;
	}




	public List<ErrorField> getFields() {
		return fields;
	}




	public void setFields(List<ErrorField> fields) {
		this.fields = fields;
	}



	
	
}
