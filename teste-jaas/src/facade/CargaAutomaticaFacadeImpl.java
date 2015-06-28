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
import negocio.IArquivoTemporarioBusiness;

import org.primefaces.model.UploadedFile;

import util.UtilData;

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
    
    @EJB
    private IArquivoTemporarioBusiness arquivoTemporarioBusiness;

    @Override
    public void uploadArquivoCarga(UploadedFile arquivo, long cargaArquivoId) {
    	
    	ArquivoCarga arquivoCarga = new ArquivoCarga(arquivo);

    	// A carga inicial vai para uma pasta temporaria
    	arquivoCarga.setPasta("TEMP/"+cargaArquivoId);
    	
    	String mensagemErro = "";
    	try{
        	// realiza o upload
        	arquivoUploadFacade.upload(arquivoCarga);
    		
    		// Carrega os parametros encontrados no nome do aarquivo
    		arquivoCarga.carregarParametros();
    		
    		// Validar os dados do arquivo
    		//ValidatorFactory.getInstance().getValidator(arquivoCarga).validate();
    		
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
    	arquivoTemporarioBusiness.revalidarArquivoTemporario(arquivoTemporario);
    }
	
	@Override
	public void gravarDocumentos(long cargaArquivoId) throws Exception {
		
		CargaArquivo cargaArquivo = cargaArquivoFacade.buscarCargaArquivoPorId(cargaArquivoId);
		
		System.out.println("cargaArquivo.id()="+cargaArquivo.getId());
		
		List<ArquivoTemporario> arquivosTemporarios = cargaArquivo.getArquivoTemporario();
		
		if(arquivosTemporarios==null || arquivosTemporarios.size()==0){
			arquivosTemporarios = arquivoTemporarioFacade.listarPorCarga(cargaArquivoId);
		}
		
		for(ArquivoTemporario arquivoTemporario : arquivosTemporarios){

			System.out.println("arquivoTemporario.getinderro()="+arquivoTemporario.getId()+" - "+arquivoTemporario.getIndErro());
			
			// Processa somente os arquivos OK
			if(arquivoTemporario.getIndErro().equals("S")){
				continue;
			}

			// Move da pasta TEMP para a pasta destino
	    	ArquivoCarga arquivoCarga = new ArquivoCarga();

	    	arquivoCarga.setNome(arquivoTemporario.getNome());
	    	arquivoCarga.setCpfCnpjCliente(arquivoTemporario.getCpfCnpjCliente());
	    	arquivoCarga.setPasta("TEMP/"+cargaArquivo.getId());

	    	arquivoUploadFacade.moveArquivo(arquivoCarga);
			
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
	    	
	    	System.out.println("tipoDocumento="+tipoDocumento.getCodigo());
	    	
	    	// Cadastra o documento
	    	Documento documento = new Documento();
	    	documento.setCliente(cliente);
	    	documento.setTipoDocumento(tipoDocumento);
	    	documento.setDataInclusao(UtilData.getDataAtual());
	    	documento.setTitulo(arquivoTemporario.getDescricao());
	    	documento.setDescricao("Upload Carga Automatica");

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
			arquivoTemporarioFacade.deletar(arquivoTemporario);
			
		}
		
	}

}