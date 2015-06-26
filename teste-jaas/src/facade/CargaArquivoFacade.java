package facade;

import java.util.List;

import javax.ejb.Local;

import model.CargaArquivo;
import model.SituacaoCarga;
import model.Usuario;

@Local
public interface CargaArquivoFacade {
	 
    public abstract CargaArquivo salvar(CargaArquivo cargaArquivo) throws Exception;
 
    public abstract CargaArquivo atualizar(CargaArquivo cargaArquivo);
 
    public abstract void deletar(CargaArquivo cargaArquivo);
 
	public abstract CargaArquivo buscarCargaArquivoPorId(long id);
	
    public abstract List<CargaArquivo> listarTodos();

    public abstract List<CargaArquivo> filtrar(Long id, String descricao, SituacaoCarga situacao, String dataInicio, String dataFim, String usuario);

    public abstract CargaArquivo iniciarCarga(String descricao, Usuario usuario);
    
    public abstract void finalizarCarga(long id, Usuario usuario);
}