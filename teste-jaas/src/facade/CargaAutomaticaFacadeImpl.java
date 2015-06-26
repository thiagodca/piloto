package facade;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import model.ArquivoAnexo;
import model.ArquivoCarga;
import model.ArquivoTemporario;
import model.CargaArquivo;
import model.Cliente;
import model.Documento;
import model.TipoDocumento;

import org.primefaces.model.UploadedFile;

import util.UtilData;
import validation.ValidatorFactory;

@Stateless
public class CargaAutomaticaFacadeImpl implements CargaAutomaticaFacade{

	@EJB
	private ArquivoAnexoFacade arquivoAnexoFacade;
	
	@EJB
	private ArquivoTemporarioFacade arquivoTemporarioFacade;

	@EJB
	private DocumentoFacade documentoFacade;
	
    @EJB
    private ClienteFacade clienteFacade;
    
    @EJB
    private ArquivoUploadFacade arquivoUploadFacade;

    @EJB
    private TipoDocumentoFacade tipoDocumentoFacade;
    
    @EJB
    private CargaArquivoFacade cargaArquivoFacade;
    
    @Override
    public void uploadArquivoCarga(UploadedFile arquivo, long cargaArquivoId) {
    	
    	ArquivoCarga arquivoCarga = new ArquivoCarga(arquivo);

    	// A carga inicial vai para uma pasta temporaria
    	arquivoCarga.setPasta("TEMP/"+arquivoCarga.getCargaArquivoId());
    	
    	String mensagemErro = "";
    	try{
        	// realiza o upload
        	arquivoUploadFacade.upload(arquivoCarga);
    		
    		// Carrega os parametros encontrados no nome do aarquivo
    		arquivoCarga.carregarParametros();
    		
    		// Validar os dados do arquivo
    		ValidatorFactory.getInstance().getValidator(arquivoCarga).validate();
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		mensagemErro = "" + e.getMessage();
    	}
    	
    	arquivoCarga.setMsgErro(mensagemErro);
    	
    	salvarArquivoTemporario(cargaArquivoId, arquivoCarga);

    }

	private void salvarArquivoTemporario(long cargaArquivoId, ArquivoCarga arquivoCarga) {
		
		// persiste
    	ArquivoTemporario arquivoTemporario = new ArquivoTemporario();
    	arquivoTemporario.setNome(arquivoCarga.getNome());
    	arquivoTemporario.setPasta(arquivoCarga.getPasta());
    	arquivoTemporario.setCodigoTipo(arquivoCarga.getCodigoTipo());
    	arquivoTemporario.setCpfCnpjCliente(arquivoCarga.getCpfCnpjCliente());
    	arquivoTemporario.setDescricao(arquivoCarga.getDescricao());
    	arquivoTemporario.setTamanho(arquivoCarga.getTamanho());
    	arquivoTemporario.setIndErro("N");
    	if(!arquivoCarga.getMsgErro().isEmpty()){
    		arquivoTemporario.setMsgErro(arquivoCarga.getMsgErro());
    		arquivoTemporario.setIndErro("S");
    	}
    	arquivoTemporario.setCargaArquivo(cargaArquivoFacade.buscarCargaArquivoPorId(cargaArquivoId));
    	
    	//chamar o facade para gravar
    	arquivoTemporarioFacade.salvar(arquivoTemporario);
    	
	}

    @Override
    public void revalidarArquivoTemporario(ArquivoTemporario arquivoTemporario) {
   
    	try{
			// Validar os dados do arquivo
			ValidatorFactory.getInstance().getValidator(arquivoTemporario).validate();

			arquivoTemporario.setIndErro("N");
			
    	} catch(Exception e){
    		e.printStackTrace();

    		arquivoTemporario.setMsgErro("" + e.getMessage());
    		arquivoTemporario.setIndErro("S");
    	}

    	arquivoTemporarioFacade.atualizar(arquivoTemporario);
    }
	
	@Override
	public void gravarDocumentos(long cargaArquivoId) throws Exception {
		
		CargaArquivo cargaArquivo = cargaArquivoFacade.buscarCargaArquivoPorId(cargaArquivoId);
		
		List<ArquivoTemporario> arquivosTemporarios = cargaArquivo.getArquivoTemporario();
		
		for(ArquivoTemporario arquivoTemporario : arquivosTemporarios){

			// Processa somente os arquivos OK
			if(arquivoTemporario.getIndErro().equals("S")){
				continue;
			}
			
	    	// Cadastra um novo cliente se nao existir
	    	Cliente cliente = clienteFacade.buscarClientePorCPFCNPJ(arquivoTemporario.getCpfCnpjCliente());        	
	    	if(cliente==null){
	    		cliente = new Cliente();
	    		// gerar codigo
	    		cliente.setCodigo("");
	    		cliente.setCpfCnpj(arquivoTemporario.getCpfCnpjCliente());
	    		cliente.setDataInclusao(UtilData.getDataAtual());
	    		cliente.setUsuarioCriacao(cargaArquivo.getUsuarioAlteracao());
	    		
	    		clienteFacade.salvar(cliente);
	    	}
	    	
	    	// Obtem o tipo de documento
	    	TipoDocumento tipoDocumento = tipoDocumentoFacade.buscarPorCodigo(arquivoTemporario.getCodigoTipo());
	    	
	    	// Cadastra o documento
	    	Documento documento = new Documento();
	    	documento.setCliente(cliente);
	    	documento.setTipoDocumento(tipoDocumento);
	    	documento.setDataInclusao(UtilData.getDataAtual());
	    	documento.setTitulo(arquivoTemporario.getDescricao());
	    	documento.setDescricao(arquivoTemporario.getDescricao());

	    	documento.setCargaArquivo(cargaArquivo);
	    	
	    	documentoFacade.salvar(documento);
	    	
	    	// Cadastra o anexo
	    	ArquivoAnexo anexo = new ArquivoAnexo();
	    	anexo.setNome(arquivoTemporario.getNome());
	    	anexo.setDataInclusao(UtilData.getDataAtual());
			anexo.setUsuarioCriacao(cargaArquivo.getUsuarioAlteracao());
			anexo.setDocumento(documento);
			
			arquivoAnexoFacade.salvar(anexo);
			
			// Deletar arquivo temporario
			
		}
		
	}

}