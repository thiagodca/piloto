package mb;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import model.TipoDocumento;
import facade.TipoDocumentoFacade;

@ManagedBean(name="tipoDocumentoBean")
@RequestScoped
public class TipoDocumentoBean {
	
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
	
    public void atualizarTipoDocumentoFim(){
        try {
            tipoDocumentoFacade.atualizar(tipoDocumento);
        } catch (EJBException e) {
            sendErrorMessageToUser("Error. Check if the weight is above 0 or call the adm");
        }
 
        sendInfoMessageToUser("Operation Complete: Update");
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dialogAlterarTipoDocumento').hide();");
    }
 
    public void deletarTipoDocumentoFim(){
        try {
            tipoDocumentoFacade.deletar(tipoDocumento);
        } catch (EJBException e) {
            sendErrorMessageToUser("Error. Call the ADM");
        }           
 
        sendInfoMessageToUser("Operation Complete: Delete");
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dialogDeletarTipoDocumento').hide();");
    }
 
    public void incluirTipoDocumentoFim(){
        try {
            tipoDocumentoFacade.salvar(tipoDocumento);
        } catch (EJBException e) {
            sendErrorMessageToUser("Error. Check if the weight is above 0 or call the adm");
        }       
 
        sendInfoMessageToUser("Operation Complete: Create");
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dialogIncluirTipoDocumento').hide();");
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