package facade;

import java.util.List;

import javax.ejb.Local;

import model.Usuario;

@Local
public interface UsuarioFacade {
    
    public abstract void salvar(Usuario usuario);
    
    public abstract Usuario atualizar(Usuario usuario);
 
    public abstract void deletar(Usuario usuario);
 
    public abstract Usuario buscar(String login);
 
    public abstract List<Usuario> listarTodos();

    
}