package dao;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;

import model.Documento;

@Stateless
public class DocumentoDAO extends GenericDAO<Documento> {

	public DocumentoDAO() {
		super(Documento.class);
	}

	public List<Documento> listarTodos(){

		String query = "SELECT d FROM " + Documento.class.getName() + " d " +
						"WHERE d.dataExclusao IS NULL";
			
		List<Documento> result = super.find(query, new HashMap<String, Object>());
		return result;
	}
	
	public List<Documento> listarPorCliente(String codigoCliente){

		String query = "SELECT d FROM " + Documento.class.getName() + " d " +
						"WHERE d.cliente.codigo = :codigoCliente " + 
						"AND d.dataExclusao IS NULL";
			
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigoCliente", codigoCliente);
		List<Documento> result = super.find(query, parametros);
		return result;
	}
}