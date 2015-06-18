package facade;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import validation.ValidatorFactory;
import model.Cliente;
import dao.ClienteDAO;

@Stateless
public class ClienteFacadeImpl implements ClienteFacade{
	
	@EJB
	private ClienteDAO clienteDAO;

	@Override
	public List<Cliente> listarTodos() {
		List<Cliente> result = clienteDAO.listarTodos();
		return result;
	}
	
	@Override
	public Cliente buscarClientePorId(long id){
		return clienteDAO.buscar(id);
	}
	
	@Override
	public Cliente atualizar(Cliente cliente){
		return clienteDAO.update(cliente);
	}

	@Override
	public void salvar(Cliente cliente) throws Exception {
		
		validar(cliente);
		
		clienteDAO.save(cliente);
	}

	@Override
	public void deletar(Cliente cliente) {
		clienteDAO.delete(cliente.getId(), Cliente.class);
	}
	
	@Override
	public Cliente buscarClientePorCodigo(String codigoCliente){
		return clienteDAO.buscar(codigoCliente);
	}

	@Override
	public Cliente buscarClientePorCPFCNPJ(String cpfCnpj) {
		return clienteDAO.buscarPorCPFCNPJ(cpfCnpj);
	}
	
	private void validar(Cliente cliente) throws Exception{
		
		ValidatorFactory.getInstance().getValidator(cliente).validate();
		
	}
	
}