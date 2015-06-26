package validation;

import model.ArquivoCarga;
import model.ArquivoTemporario;
import model.Cliente;

public class ValidatorFactory {

	private static ValidatorFactory validatorFactory;
	
	public static ValidatorFactory getInstance(){
		if(validatorFactory == null){
			validatorFactory = new ValidatorFactory();
		}
		return validatorFactory;
	}
	
	public Validator getValidator(Object model){
		
		if(model instanceof Cliente){
			return new ValidacaoCliente((Cliente) model);
		}
		
		if(model instanceof ArquivoCarga){
			return new ValidacaoArquivoCarga((ArquivoCarga) model);
		}

		if(model instanceof ArquivoTemporario){
			return new ValidacaoArquivoTemporario((ArquivoTemporario) model);
		}

		return null;
	}
	
}
