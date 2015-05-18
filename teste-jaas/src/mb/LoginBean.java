package mb;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import model.Usuario;
import facade.UsuarioFacade;

@SessionScoped
@ManagedBean
public class LoginBean {
    private Usuario usuario;
 
    @EJB
    private UsuarioFacade usuarioFacade;
 
    public Usuario getUsuario(){
        if(usuario == null){
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            String login = context.getUserPrincipal().getName();
 
            usuario = usuarioFacade.buscar(login);
        }
 
        return usuario;
    }
 
    public boolean isUsuarioAdmin(){
        return getRequest().isUserInRole("admin-role");
    }
    
    public boolean isUsuarioDigitador(){
        return getRequest().isUserInRole("digitador-role");
    }
 
    public String logOut(){
        getRequest().getSession().invalidate();
        return "logout";
    }
 
    private HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }
}