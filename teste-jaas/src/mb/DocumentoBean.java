package mb;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import model.ArquivoAnexo;
import model.Documento;
import model.TipoDocumento;
import model.Usuario;
import util.UtilData;
import facade.DocumentoFacade;
import facade.TipoDocumentoFacade;
import facade.UsuarioFacade;

@ManagedBean(name="documentoBean")
@RequestScoped
public class DocumentoBean {

    private static final String INCLUIR_DOCUMENTO 	= "incluirDocumento";
    private static final String DELETAR_DOCUMENTO 	= "deletarDocumento";
    private static final String ATUALIZAR_DOCUMENTO = "atualizarDocumento";
    private static final String LISTAR_DOCUMENTOS 	= "listarDocumentos";
    private static final String INCLUIR_ARQUIVOANEXO= "incluirArquivoAnexo";
    private static final String CONTINUAR_NA_PAGINA = null;
	
	@EJB
	private DocumentoFacade documentoFacade;
	
    @EJB
    private UsuarioFacade usuarioFacade;

    @EJB
    private TipoDocumentoFacade tipoDocumentoFacade;

    private Documento documento;

	public Documento getDocumento(){
		if(documento == null){
			documento = new Documento();
		}
		return documento;
	}
	
	public void setDocumento(Documento documento){
		this.documento = documento;
	}
	
	public List<Documento> getListaDocumentos(){
		return documentoFacade.listarTodos();
	}

	public List<TipoDocumento> getListaTiposDocumentos(){
		return tipoDocumentoFacade.listarTodos();
	}

    public String atualizarDocumentoInicio(){
        return ATUALIZAR_DOCUMENTO;
    }
 
    public String atualizarDocumentoFim(){
        try {
        	Documento umDocumento = documentoFacade.buscar(documento.getId());
        	
        	umDocumento.setTipoDocumento(documento.getTipoDocumento());
        	umDocumento.setTitulo(documento.getTitulo());
        	umDocumento.setDescricao(documento.getDescricao());
        	
        	umDocumento.setDataAlteracao(UtilData.getDataAtual());
        	umDocumento.setUsuarioAlteracao(getUsuarioLogado());
        	
            documentoFacade.atualizar(documento);
        } catch (EJBException e) {
            sendErrorMessageToUser("Error. Check if the weight is above 0 or call the adm");
            return CONTINUAR_NA_PAGINA;
        }
 
        sendInfoMessageToUser("Operation Complete: Update");
        return LISTAR_DOCUMENTOS;
    }
 
    public String deletarDocumentoInicio(){
        return DELETAR_DOCUMENTO;
    }
 
    public String deletarDocumentoFim(){
        try {
        	
        	Documento umDocumento = documentoFacade.buscar(documento.getId());
        	umDocumento.setDataExclusao(UtilData.getDataAtual());
        	umDocumento.setUsuarioAlteracao(getUsuarioLogado());
        	
            documentoFacade.atualizar(umDocumento);
        } catch (EJBException e) {
            sendErrorMessageToUser("Error. Call the ADM");
            return CONTINUAR_NA_PAGINA;
        }           
 
        sendInfoMessageToUser("Operation Complete: Delete");
 
        return LISTAR_DOCUMENTOS;
    }
 
    public String incluirDocumentoInicio(){
        return INCLUIR_DOCUMENTO;
    }
        
    public String incluirDocumentoFim(){
        try {
        	documento.setUsuarioCriacao(getUsuarioLogado());
        	documento.setDataInclusao(UtilData.getDataAtual());
        	
            documentoFacade.salvar(documento);
        } catch (EJBException e) {
            sendErrorMessageToUser("Error. Check if the weight is above 0 or call the adm");
 
            return CONTINUAR_NA_PAGINA;
        }       
 
        sendInfoMessageToUser("Operation Complete: Create");
 
        return LISTAR_DOCUMENTOS;
    }
 
    public String incluirArquivoAnexoInicio(){
        return INCLUIR_ARQUIVOANEXO;
    }
 
    public String listarDocumentos(){
        return LISTAR_DOCUMENTOS;
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
    private Usuario getUsuarioLogado(){
    	String login = getContext().getExternalContext().getUserPrincipal().getName();
    	Usuario usuario = usuarioFacade.buscar(login);
    	return usuario;
    }
}


