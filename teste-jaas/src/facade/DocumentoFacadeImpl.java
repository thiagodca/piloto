package facade;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import model.Documento;
import dao.DocumentoDAO;

@Stateless
public class DocumentoFacadeImpl implements DocumentoFacade{
	
	@EJB
	private DocumentoDAO documentoDAO;

	@Override
	public List<Documento> listarTodos() {
		List<Documento> result = documentoDAO.listarTodos();
		return result;
	}
	
	@Override
	public Documento buscar(long id){
		return documentoDAO.find(id);
	}
	
	@Override
	public Documento atualizar(Documento documento){
		return documentoDAO.update(documento);
	}

	@Override
	public void salvar(Documento documento) {
		documentoDAO.save(documento);
	}

	@Override
	public void deletar(Documento documento) {
		documentoDAO.delete(documento.getId(), Documento.class);
		
	}
	@Override
	public List<Documento> listarDocumentosPorCodigoCliente(String codigoCliente){
		return documentoDAO.listarPorCliente(codigoCliente);
	}
}