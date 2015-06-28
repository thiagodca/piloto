package validation;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import model.ArquivoTemporario;
import model.TipoDocumento;
import dao.TipoDocumentoDAO;

@Stateless
public class ValidacaoArquivoTemporario implements Validator<ArquivoTemporario>{

	@EJB
	TipoDocumentoDAO tipoDocumentoDAO;
	
	public ValidacaoArquivoTemporario(){
	}

	@Override
	public void validate(ArquivoTemporario arquivoTemporario) throws Exception {

		String msgErro = "";
		
		String cpfCnpj = arquivoTemporario.getCpfCnpjCliente();
		
		try{
			
			// Verificar se o cpf/cnpj eh valido
	    	if(!ValidacaoCliente.validarCPFCNPJ(cpfCnpj)){
	    		msgErro = "CPF/CNPJ invalido " + cpfCnpj;
	    	}
	    	
	    	String codigoTipo = arquivoTemporario.getCodigoTipo();
	    	
	    	// Verificar se o tipo documento existe
	    	TipoDocumento tipoDocumento = tipoDocumentoDAO.buscar(codigoTipo);
	    	if(tipoDocumento==null){
	    		msgErro = "Tipo de Documento inexistente " + codigoTipo;
	    	}
	    	
		} catch (Throwable e) {
			e.printStackTrace();
			msgErro = "Erro interno = " + e.getMessage();
		}
		
		if(!msgErro.isEmpty()){
			throw new Exception(msgErro);
		}
		
	}	
}
