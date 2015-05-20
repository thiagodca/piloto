package facade;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import model.Cliente;
import dao.ClienteDAO;

@Stateless
public class ClienteFacadeImpl implements ClienteFacade{
	
	@EJB
	private ClienteDAO clienteDAO;

	@Override
	public List<Cliente> listarTodos() {
		List<Cliente> result = clienteDAO.findAll();
		return result;
	}
	
	@Override
	public Cliente buscar(long id){
		return clienteDAO.find(id);
	}
	
	@Override
	public Cliente atualizar(Cliente cliente){
		return clienteDAO.update(cliente);
	}

	@Override
	public void salvar(Cliente cliente) {
		clienteDAO.save(cliente);
	}

	@Override
	public void deletar(Cliente cliente) {
		clienteDAO.delete(cliente.getId(), Cliente.class);
	}
}