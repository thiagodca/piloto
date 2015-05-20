package facade;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import model.ArquivoAnexo;
import dao.ArquivoAnexoDAO;

@Stateless
public class ArquivoAnexoFacadeImpl implements ArquivoAnexoFacade{
	
	@EJB
	private ArquivoAnexoDAO arquivoAnexoDAO;

	@Override
	public List<ArquivoAnexo> listarTodos(long idDocumento) {
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("id", idDocumento);
		List<ArquivoAnexo> result = arquivoAnexoDAO.find("ArquivoAnexo.listarPorDocumento", parametros);
		return result;
	}
	
	@Override
	public ArquivoAnexo buscar(long id){
		return arquivoAnexoDAO.find(id);
	}
	
	@Override
	public ArquivoAnexo atualizar(ArquivoAnexo arquivoAnexo){
		return arquivoAnexoDAO.update(arquivoAnexo);
	}

	@Override
	public void salvar(ArquivoAnexo arquivoAnexo) {
		arquivoAnexoDAO.save(arquivoAnexo);
	}

	@Override
	public void deletar(ArquivoAnexo arquivoAnexo) {
		arquivoAnexoDAO.delete(arquivoAnexo.getId(), ArquivoAnexo.class);
		
	}
}