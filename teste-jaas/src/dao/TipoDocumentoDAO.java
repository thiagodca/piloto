package dao;
import java.util.HashMap;

import javax.ejb.Stateless;

import model.TipoDocumento;

@Stateless
public class TipoDocumentoDAO extends GenericDAO<TipoDocumento> {

	public TipoDocumentoDAO() {
		super(TipoDocumento.class);
	}
	
	public TipoDocumento buscar(String codigo){
		
		String query = "SELECT tp FROM " + TipoDocumento.class.getName() + " tp " +
					"WHERE tp.codigo = :codigo";
		
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo", codigo);
		
		TipoDocumento result = super.findOneResult(query, parametros);
		return result;
	}

}