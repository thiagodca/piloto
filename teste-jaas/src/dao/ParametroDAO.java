package dao;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;

import model.Parametro;

@Stateless
public class ParametroDAO extends GenericDAO<Parametro> {

	public ParametroDAO() {
		super(Parametro.class);
	}
	
	public List<Parametro> buscarParametroPorTipo(long idTipo) {
		
		String query = "SELECT p FROM " + Parametro.class.getName() + " p " +
					"WHERE p.tipoParametro.id = :tipoParametro";
		
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("tipoParametro", idTipo);
		List<Parametro> result = super.find(query, parametros);
		return result;
	}
	
	public Parametro buscarParametroPorTipoENome(long idTipo, String nome) {
		
		String query = "SELECT p FROM " + Parametro.class.getName() + " p " +
					   " WHERE p.tipoParametro.id = :tipoParametro" +
					   "   AND p.nome             = :nome";
	
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("tipoParametro", idTipo);
		parametros.put("nome", nome);
		Parametro result = super.findOneResult(query, parametros);
		return result;
	}
}