package mb;
import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import model.ArquivoAnexo;
import model.ArquivoUpload;
import model.Documento;
import model.Usuario;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import util.UtilData;
import facade.ArquivoAnexoFacade;
import facade.ArquivoUploadFacade;
import facade.DocumentoFacade;
import facade.UsuarioFacade;

@ManagedBean(name="arquivoAnexoBean")
@RequestScoped
public class ArquivoAnexoBean {

	private static final String LISTAR_DOCUMENTOS 	= "listarDocumentos";

	@EJB
	private ArquivoAnexoFacade arquivoAnexoFacade;
	
	@EJB
	private DocumentoFacade documentoFacade;
	
    @EJB
    private UsuarioFacade usuarioFacade;

    @EJB
    private ArquivoUploadFacade arquivoUploadFacade;

    private ArquivoAnexo arquivoAnexo;
    
    private Documento documento;
    
    private List<ArquivoAnexo> listaArquivosAnexos;
    
    private UploadedFile arquivo;
    
    private String codigoCliente;

    /**
     * Construtor
     */
    public ArquivoAnexoBean(){
    	System.out.println(">> ArquivoAnexoBean()");		
    }
    
	public ArquivoAnexo getArquivoAnexo(){
		if(arquivoAnexo == null){
			arquivoAnexo = new ArquivoAnexo();
		}
		return arquivoAnexo;
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
        	au.setPasta(umDocumento.getCliente().getCpfCnpj());

        	String nomeArquivo = au.getNome();

        	arquivoAnexo = getArquivoAnexo();
        	
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
 
            //return CONTINUAR_NA_PAGINA;
        } catch (IOException e) {
            sendErrorMessageToUser("Erro ao carregar arquivo para o servidor. " +e.getMessage());
 
            //return CONTINUAR_NA_PAGINA;
        }       
 
        sendInfoMessageToUser("Arquivos anexados com sucesso!");
 
        //return INCLUIR_ARQUIVOANEXO;
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dialogAnexar').hide();");
    }
	
    public String listarDocumentos(){
        return LISTAR_DOCUMENTOS;
    }
    
    public List<ArquivoAnexo> getListaArquivosAnexos(){
    	System.out.println(">> getListaArquivosAnexos()");
    	return listaArquivosAnexos;
	}
    
	public void setArquivoAnexo(ArquivoAnexo arquivoAnexo){
		this.arquivoAnexo = arquivoAnexo;
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

	public Documento getDocumento() {
		System.out.println(">> getDocumento()");
		if(documento == null){
			System.out.println("Documento = null");
			documento = new Documento();
		}
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

    private Usuario getUsuarioLogado(){
    	String login = getContext().getExternalContext().getUserPrincipal().getName();
    	Usuario usuario = usuarioFacade.buscar(login);
    	return usuario;
    }

	public String getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public UploadedFile getArquivo() {
		return arquivo;
	}

	public void setArquivo(UploadedFile arquivo) {
		this.arquivo = arquivo;
	}
}


