package facade;

import java.util.List;

import javax.ejb.Local;

import model.Cliente;

@Local
public interface ClienteFacade {
	 
    public abstract void salvar(Cliente cliente);
 
    public abstract Cliente atualizar(Cliente cliente);
 
    public abstract void deletar(Cliente cliente);
 
    public abstract Cliente buscar(long entityID);
 
    public abstract List<Cliente> listarTodos();
}