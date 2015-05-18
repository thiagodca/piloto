package dao;
import javax.ejb.Stateless;

import model.ArquivoAnexo;

@Stateless
public class ArquivoAnexoDAO extends GenericDAO<ArquivoAnexo> {

	public ArquivoAnexoDAO() {
		super(ArquivoAnexo.class);
	}

}