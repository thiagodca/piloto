package facade;
import java.util.List;

import model.Perfil;
import dao.PerfilDAO;

public class PerfilFacade {
	private PerfilDAO perfilDAO = new PerfilDAO();

	public void inserir(Perfil perfil) {
		perfilDAO.beginTransaction();
		perfilDAO.save(perfil);
		perfilDAO.commitAndCloseTransaction();
	}
	
	public List<Perfil> listarTodos() {
		perfilDAO.beginTransaction();
		List<Perfil> result = perfilDAO.findAll();
		perfilDAO.closeTransaction();
		return result;
	}
	
	public void deletar(Perfil perfil) {
		perfilDAO.beginTransaction();
		Perfil persistedPerfil = perfilDAO.findReferenceOnly(perfil.getNomePerfil());
		perfilDAO.deletar(persistedPerfil);
		perfilDAO.commitAndCloseTransaction();
	}
	
	public Perfil buscar(String nomePerfil){
		perfilDAO.beginTransaction();
		Perfil perfil = perfilDAO.buscar(nomePerfil);
		perfilDAO.closeTransaction();
		return perfil;
	}
}