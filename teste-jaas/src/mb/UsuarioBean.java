package mb;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import model.Usuario;
import util.CriptografiaUtil;
import util.UtilData;
import facade.UsuarioFacade;

@ManagedBean(name="usuarioBean")
@RequestScoped
public class UsuarioBean {

	@EJB
	private UsuarioFacade usuarioFacade;
	
	private Usuario usuario;
	
	private String senhaConfirmacao;
	
	public Usuario getUsuario(){
		if(usuario == null){
			usuario = new Usuario();
		}
		return usuario;
	}
	
	public void setUsuario(Usuario usuario){
		this.usuario = usuario;
	}
	
	public List<Usuario> getListaUsuarios(){
		return usuarioFacade.listarTodos();
	}

    public void atualizarUsuarioFim(){
        try {

        	if(!validaSenha()){
                sendErrorMessageToUser("A senha e diferente da confirmacao.");
            }

        	String senhaCriptografada = CriptografiaUtil.gerarSenhaSHA256(usuario.getSenha());
        	
            Usuario umUsuario = usuarioFacade.buscar(usuario.getLogin());
            
            umUsuario.setNome(usuario.getNome());
            umUsuario.setSenha(senhaCriptografada);
            umUsuario.setDataAlteracao(UtilData.getDataAtual());
            
        	usuarioFacade.atualizar(umUsuario);
        	
        } catch (EJBException e) {
            sendErrorMessageToUser("Error. Check if the weight is above 0 or call the adm");
        }
 
        sendInfoMessageToUser("Operation Complete: Update");
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dialogAtualizarUsuario').hide();");
    }
 
    public void deletarUsuarioFim(){
        try {
            Usuario umUsuario = usuarioFacade.buscar(usuario.getLogin());
            umUsuario.setDataExclusao(UtilData.getDataAtual());
        	usuarioFacade.atualizar(umUsuario);
        	
        } catch (EJBException e) {
            sendErrorMessageToUser("Error. Call the ADM");
        }           
 
        sendInfoMessageToUser("Operation Complete: Delete");
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dialogDeletarUsuario').hide();");
    }
 
    public void incluirUsuarioFim(){
        try {
        	
        	if(!validaSenha()){
                sendErrorMessageToUser("A senha e diferente da confirmacao.");
        	}

        	String senhaCriptografada = CriptografiaUtil.gerarSenhaSHA256(usuario.getSenha());
        	
        	usuario.setSenha(senhaCriptografada);
        	usuario.setDataInclusao(UtilData.getDataAtual());
        	
            usuarioFacade.salvar(usuario);
        } catch (EJBException e) {
            sendErrorMessageToUser("Error. Check if the weight is above 0 or call the adm");
        }       
 
        sendInfoMessageToUser("Operation Complete: Create");
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dialogIncluirUsuario').hide();");
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
    
    private boolean validaSenha(){
    	
    	if(!usuario.getSenha().equals(senhaConfirmacao)){
    		return false;
    	}
    	
    	return true;
    }

	public String getSenhaConfirmacao() {
		return senhaConfirmacao;
	}

	public void setSenhaConfirmacao(String senhaConfirmacao) {
		this.senhaConfirmacao = senhaConfirmacao;
	}
}


