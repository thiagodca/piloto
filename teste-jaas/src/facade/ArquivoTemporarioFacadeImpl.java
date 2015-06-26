package facade;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import model.ArquivoTemporario;
import dao.ArquivoTemporarioDAO;

@Stateless
public class ArquivoTemporarioFacadeImpl implements ArquivoTemporarioFacade{
	
	@EJB
	private ArquivoTemporarioDAO arquivoTemporarioDAO;

	@Override
	public ArquivoTemporario buscar(long id){
		return arquivoTemporarioDAO.find(id);
	}
	
	@Override
	public ArquivoTemporario atualizar(ArquivoTemporario arquivoTemporario){
		return arquivoTemporarioDAO.update(arquivoTemporario);
	}

	@Override
	public void salvar(ArquivoTemporario arquivoTemporario) {
		arquivoTemporarioDAO.save(arquivoTemporario);
	}

	@Override
	public void deletar(ArquivoTemporario arquivoTemporario) {
		arquivoTemporarioDAO.delete(arquivoTemporario.getId(), ArquivoTemporario.class);
	}

	@Override
	public List<ArquivoTemporario> listar() {
		return arquivoTemporarioDAO.findAll();
	}

	@Override
	public List<ArquivoTemporario> listarPorCarga(long cargaArquivoId) {
		return arquivoTemporarioDAO.listarPorCarga(cargaArquivoId);
	}
	
	@Override
	public List<ArquivoTemporario> listarPorCargaEIndErro(long cargaArquivoId, String indErro) {
		return arquivoTemporarioDAO.listarPorCargaEIndErro(cargaArquivoId, indErro);
	}
	
}