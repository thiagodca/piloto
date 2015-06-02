package facade;
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
	public List<ArquivoAnexo> listarAnexosPorDocumento(long idDocumento) {
		return arquivoAnexoDAO.listarPorDocumento(idDocumento);
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