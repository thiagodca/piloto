package dao;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;

import model.Documento;
import model.TipoDocumento;

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
	
	public List<Documento> listar(	String titulo, 
									String descricao, 
									String codigoCliente, 
									String cpfCnpjCliente,
									String nomeCliente,
									TipoDocumento tipoDocumento,
									Calendar dataInicio, 
									Calendar dataFim, 
									String usuario) {
		
		String query = "SELECT d FROM " + Documento.class.getName() + " d " +
					   "WHERE  1=1 "; 
		
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		
		if(titulo!=null){
			System.out.println("titulo="+titulo);
			query = query + "AND UPPER(d.titulo) LIKE UPPER(:titulo) ";
	
			parametros.put("titulo", "%"+titulo+"%");
		}
		
		if(descricao!=null){
			System.out.println("descricao="+descricao);
			query = query + "AND UPPER(d.descricao) LIKE UPPER(:descricao) ";
	
			parametros.put("descricao", "%"+descricao+"%");
		}

		if(codigoCliente!=null){
			System.out.println("codigoCliente="+codigoCliente);
			query = query + "AND d.cliente.codigo = :codigoCliente ";
	
			parametros.put("codigoCliente", codigoCliente);
		}

		if(cpfCnpjCliente!=null){
			System.out.println("cpfCnpjCliente="+cpfCnpjCliente);
			query = query + "AND d.cliente.cpfCnpj = :cpfCnpjCliente ";
	
			parametros.put("cpfCnpjCliente", cpfCnpjCliente);
		}

		if(nomeCliente!=null){
			System.out.println("nomeCliente="+nomeCliente);
			query = query + "AND UPPER(d.cliente.razaoSocial) LIKE UPPER(:nomeCliente) ";
			
			parametros.put("nomeCliente", "%"+nomeCliente+"%");
		}

		if(dataInicio != null && dataFim != null){
			System.out.println(dataInicio.getTime() + " - " + dataFim.getTime());
			query = query + "AND d.dataAlteracao > :dataInicio " +
							"AND d.dataAlteracao < :dataFim ";
			
			parametros.put("dataInicio", dataInicio.getTime());
			parametros.put("dataFim", dataFim.getTime());
		}
		
		if(tipoDocumento!=null){
			System.out.println("tipoDocumento="+tipoDocumento);
			query = query + "AND d.tipoDocumento.id = :tipoDocumento ";
			
			parametros.put("tipoDocumento", tipoDocumento.getId());
		}
		
		if(usuario!=null){
			query = query + "AND UPPER(d.usuarioInclusao.login) = UPPER(:usuario) ";
			
			parametros.put("usuario", usuario);
		}
		
		System.out.println("Query="+query);
		
		List<Documento> result = super.find(query, parametros);
		return result;
	}

}