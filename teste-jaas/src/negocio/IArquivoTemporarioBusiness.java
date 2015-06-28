package negocio;

import javax.ejb.Local;

import model.ArquivoTemporario;

@Local
public interface IArquivoTemporarioBusiness {

	public abstract void revalidarArquivoTemporario(
			ArquivoTemporario arquivoTemporario);

}