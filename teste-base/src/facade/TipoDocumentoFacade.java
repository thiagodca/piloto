package facade;
import model.TipoDocumento;
import dao.TipoDocumentoDAO;

public class TipoDocumentoFacade {
	private TipoDocumentoDAO tipoDocumentoDAO = new TipoDocumentoDAO();

	public TipoDocumento buscar(int id){
		tipoDocumentoDAO.beginTransaction();
		TipoDocumento perfil = tipoDocumentoDAO.buscar(id);
		tipoDocumentoDAO.closeTransaction();
		return perfil;
	}
}