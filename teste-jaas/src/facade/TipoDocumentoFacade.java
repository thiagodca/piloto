package facade;

import java.util.List;

import javax.ejb.Local;

import model.TipoDocumento;

@Local
public interface TipoDocumentoFacade {
	 
    public abstract void salvar(TipoDocumento tipoDocumento);
 
    public abstract TipoDocumento atualizar(TipoDocumento tipoDocumento);
 
    public abstract void deletar(TipoDocumento tipoDocumento);
 
    public abstract TipoDocumento buscar(long entityID);
 
    public abstract TipoDocumento buscarPorCodigo(String codigo);

    public abstract List<TipoDocumento> listarTodos();
    
    public abstract List<String> listarTodosString();
}