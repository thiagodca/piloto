package dao;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;

import model.Cliente;

@Stateless
public class ClienteDAO extends GenericDAO<Cliente> {

	public ClienteDAO() {
		super(Cliente.class);
	}

	public List<Cliente> listarTodos(){
		
		String query = "SELECT c FROM " + Cliente.class.getName() + " c " +
					"WHERE c.dataExclusao IS NULL ";
		
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		
		List<Cliente> result = super.find(query, parametros);
		return result;
	}
	
	public Cliente buscar(long idCliente){
		return super.find(idCliente);
	}

	public Cliente buscar(String codigoCliente){
			
		String query = "SELECT c FROM " + Cliente.class.getName() + " c " +
					"WHERE c.codigo = :codigoCliente " +
					"AND   c.dataExclusao IS NULL ";
		
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigoCliente", codigoCliente);
		
		Cliente result = super.findOneResult(query, parametros);
		return result;
	}

	public Cliente buscarPorCPFCNPJ(String cpfCnpj){
		
		String query = "SELECT c FROM " + Cliente.class.getName() + " c " +
					"WHERE c.cpfCnpj = :cpfCnpj " +
					"AND   c.dataExclusao IS NULL ";
		
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("cpfCnpj", cpfCnpj);
		
		Cliente result = super.findOneResult(query, parametros);
		return result;
	}
}