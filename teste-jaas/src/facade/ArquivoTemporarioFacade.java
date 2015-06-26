package facade;

import java.util.List;

import javax.ejb.Local;

import model.ArquivoTemporario;

@Local
public interface ArquivoTemporarioFacade {
	 
    public abstract void salvar(ArquivoTemporario arquivoTemporario);
 
    public abstract ArquivoTemporario atualizar(ArquivoTemporario arquivoTemporario);
 
    public abstract void deletar(ArquivoTemporario arquivoTemporario);
 
    public abstract ArquivoTemporario buscar(long entityID);
 
    public abstract List<ArquivoTemporario> listar();
    
    public abstract List<ArquivoTemporario> listarPorCarga(long cargaArquivoId);
    
    public abstract List<ArquivoTemporario> listarPorCargaEIndErro(long cargaArquivoId, String indErro);
}