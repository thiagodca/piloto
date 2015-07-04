package facade;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import model.Documento;
import model.TipoDocumento;
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
	
	@Override
	public List<Documento> filtrar( String titulo, 
									String descricao, 
									String codigoCliente, 
									String cpfCnpjCliente,
									String nomeCliente,
									TipoDocumento tipoDocumento,
									String dataInicio,
									String dataFim, 
									String usuario) {
		
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	
    	Calendar filtroDataInicio = null;
    	Calendar filtroDataFim = null;
    	
    	try {
    		if(!dataInicio.isEmpty() && !dataFim.isEmpty()){
    		
    			filtroDataInicio = Calendar.getInstance();
    			filtroDataInicio.setTime(sdf.parse(dataInicio));
	    		
    			filtroDataFim = Calendar.getInstance();
    			filtroDataFim.setTime(sdf.parse(dataFim));
    		}
        	
    	} catch (ParseException e) {
			e.printStackTrace();
		}

		return documentoDAO.listar(titulo, descricao, codigoCliente, cpfCnpjCliente, nomeCliente, tipoDocumento, filtroDataInicio, filtroDataFim, usuario);
	}

	
}