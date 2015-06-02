package facade;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import model.ArquivoUpload;
import model.Parametro;
import model.TipoParametro;

import org.primefaces.model.UploadedFile;

import dao.ParametroDAO;

@Stateless
public class ArquivoUploadFacadeImpl implements ArquivoUploadFacade{
	
	@EJB
	private ParametroDAO parametroDAO;

	@Override
    public void upload(ArquivoUpload arquivoUpload) throws IOException {
    	
    	UploadedFile file = arquivoUpload.getArquivo();
    	String fileName = arquivoUpload.getNome();
    	String diretorio = buscarDiretorio();
    	String codigoCliente = arquivoUpload.getCodigoCliente();
    	
    	String path = diretorio + codigoCliente + "/" + fileName;
    	    	
    	InputStream inputStream = file.getInputstream();          
    	FileOutputStream outputStream = new FileOutputStream(path);
         
    	byte[] buffer = new byte[4096];          
    	int bytesRead = 0;  
    	while(true) {                          
    		bytesRead = inputStream.read(buffer);  
    		if(bytesRead > 0) {  
    			outputStream.write(buffer, 0, bytesRead);  
    		}else {  
    			break;  
    		}                         
    	}  
    	outputStream.close();  
    	inputStream.close();
    	
	}  

	private String buscarDiretorio(){
		
		Parametro parametro = parametroDAO.buscarParametroPorTipoENome(TipoParametro.ARQUIVO, "PATH");
		if(parametro != null){
			String diretorio = parametro.getValor();
			if(!diretorio.endsWith("/")){
				diretorio = diretorio.concat("/");
			}
			return diretorio;
		}
		return null;
	}
	 
}