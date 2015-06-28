package validation;

import javax.ejb.Stateless;

import model.ArquivoCarga;
import model.ArquivoTemporario;
import model.Cliente;

@Stateless
public class ValidatorStrategy<T>{

    public ValidatorStrategy(){
    }

    private Validator<T> get(Object object){
        
    	if(object instanceof Cliente){
    		return (Validator<T>) new ValidacaoCliente();
    		
    	}else if(object instanceof ArquivoTemporario){
    		return (Validator<T>) new ValidacaoArquivoTemporario();
    	
    	}else if(object instanceof ArquivoCarga){
    		return (Validator<T>) new ValidacaoArquivoCarga();
    	}
    	return null;
    }
    
    public void validate(T object) throws Exception{
    	Validator<T> validator = get(object);
    	
    	validator.validate(object);
    }
    
}