package dao;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;

import model.CargaArquivo;
import model.SituacaoCarga;

@Stateless
public class CargaArquivoDAO extends GenericDAO<CargaArquivo> {

	public CargaArquivoDAO() {
		super(CargaArquivo.class);
	}
	
	public CargaArquivo buscar(long id) {
		return super.find(id);
	}
	
	public List<CargaArquivo> listar(Long id, String descricao, SituacaoCarga situacao, Calendar dataInicio, Calendar dataFim, String usuario) {
		
		String query = "SELECT ca FROM " + CargaArquivo.class.getName() + " ca " +
					   "WHERE  1=1 "; 
		
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		
		if(id!=null){
			System.out.println("id="+id.longValue());
			query = query + "AND ca.id = :id ";
	
			parametros.put("id", id.longValue());
		}
		
		if(dataInicio != null && dataFim != null){
			System.out.println(dataInicio.getTime() + " - " + dataFim.getTime());
			query = query + "AND ca.dataAlteracao > :dataInicio " +
							"AND ca.dataAlteracao < :dataFim ";
			
			parametros.put("dataInicio", dataInicio.getTime());
			parametros.put("dataFim", dataFim.getTime());
		}
		
		if(descricao!=null){
			System.out.println("descricao="+descricao);
			query = query + "AND UPPER(ca.descricao) LIKE UPPER(:descricao) ";
	
			parametros.put("descricao", "%"+descricao+"%");
		}
		
		if(situacao!=null){
			System.out.println("situacao="+situacao);
			query = query + "AND ca.situacao = :situacao ";
			
			parametros.put("situacao", situacao);
		}
		
		if(usuario!=null){
			query = query + "AND ca.usuarioInclusao.login = :usuario ";
			
			parametros.put("usuario", usuario);
		}
		
		System.out.println("Query="+query);
		
		List<CargaArquivo> result = super.find(query, parametros);
		return result;
	}

}