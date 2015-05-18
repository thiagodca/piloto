package dao;
import javax.ejb.Stateless;

import model.Usuario;

@Stateless
public class UsuarioDAO extends GenericDAO<Usuario> {

	private static final long serialVersionUID = 1L;

	public UsuarioDAO() {
		super(Usuario.class);
	}

	public Usuario buscar(String login) {
		return super.find(login);
	}

}