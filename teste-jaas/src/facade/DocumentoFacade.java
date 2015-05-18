package facade;

import java.util.List;

import javax.ejb.Local;

import model.Documento;

@Local
public interface DocumentoFacade {
	 
    public abstract void salvar(Documento documento);
 
    public abstract Documento atualizar(Documento documento);
 
    public abstract void deletar(Documento documento);
 
    public abstract Documento buscar(long entityID);
 
    public abstract List<Documento> listarTodos();
}