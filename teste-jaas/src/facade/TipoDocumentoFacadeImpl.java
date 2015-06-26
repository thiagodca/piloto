package facade;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import model.TipoDocumento;
import dao.TipoDocumentoDAO;

@Stateless
public class TipoDocumentoFacadeImpl implements TipoDocumentoFacade{
	
	@EJB
	private TipoDocumentoDAO tipoDocumentoDAO;

	@Override
	public List<TipoDocumento> listarTodos() {
		List<TipoDocumento> result = tipoDocumentoDAO.findAll();
		return result;
	}
	
	@Override
	public List<String> listarTodosString() {
		List<TipoDocumento> tipos = tipoDocumentoDAO.findAll();
		
		List<String> result = new ArrayList<String>();
		for(TipoDocumento tp : tipos){
			result.add(tp.getNomeTipo() + ";" + tp.getCodigo());
		}
		
		return result;
	}

	@Override
	public TipoDocumento buscar(long id){
		return tipoDocumentoDAO.find(id);
	}
	
	@Override
	public TipoDocumento buscarPorCodigo(String codigo){
		return tipoDocumentoDAO.buscar(codigo);
	}
	
	@Override
	public TipoDocumento atualizar(TipoDocumento tipoDocumento){
		return tipoDocumentoDAO.update(tipoDocumento);
	}

	@Override
	public void salvar(TipoDocumento tipoDocumento) {
		tipoDocumentoDAO.save(tipoDocumento);
	}

	@Override
	public void deletar(TipoDocumento tipoDocumento) {
		tipoDocumentoDAO.delete(tipoDocumento.getId(), TipoDocumento.class);
		
	}
}