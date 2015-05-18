package mb;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import model.Usuario;
import util.CriptografiaUtil;
import util.UtilData;
import facade.UsuarioFacade;

@ManagedBean(name="usuarioBean")
@RequestScoped
public class UsuarioBean {

    private static final String INCLUIR_DOCUMENTO 	= "incluirUsuario";
    private static final String DELETAR_DOCUMENTO 	= "deletarUsuario";
    private static final String ATUALIZAR_DOCUMENTO = "atualizarUsuario";
    private static final String LISTAR_USUARIOS 	= "listarUsuarios";
    private static final String CONTINUAR_NA_PAGINA = null;
	
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

    public String atualizarUsuarioInicio(){
        return ATUALIZAR_DOCUMENTO;
    }
 
    public String atualizarUsuarioFim(){
        try {

        	if(!validaSenha()){
                sendErrorMessageToUser("A senha e diferente da confirmacao.");
                return CONTINUAR_NA_PAGINA;
        	}

        	String senhaCriptografada = CriptografiaUtil.gerarSenhaSHA256(usuario.getSenha());
        	
            Usuario umUsuario = usuarioFacade.buscar(usuario.getLogin());
            
            umUsuario.setNome(usuario.getNome());
            umUsuario.setSenha(senhaCriptografada);
            umUsuario.setDataAlteracao(UtilData.getDataAtual());
            
        	usuarioFacade.atualizar(umUsuario);
        	
        } catch (EJBException e) {
            sendErrorMessageToUser("Error. Check if the weight is above 0 or call the adm");
            return CONTINUAR_NA_PAGINA;
        }
 
        sendInfoMessageToUser("Operation Complete: Update");
        return LISTAR_USUARIOS;
    }
 
    public String deletarUsuarioInicio(){
        return DELETAR_DOCUMENTO;
    }
 
    public String deletarUsuarioFim(){
        try {
            Usuario umUsuario = usuarioFacade.buscar(usuario.getLogin());
            umUsuario.setDataExclusao(UtilData.getDataAtual());
        	usuarioFacade.atualizar(umUsuario);
        	
        } catch (EJBException e) {
            sendErrorMessageToUser("Error. Call the ADM");
            return CONTINUAR_NA_PAGINA;
        }           
 
        sendInfoMessageToUser("Operation Complete: Delete");
 
        return LISTAR_USUARIOS;
    }
 
    public String incluirUsuarioInicio(){
        return INCLUIR_DOCUMENTO;
    }
 
    public String incluirUsuarioFim(){
        try {
        	
        	if(!validaSenha()){
                sendErrorMessageToUser("A senha e diferente da confirmacao.");
                return CONTINUAR_NA_PAGINA;
        	}

        	String senhaCriptografada = CriptografiaUtil.gerarSenhaSHA256(usuario.getSenha());
        	
        	usuario.setSenha(senhaCriptografada);
        	usuario.setDataInclusao(UtilData.getDataAtual());
        	
            usuarioFacade.salvar(usuario);
        } catch (EJBException e) {
            sendErrorMessageToUser("Error. Check if the weight is above 0 or call the adm");
 
            return CONTINUAR_NA_PAGINA;
        }       
 
        sendInfoMessageToUser("Operation Complete: Create");
 
        return LISTAR_USUARIOS;
    }
 
    public String listarUsuarios(){
        return LISTAR_USUARIOS;
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


