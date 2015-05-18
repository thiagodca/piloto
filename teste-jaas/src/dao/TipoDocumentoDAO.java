package dao;
import javax.ejb.Stateless;

import model.TipoDocumento;

@Stateless
public class TipoDocumentoDAO extends GenericDAO<TipoDocumento> {

	public TipoDocumentoDAO() {
		super(TipoDocumento.class);
	}

}