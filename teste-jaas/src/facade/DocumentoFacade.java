package facade;

import java.util.List;

import javax.ejb.Local;

import model.Documento;
import model.TipoDocumento;

@Local
public interface DocumentoFacade {
	 
    public abstract void salvar(Documento documento);
 
    public abstract Documento atualizar(Documento documento);
 
    public abstract void deletar(Documento documento);
 
    public abstract Documento buscar(long entityID);
 
    public abstract List<Documento> listarTodos();
    
    public abstract List<Documento> listarDocumentosPorCodigoCliente(String codigoCliente);

	public abstract List<Documento> filtrar( String titulo, 
			String descricao, 
			String codigoCliente, 
			String cpfCnpjCliente,
			String nomeCliente,
			TipoDocumento tipoDocumento,
			String dataInicio,
			String dataFim, 
			String usuario);

}