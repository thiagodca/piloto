package facade;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import model.Usuario;
import dao.UsuarioDAO;

@Stateless
public class UsuarioFacadeImpl implements UsuarioFacade{
	
	@EJB
	private UsuarioDAO usuarioDAO;

	
	@Override
	public List<Usuario> listarTodos() {
		List<Usuario> result = usuarioDAO.findAll();
		return result;
	}
	
	@Override
	public Usuario buscar(String login){
		Usuario usuario = usuarioDAO.buscar(login);
		return usuario;
	}
	
	@Override
	public Usuario atualizar(Usuario usuario){
		return usuarioDAO.update(usuario);
	}

	@Override
	public void salvar(Usuario usuario) {
		usuarioDAO.save(usuario);
	}

	@Override
	public void deletar(Usuario usuario) {
		usuarioDAO.delete(usuario.getLogin(), Usuario.class);
		
	}
}