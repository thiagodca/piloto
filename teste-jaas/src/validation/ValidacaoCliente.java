package validation;

import model.Cliente;
import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;

public class ValidacaoCliente implements Validator<Cliente>{

	public ValidacaoCliente() {
	}

	public static boolean validarCPFCNPJ(String cpfCnpj){

		if (cpfCnpj.length() == 11){  
		   new CPFValidator(false).assertValid(cpfCnpj);  
		   return (true);  
		
		}else if (cpfCnpj.length() == 14){  
			new CNPJValidator(false).assertValid(cpfCnpj);  
			return (true);  
  
		}else{
			return false;
		}
		
	}

	@Override
	public void validate(Cliente cliente) throws Exception {
		
		if(!validarCPFCNPJ(cliente.getCpfCnpj())){
			throw new Exception("CPF/CNPJ invalidos.");
		}
		
	}	
}
