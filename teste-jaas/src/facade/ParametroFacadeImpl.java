package facade;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import model.Parametro;
import dao.ParametroDAO;

@Stateless
public class ParametroFacadeImpl implements ParametroFacade{
	
	@EJB
	private ParametroDAO parametroDAO;

	@Override
	public List<Parametro> listarTodos() {
		List<Parametro> result = parametroDAO.findAll();
		return result;
	}
	
	@Override
	public Parametro buscar(long id){
		return parametroDAO.find(id);
	}
	
	@Override
	public Parametro atualizar(Parametro parametro){
		return parametroDAO.update(parametro);
	}

	@Override
	public void salvar(Parametro parametro) {
		parametroDAO.save(parametro);
	}

	@Override
	public void deletar(Parametro parametro) {
		parametroDAO.delete(parametro.getId(), Parametro.class);
	}

	@Override
	public List<Parametro> buscarPorTipo(long idTipo) {
		
		String query = "SELECT p FROM " + Parametro.class.getName() + " p " +
					"WHERE p.tipoParametro.id = :tipoParametro";
		
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("tipoParametro", idTipo);
		List<Parametro> result = parametroDAO.executeQuery(query, parametros);
		return result;
	}
}