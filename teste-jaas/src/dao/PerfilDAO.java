package dao;
import model.Perfil;

public class PerfilDAO extends GenericDAO<Perfil> {

	private static final long serialVersionUID = 1L;

	public PerfilDAO() {
		super(Perfil.class);
	}

	public Perfil buscar(String nomePerfil) {
		return super.find(nomePerfil);
	}
}