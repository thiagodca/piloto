package facade;

import java.io.IOException;

import javax.ejb.Local;

import model.ArquivoCarga;
import model.ArquivoUpload;

@Local
public interface ArquivoUploadFacade {

	public void moveArquivo(ArquivoCarga arquivoCarga) throws Exception;
	
    public abstract void upload(ArquivoUpload arquivoUpload) throws IOException;
    
    public abstract void upload(ArquivoCarga arquivoCarga) throws IOException;
 
}