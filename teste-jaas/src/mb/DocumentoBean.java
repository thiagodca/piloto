package mb;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import model.ArquivoAnexo;
import model.ArquivoUpload;
import model.Cliente;
import model.Documento;
import model.TipoDocumento;
import model.Usuario;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.sun.org.apache.bcel.internal.generic.ANEWARRAY;

import util.UtilData;
import facade.ArquivoAnexoFacade;
import facade.ArquivoUploadFacade;
import facade.ClienteFacade;
import facade.DocumentoFacade;
import facade.TipoDocumentoFacade;
import facade.UsuarioFacade;

@ManagedBean(name="documentoBean")
@ViewScoped
public class DocumentoBean implements Serializable{

    private static final String LISTAR_DOCUMENTOS 	= "listarDocumentos";
        
	@EJB
	private DocumentoFacade documentoFacade;
	
    @EJB
    private UsuarioFacade usuarioFacade;

    @EJB
    private TipoDocumentoFacade tipoDocumentoFacade;

    @EJB
    private ClienteFacade clienteFacade;

    private Documento documento;
    
    private String codigoCliente;
    
    private List<Documento> listaDeDocumentos;
    
    private List<TipoDocumento> listaTiposDocumentos;
    
    @EJB
    private ArquivoAnexoFacade arquivoAnexoFacade;

    @EJB
    private ArquivoUploadFacade arquivoUploadFacade;

    private ArquivoAnexo arquivoAnexo;
    
    private List<ArquivoAnexo> listaArquivosAnexos;

    private UploadedFile arquivo;
    
    /**
     * Construtor
     */
    public DocumentoBean(){
    	System.out.println(">> DocumentoBean()");		
    }

    @PostConstruct
    public void init(){
    	System.out.println(">> init()");
    	
    	obterListaDeDocumentos();
    	
    	listaTiposDocumentos = tipoDocumentoFacade.listarTodos();
    	
    }
    
    /**
     * Persiste as alteracoes do documento
     * @return
     */
    public void atualizarDocumentoFim(){
    	System.out.println(">> atualizarDocumentoFim()");
        try {
        	Documento umDocumento = documentoFacade.buscar(documento.getId());
        	
        	umDocumento.setTipoDocumento(documento.getTipoDocumento());
        	umDocumento.setTitulo(documento.getTitulo());
        	umDocumento.setDescricao(documento.getDescricao());
        	
        	umDocumento.setDataAlteracao(UtilData.getDataAtual());
        	umDocumento.setUsuarioAlteracao(getUsuarioLogado());
        	
            documentoFacade.atualizar(umDocumento);
            
        } catch (EJBException e) {
        	System.out.println("ERRO ao atualizar");
            sendErrorMessageToUser("Error. Check if the weight is above 0 or call the adm");
        }
 
        sendInfoMessageToUser("Documento atualizado com sucesso.");

        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dialogAtualizarDocumento').hide();");
    }
 
    /**
     * Persiste a exclusao do documento
     * @return
     */
    public void deletarDocumentoFim(){
    	System.out.println(">> deletarDocumentoFim()");
        try {
        	Documento umDocumento = documentoFacade.buscar(documento.getId());
        	umDocumento.setDataExclusao(UtilData.getDataAtual());
        	umDocumento.setUsuarioAlteracao(getUsuarioLogado());
        	
            documentoFacade.atualizar(umDocumento);
            
        } catch (EJBException e) {
            sendErrorMessageToUser("Error. Call the ADM");
            System.out.println("ERRO ao deletar");
            //return CONTINUAR_NA_PAGINA;
        }           
 
        sendInfoMessageToUser("Documento excluido com sucesso.");
 
        //return LISTAR_DOCUMENTOS;
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dialogDeletarDocumento').hide();");
    }
 
    /**
     * Direciona para a pagina que inclui um novo documento
     * @return
     */
    public void incluirDocumentoInicio(){
    	System.out.println(">> incluirDocumentoInicio()");
    	//codigoCliente = documento.getCliente().getCodigo();
    	System.out.println("codigoCliente="+codigoCliente);
    	//documento = new Documento();        
    }
    
    /**
     * Persiste o novo documento
     * @return
     */
    public void incluirDocumentoFim(){
    	System.out.println(">> incluirDocumentoFim()");
        try {
        	Cliente cliente = clienteFacade.buscarClientePorCodigo(codigoCliente);
        	
        	documento.setCliente(cliente);
        	documento.setUsuarioCriacao(getUsuarioLogado());
        	documento.setDataInclusao(UtilData.getDataAtual());
        	
            documentoFacade.salvar(documento);
        } catch (EJBException e) {
            sendErrorMessageToUser("Error. Check if the weight is above 0 or call the adm");
 
            //return CONTINUAR_NA_PAGINA;
        }       
 
        sendInfoMessageToUser("Documento incluido com sucesso.");
 
        //return LISTAR_DOCUMENTOS;
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dialogIncluirDocumento').hide();");
    }
    
    public void incluirArquivoAnexoInicio(){
    	System.out.println(">> incluirArquivoAnexoInicio()");
    	if(documento!=null){
    		System.out.println("documento!=null");
    		listaArquivosAnexos = arquivoAnexoFacade.listarAnexosPorDocumento(documento.getId());
    	}
    	RequestContext.getCurrentInstance().openDialog("incluirArquivoAnexo");
    }
    
