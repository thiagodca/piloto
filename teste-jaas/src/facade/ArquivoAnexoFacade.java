package facade;

import java.util.List;

import javax.ejb.Local;

import model.ArquivoAnexo;

@Local
public interface ArquivoAnexoFacade {
	 
    public abstract void salvar(ArquivoAnexo arquivoAnexo);
 
    public abstract ArquivoAnexo atualizar(ArquivoAnexo arquivoAnexo);
 
    public abstract void deletar(ArquivoAnexo arquivoAnexo);
 
    public abstract ArquivoAnexo buscar(long entityID);
 
    public abstract List<ArquivoAnexo> listarAnexosPorDocumento(long idDocumento);
}