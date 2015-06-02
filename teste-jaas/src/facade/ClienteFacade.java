package facade;

import java.util.List;

import javax.ejb.Local;

import model.Cliente;

@Local
public interface ClienteFacade {
	 
    public abstract void salvar(Cliente cliente);
 
    public abstract Cliente atualizar(Cliente cliente);
 
    public abstract void deletar(Cliente cliente);
 
	public abstract Cliente buscarClientePorId(long id);
	
	public abstract Cliente buscarClientePorCodigo(String codigoCliente);
	
    public abstract List<Cliente> listarTodos();
}