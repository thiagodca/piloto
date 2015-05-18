package dao;
import javax.ejb.Stateless;

import model.Documento;

@Stateless
public class DocumentoDAO extends GenericDAO<Documento> {

	public DocumentoDAO() {
		super(Documento.class);
	}

}