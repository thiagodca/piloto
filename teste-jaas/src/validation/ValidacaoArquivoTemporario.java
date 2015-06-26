package validation;

import javax.ejb.EJB;

import model.ArquivoTemporario;
import facade.TipoDocumentoFacade;

public class ValidacaoArquivoTemporario extends Validator{

	private ArquivoTemporario arquivoTemporario;
	
	@EJB
	TipoDocumentoFacade tipoDocumentoFacade;
	
	public ValidacaoArquivoTemporario(ArquivoTemporario arquivoTemporario) {
		this.arquivoTemporario = arquivoTemporario;
	}

	@Override
	public void validate() throws Exception {

		String msgErro = "";
		
		String cpfCnpj = arquivoTemporario.getCpfCnpjCliente();
		
		try{
			
			// Verificar se o cpf/cnpj eh valido
	    	if(!ValidacaoCliente.validarCPFCNPJ(cpfCnpj)){
	    		msgErro = "CPF/CNPJ invalido " + cpfCnpj;
	    	}
	    	/*
	    	String codigoTipo = arquivoTemporario.getCodigoTipo();
	    	
	    	// Verificar se o tipo documento existe
	    	TipoDocumento tipoDocumento = tipoDocumentoFacade.buscarPorCodigo(codigoTipo);
	    	if(tipoDocumento==null){
	    		msgErro = "Tipo de Documento inexistente " + codigoTipo;
	    	}
	    	*/
		} catch (Throwable e) {
			e.printStackTrace();
			msgErro = "Erro interno = " + e.getMessage();
		}
		
		if(!msgErro.isEmpty()){
			throw new Exception(msgErro);
		}
		
	}	
}
