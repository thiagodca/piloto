package dao;
import javax.ejb.Stateless;

import model.Parametro;

@Stateless
public class ParametroDAO extends GenericDAO<Parametro> {

	public ParametroDAO() {
		super(Parametro.class);
	}

}