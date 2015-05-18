package facade;
import model.Perfil;
import dao.PerfilDAO;

public class PerfilFacade {
	private PerfilDAO perfilDAO = new PerfilDAO();

	public Perfil buscar(String nomePerfil){
		Perfil perfil = perfilDAO.buscar(nomePerfil);
		return perfil;
	}
}