    public void incluirArquivoAnexoFim(FileUploadEvent event){
    	System.out.println(">> incluirArquivoAnexoFim()");
        try {
        	
        	String codigoCliente = (String) event.getComponent().getAttributes().get("codigoCliente");
        	Long id = (Long) event.getComponent().getAttributes().get("idDocumento");
        	
        	System.out.println("ID="+id.longValue());
        	System.out.println("codigoCliente="+codigoCliente);
        	Documento umDocumento = documentoFacade.buscar(documento.getId());
        	
        	this.arquivo = event.getFile();
        	System.out.println("Uploaded File Name Is :: "+arquivo.getFileName()+" :: Uploaded File Size :: "+arquivo.getSize());
        	
        	ArquivoUpload au = new ArquivoUpload(arquivo);
        	au.setCodigoCliente(umDocumento.getCliente().getCodigo());

        	String nomeArquivo = au.getNome();

        	arquivoAnexo = new ArquivoAnexo();
        	
        	arquivoAnexo.setDocumento(umDocumento);
            arquivoAnexo.setNome(nomeArquivo);
            arquivoAnexo.setDataInclusao(UtilData.getDataAtual());
            arquivoAnexo.setUsuarioCriacao(getUsuarioLogado());
            arquivoAnexoFacade.salvar(arquivoAnexo);

        	// Realiza o upload do arquivo para o servidor
        	arquivoUploadFacade.upload(au);
        	
        	codigoCliente = umDocumento.getCliente().getCodigo();
            
        } catch (EJBException e) {
            sendErrorMessageToUser("Error. Check if the weight is above 0 or call the adm");
 
        } catch (IOException e) {
            sendErrorMessageToUser("Erro ao carregar arquivo para o servidor. " +e.getMessage());
        }       
 
        sendInfoMessageToUser("Arquivos anexados com sucesso!");

        //RequestContext context = RequestContext.getCurrentInstance();
        //context.execute("PF('dialogAnexarDocumento').hide();");
    }

    public List<ArquivoAnexo> getListaArquivosAnexos(){
    	System.out.println(">> getListaArquivosAnexos()");
    	if(documento!=null){
    		listaArquivosAnexos = arquivoAnexoFacade.listarAnexosPorDocumento(documento.getId());
    	}
    	return listaArquivosAnexos;
	}
    
	public void setArquivoAnexo(ArquivoAnexo arquivoAnexo){
		this.arquivoAnexo = arquivoAnexo;
	}

	public ArquivoAnexo getArquivoAnexo(){
		if(arquivoAnexo == null){
			arquivoAnexo = new ArquivoAnexo();
		}
		return arquivoAnexo;
	}

    
    /**
     * Carrega uma lista de documentos. A lista eh filtrada por cliente, se houver um informado
     */
    public void obterListaDeDocumentos(){
    	System.out.println(">> obterListaDeDocumentos()");
    	
		if(codigoCliente!=null){
			listaDeDocumentos = documentoFacade.listarDocumentosPorCodigoCliente(codigoCliente);
			System.out.println("size: "+listaDeDocumentos.size());
		}else{
			listaDeDocumentos = documentoFacade.listarTodos();
		}
    }
    
    /**
     * Direciona para a pagina que exibe uma lista de documentos
     * @return
     */
    public String listarDocumentos(){
    	System.out.println(">> listarDocumentos()");
    	
    	obterListaDeDocumentos();
    	
        return LISTAR_DOCUMENTOS;
    }

	public List<TipoDocumento> getListaTiposDocumentos(){
		System.out.println(">> getListaTiposDocumentos()");
		return listaTiposDocumentos;
	}

    private void sendInfoMessageToUser(String message){
        FacesContext context = getContext();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
    }
 
    private void sendErrorMessageToUser(String message){
        FacesContext context = getContext();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
    }
 
    private FacesContext getContext() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context;

    }
    
    /**
     * Retorna o usuario logado no sistema
     * 
     * @return Usuario
     */
    private Usuario getUsuarioLogado(){
    	System.out.println(">> getUsuarioLogado()");
    	String login = getContext().getExternalContext().getUserPrincipal().getName();
    	Usuario usuario = usuarioFacade.buscar(login);
    	return usuario;
    }

	public Documento getDocumento(){
		System.out.println(">> getDocumento()");
		if(documento == null){
			documento = new Documento();
		}
		return documento;
	}
	
	public void setDocumento(Documento documento){
		this.documento = documento;
	}
	
	public String getCodigoCliente() {
		return this.codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public List<Documento> getListaDeDocumentos() {
		return this.listaDeDocumentos;
	}

	public void setListaDeDocumentos(List<Documento> listaDeDocumentos) {
		this.listaDeDocumentos = listaDeDocumentos;
	}

	public void setListaTiposDocumentos(List<TipoDocumento> listaTiposDocumentos) {
		this.listaTiposDocumentos = listaTiposDocumentos;
	}

	public UploadedFile getArquivo() {
		return arquivo;
	}

	public void setArquivo(UploadedFile arquivo) {
		this.arquivo = arquivo;
	}
	
}


