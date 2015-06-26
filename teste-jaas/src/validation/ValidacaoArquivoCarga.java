package validation;

import javax.ejb.EJB;

import model.ArquivoCarga;
import facade.TipoDocumentoFacade;

public class ValidacaoArquivoCarga extends Validator{

	private ArquivoCarga arquivoCarga;
	
	@EJB
	TipoDocumentoFacade tipoDocumentoFacade;
	
	public ValidacaoArquivoCarga(ArquivoCarga arquivoCarga) {
		this.arquivoCarga = arquivoCarga;
	}

	@Override
	public void validate() throws Exception {

		String msgErro = "";
		
		String cpfCnpj = arquivoCarga.getCpfCnpjCliente();
		
		try{
			
			// Verificar se o cpf/cnpj eh valido
	    	if(!ValidacaoCliente.validarCPFCNPJ(cpfCnpj)){
	    		msgErro = "CPF/CNPJ invalido " + cpfCnpj;
	    	}
	    	/*
	    	String codigoTipo = arquivoCarga.getCodigoTipo();
	    	
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
