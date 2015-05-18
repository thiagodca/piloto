package facade;

import java.util.List;

import javax.ejb.Local;

import model.TipoDocumento;

@Local
public interface TipoDocumentoFacade {
	 
    public abstract void salvar(TipoDocumento tipoDocumento);
 
    public abstract TipoDocumento atualizar(TipoDocumento tipoDocumento);
 
    public abstract void deletar(TipoDocumento tipoDocumento);
 
    public abstract TipoDocumento buscar(int entityID);
 
    public abstract List<TipoDocumento> listarTodos();
}