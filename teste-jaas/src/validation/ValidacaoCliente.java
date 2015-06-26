package validation;

import model.Cliente;
import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;

public class ValidacaoCliente extends Validator{

	private Cliente cliente;
	
	public ValidacaoCliente(Cliente cliente) {
		this.cliente = cliente;
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
	public void validate() throws Exception {

		if(!validarCPFCNPJ(cliente.getCpfCnpj())){
			throw new Exception("CPF/CNPJ invalidos.");
		}
		
	}	
}
