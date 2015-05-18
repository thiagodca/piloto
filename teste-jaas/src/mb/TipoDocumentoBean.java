package mb;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import model.TipoDocumento;
import facade.TipoDocumentoFacade;

@ManagedBean(name="tipoDocumentoBean")
@RequestScoped
public class TipoDocumentoBean {

    private static final String INCLUIR_TIPO_DOCUMENTO 		= "incluirTipoDocumento";
    private static final String DELETAR_TIPO_DOCUMENTO 		= "deletarTipoDocumento";
    private static final String ATUALIZAR_TIPO_DOCUMENTO 	= "atualizarTipoDocumento";
    private static final String LISTAR_TIPOS_DOCUMENTOS 	= "listarTiposDocumentos";
    private static final String CONTINUAR_NA_PAGINA 		= null;
	
	@EJB
	private TipoDocumentoFacade tipoDocumentoFacade;
	
	private TipoDocumento tipoDocumento;

	public TipoDocumento getTipoDocumento(){
		if(tipoDocumento == null){
			tipoDocumento = new TipoDocumento();
		}
		return tipoDocumento;
	}
	
	public void setTipoDocumento(TipoDocumento tipoDocumento){
		this.tipoDocumento = tipoDocumento;
	}
	
	public List<TipoDocumento> getListaTiposDocumentos(){
		return tipoDocumentoFacade.listarTodos();
	}
	
    public String atualizarTipoDocumentoInicio(){
        return ATUALIZAR_TIPO_DOCUMENTO;
    }
 
    public String atualizarTipoDocumentoFim(){
        try {
            tipoDocumentoFacade.atualizar(tipoDocumento);
        } catch (EJBException e) {
            sendErrorMessageToUser("Error. Check if the weight is above 0 or call the adm");
            return CONTINUAR_NA_PAGINA;
        }
 
        sendInfoMessageToUser("Operation Complete: Update");
        return LISTAR_TIPOS_DOCUMENTOS;
    }
 
    public String deletarTipoDocumentoInicio(){
        return DELETAR_TIPO_DOCUMENTO;
    }
 
    public String deletarTipoDocumentoFim(){
        try {
            tipoDocumentoFacade.deletar(tipoDocumento);
        } catch (EJBException e) {
            sendErrorMessageToUser("Error. Call the ADM");
            return CONTINUAR_NA_PAGINA;
        }           
 
        sendInfoMessageToUser("Operation Complete: Delete");
 
        return LISTAR_TIPOS_DOCUMENTOS;
    }
 
    public String incluirTipoDocumentoInicio(){
        return INCLUIR_TIPO_DOCUMENTO;
    }
 
    public String incluirTipoDocumentoFim(){
        try {
            tipoDocumentoFacade.salvar(tipoDocumento);
        } catch (EJBException e) {
            sendErrorMessageToUser("Error. Check if the weight is above 0 or call the adm");
 
            return CONTINUAR_NA_PAGINA;
        }       
 
        sendInfoMessageToUser("Operation Complete: Create");
 
        return LISTAR_TIPOS_DOCUMENTOS;
    }
 
    public String listarTiposDocumentos(){
        return LISTAR_TIPOS_DOCUMENTOS;
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
}