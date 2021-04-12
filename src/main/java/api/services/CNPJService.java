package api.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import api.exceptions.ApiException;
import api.external.response.EnterpriseResponse;
import reactor.core.publisher.Mono;


@Service
public class CNPJService {

	@Autowired
	private WebClient webClient;
	

	public  EnterpriseResponse findCNPJ(String CNPJ) {
		 
		Mono<EnterpriseResponse> mono= this.webClient
		   .method(HttpMethod.GET)
		   .uri("/{cnpj}",CNPJ)
		   .retrieve()// retrieve dispatches the  request
		   .onStatus(HttpStatus::is4xxClientError, response -> {
   
               return Mono.error(new ApiException("Erro ao consultar a API da receita"));
           })
		   .bodyToMono(EnterpriseResponse.class);
		
		
		/* to await  webClient asynchronous returning */
		mono.subscribe(enterprise->{
			System.out.print("Enterprise data received from the API");
		} );
		
		EnterpriseResponse e =  mono.block();
		System.out.print("****");
		System.out.print(e);
		
		if(e==null) {
			throw new ApiException("Houve um erro com a API");
		}
	   return e;
	}
	
		

}
