package dao;
import model.TipoDocumento;

public class TipoDocumentoDAO extends GenericDAO<TipoDocumento> {

	private static final long serialVersionUID = 1L;

	public TipoDocumentoDAO() {
		super(TipoDocumento.class);
	}

	public TipoDocumento buscar(int id) {
		return super.find(id);
	}
}