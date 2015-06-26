package mb;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import model.Usuario;
import facade.UsuarioFacade;

public class ManagedBean {

	@EJB
	private UsuarioFacade usuarioFacade;

	public ManagedBean() {
		super();
	}

	/**
	 * Retorna o usuario logado no sistema
	 * 
	 * @return Usuario
	 */
	protected Usuario getUsuarioLogado() {
		System.out.println(">> getUsuarioLogado()");
		String login = getContext().getExternalContext().getUserPrincipal().getName();
		Usuario usuario = usuarioFacade.buscar(login);
		return usuario;
	}

	/**
	 * Verifica se o usuario tem perfil ADMIN
	 * 
	 * @return boolean
	 */
    public boolean isUsuarioAdmin(){
        return getRequest().isUserInRole("admin-role");
    }

	/**
	 * Verifica se o usuario tem perfil DIGITADOR
	 * 
	 * @return boolean
	 */
    public boolean isUsuarioDigitador(){
        return getRequest().isUserInRole("digitador-role");
    }
	
	protected HttpServletRequest getRequest() {
	    return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	protected void sendInfoMessageToUser(String message) {
	    FacesContext context = getContext();
	    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
	}

	protected void sendErrorMessageToUser(String message) {
	    FacesContext context = getContext();
	    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
	}

	private FacesContext getContext() {
	    FacesContext context = FacesContext.getCurrentInstance();
	    return context;
	}

}