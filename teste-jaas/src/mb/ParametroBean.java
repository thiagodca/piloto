package mb;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import model.Parametro;
import facade.ParametroFacade;
import facade.UsuarioFacade;

@ManagedBean(name="parametroBean")
@RequestScoped
public class ParametroBean {

	@EJB
	private ParametroFacade parametroFacade;
	
    @EJB
    private UsuarioFacade usuarioFacade;

    private Parametro parametro;

	public Parametro getParametro(){
		if(parametro == null){
			parametro = new Parametro();
		}
		return parametro;
	}
	
	public void setParametro(Parametro parametro){
		this.parametro = parametro;
	}
	
	public List<Parametro> getListaParametros(){
		return parametroFacade.listarTodos();
	}

    public void atualizarParametroFim(){
        try {
        	Parametro umParametro = parametroFacade.buscar(parametro.getId());
        	
        	umParametro.setValor(parametro.getValor());
        	
            parametroFacade.atualizar(parametro);
        } catch (EJBException e) {
            sendErrorMessageToUser("Error. Check if the weight is above 0 or call the adm");
        }
 
        sendInfoMessageToUser("Operation Complete: Update");
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dialogAtualizarParametro').hide();");
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