package api.services;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import api.exceptions.ApiException;
import api.model.domain.User;
import api.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository repo;
	
	public User findById(Long id) {
		 var user= repo.findById(id);
		 
		 return user.orElse(null);
		
	}
	
	public List<User> findAll(){
		List<User> users = repo.findAll();
		
		return users;
		}
	
	public User create (User newUser) {
		
		User user =repo.findBycpf(newUser.getCpf()).get();
		User userHasEmail = repo.findByEmail(newUser.getEmail()).get();
		
		if(newUser.getLogin()==null) {
			newUser.setLogin(newUser.getEmail());
		}
		
		if(user!=null) {
			throw new ApiException("Um usuário com esse cpf já está cadastrado! ",HttpStatus.FORBIDDEN);
		}
		
		if(userHasEmail!=null) {
			throw new ApiException("Um usuário com esse email já foi cadastrado!",HttpStatus.FORBIDDEN);
		}
	  return repo.save(newUser);
	
	}
	
	
	public User update(Long id, User user) {
		user.setId(id);
		return repo.save(user);
	}
	
	public void delete(Long id) {
		var thisUser = repo.findById(id).get();
		thisUser.setDeletedAt(OffsetDateTime.now());
	}
	
	
public boolean checkIfPatchFieldsIsAllowed(Long id,User user) {

		
		if(user.getEmail()!=null) {
			
		   var emailExists = repo.findById(id).get().getEmail();
		   
		    if( emailExists==user.getEmail()) {
		    	      throw new ApiException(
	         		  "Esse email já existe!",
	         		  HttpStatus.FORBIDDEN);
			}
			    
		}
        if(user.getCpf()!=null) {
           var cpfExists = repo.findById(id).get().getCpf();
           
           if( cpfExists == user.getCpf()) {
        	   throw new ApiException(
               "Esse cpf já existe!",
                HttpStatus.FORBIDDEN);
           } 
           
		}
		
        if(user.getLogin()!=null) {
          var loginExists = repo.findById(id).get().getLogin();
          
          if(loginExists == user.getLogin()) {
        	      throw new ApiException(
        		  "Esse login já existe!",
        		  HttpStatus.FORBIDDEN);
          }
          
		}
		
		return true;
	}
	
	
	public boolean checkIfPutIsAllowed(Long id,User user) {

		   var anyUserHasThisEmail = repo.findByEmail(user.getEmail());
		   var anyUserHasThisLogin = repo.findByLogin(user.getLogin());
		   var anyUserHasThisCpf = repo.findBycpf(user.getCpf());
		   
		  /**checking if the found user is the same who is 
		   * requesting the put method
		   **/
		   if(anyUserHasThisEmail.isPresent()) {
			    if(anyUserHasThisEmail.get().getId()!= user.getId()) {
			    	      throw new ApiException(
		         		  "Esse email já existe!",
		         		  HttpStatus.FORBIDDEN);	    
			   }
		   }
		   
		   /**checking if the found user is the same who is 
			* requesting the put method
		    **/
		    if(anyUserHasThisLogin.isPresent()) {
			    if(anyUserHasThisLogin.get().getId()!= user.getId()) {
		    	      throw new ApiException(
	       		      "Esse login já existe!",
	       		       HttpStatus.FORBIDDEN);	    
		        }
		    }  
		    
		    
		    
		    /**checking if the found user is the same who is 
			* requesting the put method
		    **/
		    if(anyUserHasThisCpf.isPresent()) {
			    if(anyUserHasThisCpf.get().getId()!= user.getId()) {
		    	      throw new ApiException(
	     		      "Esse CPF já existe!",
	     		       HttpStatus.FORBIDDEN);	    
		        }
		    }
		   return true;
	}
	
	
	public boolean userExists(Long id) {
      var userExists = repo.findById(id);
		
		if(userExists ==null) {
			throw new ApiException("Usuário não existe!", HttpStatus.NOT_FOUND);
		}
		
		return true;
	}
}
