package facade;

import javax.ejb.Local;

import model.ArquivoTemporario;

import org.primefaces.model.UploadedFile;

@Local
public interface CargaAutomaticaFacade {
	
	public abstract void uploadArquivoCarga(UploadedFile arquivo, long cargaArquivoId);
	public abstract void revalidarArquivoTemporario(ArquivoTemporario arquivoTemporario);
    public abstract void gravarDocumentos(long cargaArquivoId) throws Exception;
 
}