package mb;
import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import model.ArquivoAnexo;
import model.Documento;
import model.Parametro;
import model.TipoParametro;
import model.Usuario;
import util.UtilData;
import facade.ArquivoAnexoFacade;
import facade.DocumentoFacade;
import facade.UsuarioFacade;

@ManagedBean(name="arquivoAnexoBean")
@RequestScoped
public class ArquivoAnexoBean {

	private static final String LISTAR_DOCUMENTOS 	= "listarDocumentos";
    private static final String INCLUIR_ARQUIVOANEXO= "incluirArquivoAnexo";
    private static final String CONTINUAR_NA_PAGINA = null;

	@EJB
	private ArquivoAnexoFacade arquivoAnexoFacade;
	
	@EJB
	private DocumentoFacade documentoFacade;
	
    @EJB
    private UsuarioFacade usuarioFacade;

    private ArquivoAnexo arquivoAnexo;
    
    private Documento documento;
    
    private Part file;

	public ArquivoAnexo getArquivoAnexo(){
		if(arquivoAnexo == null){
			arquivoAnexo = new ArquivoAnexo();
		}
		return arquivoAnexo;
	}
	
    public String incluirArquivoAnexoInicio(){
        return INCLUIR_ARQUIVOANEXO;
    }
    
    public String incluirArquivoAnexoFim(){
        try {
        	UtilArquivo ua = new UtilArquivo();
        	

        	String nomeArquivo = ua.upload(file);
        	
            Documento umDocumento = documentoFacade.buscar(documento.getId());
            arquivoAnexo.setDocumento(umDocumento);
            arquivoAnexo.setNome(nomeArquivo);
            arquivoAnexo.setDataInclusao(UtilData.getDataAtual());
            arquivoAnexo.setUsuarioCriacao(getUsuarioLogado());
            arquivoAnexoFacade.salvar(arquivoAnexo);
            
        } catch (EJBException e) {
            sendErrorMessageToUser("Error. Check if the weight is above 0 or call the adm");
 
            return CONTINUAR_NA_PAGINA;
        } catch (IOException e) {
            sendErrorMessageToUser("Erro ao carregar arquivo para o servidor. " +e.getMessage());
 
            return CONTINUAR_NA_PAGINA;
        }       
 
        sendInfoMessageToUser("Operation Complete: Create");
 
        return INCLUIR_ARQUIVOANEXO;
    }
	
    public String listarDocumentos(){
        return LISTAR_DOCUMENTOS;
    }
    
    public List<ArquivoAnexo> getListaArquivosAnexos(){
    	if(documento != null){
    		return arquivoAnexoFacade.listarTodos(documento.getId());	
    	}
		return null;
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
		if(documento == null){
			documento = new Documento();
		}
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

    private Usuario getUsuarioLogado(){
    	String login = getContext().getExternalContext().getUserPrincipal().getName();
    	Usuario usuario = usuarioFacade.buscar(login);
    	return usuario;
    }
}


