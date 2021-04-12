package api.handlers;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import api.exceptions.ApiException;
import api.exceptions.ErrorFormat;

@ControllerAdvice                    // this class aims with a "boilerplate"
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
	

	@Autowired //interface to access messages.properties
	private MessageSource messageSource;
	
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<?> handleApiExceptions(ApiException ex, WebRequest request){
		
		var status = ex.getStatus();
		if(status==null) {
			status = HttpStatus.NOT_FOUND;
		}
		var errorFormat = new ErrorFormat();
		
		var errorFields = new ArrayList<ErrorFormat.ErrorField>();
		
		
		
		
		errorFormat.setCustomMessage(ex.getMessage());
		errorFormat.setStatus(status.value());
		errorFormat.setHour(LocalDateTime.now());
		errorFormat.setFields(errorFields);
		
		return super.handleExceptionInternal(ex, errorFormat, new HttpHeaders(), status, request);
	}

	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		var errorFormat = new ErrorFormat();
		
		var errorFields = new ArrayList<ErrorFormat.ErrorField>();
		
		
		for (ObjectError error: ex.getBindingResult().getAllErrors()) {
			
		   //String message = error.getDefaultMessage(); not translated
			
		   String message= messageSource.getMessage(error, LocaleContextHolder.getLocale());
		   String fieldName = ((FieldError)error).getField();
		   errorFields.add( new ErrorFormat.ErrorField(fieldName, message));
		   
		}
		
		errorFormat.setCustomMessage("Há campos não preenchidos ou preenchidos incorretamente");
		errorFormat.setHour(LocalDateTime.now());
		errorFormat.setStatus(status.value());
		errorFormat.setFields(errorFields);
		
		return super.handleExceptionInternal(ex, errorFormat, headers, status, request);
	}
	
	
	
	
	
	
}
