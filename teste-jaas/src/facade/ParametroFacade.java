package facade;

import java.util.List;

import javax.ejb.Local;

import model.Parametro;

@Local
public interface ParametroFacade {
	 
    public abstract void salvar(Parametro parametro);
 
    public abstract Parametro atualizar(Parametro parametro);
 
    public abstract void deletar(Parametro parametro);
 
    public abstract Parametro buscar(long entityID);
 
    public abstract List<Parametro> buscarPorTipo(long tipoId);
    
    public abstract List<Parametro> listarTodos();
}