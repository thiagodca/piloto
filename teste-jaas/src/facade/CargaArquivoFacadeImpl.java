package facade;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import model.CargaArquivo;
import model.SituacaoCarga;
import model.Usuario;
import util.UtilData;
import dao.CargaArquivoDAO;

@Stateless
public class CargaArquivoFacadeImpl implements CargaArquivoFacade{
	
	@EJB
	private CargaArquivoDAO cargaArquivoDAO;

	@Override
	public List<CargaArquivo> listarTodos() {
		List<CargaArquivo> result = cargaArquivoDAO.findAll();
		return result;
	}
	
	@Override
	public CargaArquivo buscarCargaArquivoPorId(long id){
		return cargaArquivoDAO.buscar(id);
	}
	
	@Override
	public CargaArquivo atualizar(CargaArquivo cargaArquivo){
		return cargaArquivoDAO.update(cargaArquivo);
	}

	@Override
	public CargaArquivo salvar(CargaArquivo cargaArquivo){
		return cargaArquivoDAO.saveAndReturn(cargaArquivo);
	}

	@Override
	public void deletar(CargaArquivo cargaArquivo) {
		cargaArquivoDAO.delete(cargaArquivo.getId(), CargaArquivo.class);
	}
	
	@Override
	public List<CargaArquivo> filtrar(Long id, String descricao, SituacaoCarga situacao, String dataInicio, String dataFim, String usuario) {
		
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

		return cargaArquivoDAO.listar(id, descricao, situacao, filtroDataInicio, filtroDataFim, usuario);
	}

	@Override
	public CargaArquivo iniciarCarga(String descricao, Usuario usuario) {
		
		CargaArquivo cargaArquivo = new CargaArquivo();
		
		cargaArquivo.setDescricao(descricao);
		cargaArquivo.setDataInclusao(UtilData.getDataAtual());
		cargaArquivo.setDataAlteracao(UtilData.getDataAtual());
		cargaArquivo.setSituacao(SituacaoCarga.PENDENTE);
		cargaArquivo.setUsuarioInclusao(usuario);
		cargaArquivo.setUsuarioAlteracao(usuario);
		
		return salvar(cargaArquivo);
	}
	
	@Override
	public void finalizarCarga(long id, Usuario usuario) {
		
		CargaArquivo cargaArquivo = buscarCargaArquivoPorId(id);
		
		cargaArquivo.setDataAlteracao(UtilData.getDataAtual());
		cargaArquivo.setSituacao(SituacaoCarga.PENDENTE);
		cargaArquivo.setUsuarioAlteracao(usuario);
	
		atualizar(cargaArquivo);
	}
	
}