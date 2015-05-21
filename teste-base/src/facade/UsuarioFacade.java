package facade;
import java.util.List;

import model.Usuario;
import dao.UsuarioDAO;

public class UsuarioFacade {
	private UsuarioDAO usuarioDAO = new UsuarioDAO();

	public void inserir(Usuario usuario) {
		usuarioDAO.beginTransaction();
		usuarioDAO.save(usuario);
		usuarioDAO.commitAndCloseTransaction();
	}
	
	public List<Usuario> listarTodos() {
		usuarioDAO.beginTransaction();
		List<Usuario> result = usuarioDAO.findAll();
		usuarioDAO.closeTransaction();
		return result;
	}
	
	public void deletar(Usuario usuario) {
		usuarioDAO.beginTransaction();
		Usuario persistedUsuario = usuarioDAO.findReferenceOnly(usuario.getLogin());
		usuarioDAO.deletar(persistedUsuario);
		usuarioDAO.commitAndCloseTransaction();
	}
	
	public Usuario buscar(String login){
		usuarioDAO.beginTransaction();
		Usuario usuario = usuarioDAO.buscar(login);
		usuarioDAO.closeTransaction();
		return usuario;
	}
}