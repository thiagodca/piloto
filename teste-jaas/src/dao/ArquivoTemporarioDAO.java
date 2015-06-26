package dao;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;

import model.ArquivoTemporario;

@Stateless
public class ArquivoTemporarioDAO extends GenericDAO<ArquivoTemporario> {

	public ArquivoTemporarioDAO() {
		super(ArquivoTemporario.class);
	}
	
	public List<ArquivoTemporario> listarPorCarga(long cargaArquivoId){
		
		String query = "SELECT a FROM " + ArquivoTemporario.class.getName() + " a " +
				"WHERE a.cargaArquivo.id = :cargaArquivoId ";
			
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("cargaArquivoId", cargaArquivoId);
		List<ArquivoTemporario> result = super.find(query, parametros);
		return result;
		
	}
	
	public List<ArquivoTemporario> listarPorCargaEIndErro(long cargaArquivoId, String indErro){
		
		String query = "SELECT a FROM " + ArquivoTemporario.class.getName() + " a " +
				"WHERE a.cargaArquivo.id = :cargaArquivoId " +
				"AND   a.indErro 		 = :indErro";
			
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("cargaArquivoId", cargaArquivoId);
		parametros.put("indErro", indErro);
		List<ArquivoTemporario> result = super.find(query, parametros);
		return result;
		
	}
}