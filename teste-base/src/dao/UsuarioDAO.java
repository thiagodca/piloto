package dao;
import model.Usuario;

public class UsuarioDAO extends GenericDAO<Usuario> {

	private static final long serialVersionUID = 1L;

	public UsuarioDAO() {
		super(Usuario.class);
	}

	public Usuario buscar(String login) {
		return super.find(login);
	}
    public void deletar(Usuario usuario) {
    	super.delete(usuario.getLogin(), Usuario.class);
    }
}