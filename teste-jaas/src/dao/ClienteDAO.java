package dao;
import java.util.HashMap;

import javax.ejb.Stateless;

import model.Cliente;

@Stateless
public class ClienteDAO extends GenericDAO<Cliente> {

	public ClienteDAO() {
		super(Cliente.class);
	}
	
	public Cliente buscar(long idCliente){
		return super.find(idCliente);
	}

	public Cliente buscar(String codigoCliente){
			
		String query = "SELECT c FROM " + Cliente.class.getName() + " c " +
					"WHERE c.codigo = :codigoCliente";
		
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigoCliente", codigoCliente);
		
		Cliente result = super.findOneResult(query, parametros);
		return result;
	}

}