package mb;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import model.Cliente;
import model.Usuario;
import util.UtilData;
import facade.ClienteFacade;
import facade.UsuarioFacade;

@ManagedBean(name="clienteBean")
@RequestScoped
public class ClienteBean {

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

    public void atualizarClienteFim(){
        try {
        	Cliente umCliente = clienteFacade.buscarClientePorId(cliente.getId());
        	
        	umCliente.setCodigo(cliente.getCodigo());
        	umCliente.setRazaoSocial(cliente.getRazaoSocial());
        	umCliente.setCpfCnpj(cliente.getCpfCnpj());
        	
        	umCliente.setUsuarioAlteracao(getUsuarioLogado());
        	umCliente.setDataAlteracao(UtilData.getDataAtual());
        	
            clienteFacade.atualizar(cliente);
        } catch (EJBException e) {
            sendErrorMessageToUser("Error. Check if the weight is above 0 or call the adm");
            }
 
        sendInfoMessageToUser("Operation Complete: Update");
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dialogAtualizarCliente').hide();");
    }
 
    public void deletarClienteFim(){
        try {
        	
        	Cliente umCliente = clienteFacade.buscarClientePorId(cliente.getId());
        	umCliente.setDataExclusao(UtilData.getDataAtual());
        	umCliente.setUsuarioAlteracao(getUsuarioLogado());
        	
            clienteFacade.atualizar(umCliente);
        } catch (EJBException e) {
            sendErrorMessageToUser("Error. Call the ADM");
        }           
 
        sendInfoMessageToUser("Operation Complete: Delete");
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dialogDeletarCliente').hide();");
    }
 
    public void incluirClienteFim(){
        try {
        	cliente.setUsuarioCriacao(getUsuarioLogado());
        	cliente.setDataInclusao(UtilData.getDataAtual());
        	
            clienteFacade.salvar(cliente);
        } catch (EJBException e) {
            sendErrorMessageToUser("Error. Check if the weight is above 0 or call the adm");
        }       
 
        sendInfoMessageToUser("Operation Complete: Create");
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dialogIncluirCliente').hide();");
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