package mb;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import model.Cliente;
import model.Usuario;
import util.UtilData;
import facade.ClienteFacade;
import facade.UsuarioFacade;

@ManagedBean(name="clienteBean")
@RequestScoped
public class ClienteBean {

    private static final String INCLUIR_CLIENTE 	= "incluirCliente";
    private static final String DELETAR_CLIENTE 	= "deletarCliente";
    private static final String ATUALIZAR_CLIENTE 	= "atualizarCliente";
    private static final String LISTAR_CLIENTES 	= "listarClientes";
    private static final String CONTINUAR_NA_PAGINA = null;
	
	@EJB
	private ClienteFacade clienteFacade;
	
    @EJB
    private UsuarioFacade usuarioFacade;

    private Cliente cliente;

	public Cliente getCliente(){
		if(cliente == null){
			cliente = new Cliente();
		}
		return cliente;
	}
	
	public void setCliente(Cliente cliente){
		this.cliente = cliente;
	}
	
	public List<Cliente> getListaClientes(){
		return clienteFacade.listarTodos();
	}

    public String atualizarClienteInicio(){
        return ATUALIZAR_CLIENTE;
    }
 
    public String atualizarClienteFim(){
        try {
        	Cliente umCliente = clienteFacade.buscar(cliente.getId());
        	
        	umCliente.setCodigo(cliente.getCodigo());
        	umCliente.setRazaoSocial(cliente.getRazaoSocial());
        	umCliente.setCpfCnpj(cliente.getCpfCnpj());
        	
        	umCliente.setUsuarioAlteracao(getUsuarioLogado());
        	umCliente.setDataAlteracao(UtilData.getDataAtual());
        	
            clienteFacade.atualizar(cliente);
        } catch (EJBException e) {
            sendErrorMessageToUser("Error. Check if the weight is above 0 or call the adm");
            return CONTINUAR_NA_PAGINA;
        }
 
        sendInfoMessageToUser("Operation Complete: Update");
        return LISTAR_CLIENTES;
    }
 
    public String deletarClienteInicio(){
        return DELETAR_CLIENTE;
    }
 
    public String deletarClienteFim(){
        try {
        	
        	Cliente umCliente = clienteFacade.buscar(cliente.getId());
        	umCliente.setDataExclusao(UtilData.getDataAtual());
        	umCliente.setUsuarioAlteracao(getUsuarioLogado());
        	
            clienteFacade.atualizar(umCliente);
        } catch (EJBException e) {
            sendErrorMessageToUser("Error. Call the ADM");
            return CONTINUAR_NA_PAGINA;
        }           
 
        sendInfoMessageToUser("Operation Complete: Delete");
 
        return LISTAR_CLIENTES;
    }
 
    public String incluirClienteInicio(){
        return INCLUIR_CLIENTE;
    }
        
    public String incluirClienteFim(){
        try {
        	cliente.setUsuarioCriacao(getUsuarioLogado());
        	cliente.setDataInclusao(UtilData.getDataAtual());
        	
            clienteFacade.salvar(cliente);
        } catch (EJBException e) {
            sendErrorMessageToUser("Error. Check if the weight is above 0 or call the adm");
 
            return CONTINUAR_NA_PAGINA;
        }       
 
        sendInfoMessageToUser("Operation Complete: Create");
 
        return LISTAR_CLIENTES;
    }
 
    public String listarClientes(){
        return LISTAR_CLIENTES;
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


