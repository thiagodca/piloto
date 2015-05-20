package mb;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import model.Parametro;
import model.TipoParametro;
import model.Usuario;
import util.UtilData;
import facade.ParametroFacade;
import facade.UsuarioFacade;

@ManagedBean(name="parametroBean")
@RequestScoped
public class ParametroBean {

    private static final String ATUALIZAR_PARAMETRO = "atualizarParametro";
    private static final String LISTAR_PARAMETROS 	= "listarParametros";
    private static final String CONTINUAR_NA_PAGINA = null;
	
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

    public String atualizarParametroInicio(){
        return ATUALIZAR_PARAMETRO;
    }
 
    public String atualizarParametroFim(){
        try {
        	Parametro umParametro = parametroFacade.buscar(parametro.getId());
        	
        	umParametro.setValor(parametro.getValor());
        	
            parametroFacade.atualizar(parametro);
        } catch (EJBException e) {
            sendErrorMessageToUser("Error. Check if the weight is above 0 or call the adm");
            return CONTINUAR_NA_PAGINA;
        }
 
        sendInfoMessageToUser("Operation Complete: Update");
        return LISTAR_PARAMETROS;
    }
 
    public String listarParametros(){
        return LISTAR_PARAMETROS;
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