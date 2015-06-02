package facade;

import java.io.IOException;

import javax.ejb.Local;

import model.ArquivoUpload;

@Local
public interface ArquivoUploadFacade {
	 
    public abstract void upload(ArquivoUpload arquivoUpload) throws IOException;
 
}