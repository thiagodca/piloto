package mb;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ejb.EJB;
import javax.servlet.http.Part;

import model.ArquivoUpload;
import model.Cliente;
import model.Parametro;
import model.TipoParametro;
import facade.ClienteFacade;
import facade.ParametroFacade;

public class UtilArquivo {

	@EJB
	private ParametroFacade parametroFacade;
	
	@EJB
	private ClienteFacade clienteFacade;
	
	public UtilArquivo() {
	}
	
	private String buscaDiretorio(){
		
		Parametro parametro = parametroFacade.buscarPorTipoENome(TipoParametro.ARQUIVO, "PATH");
		if(parametro != null){
			String diretorio = parametro.getValor();
			if(!diretorio.endsWith("/")){
				diretorio.concat("/");
			}
			return diretorio;
		}
		return null;
	}
	
	private String buscaCodigoCliente(){
		
		Cliente cliente = clienteFacade.buscar(1);
		if(cliente != null){
			String codigo = cliente.getCodigo();
			if(!codigo.endsWith("/")){
				codigo.concat("/");
			}
			return codigo;
		}
		return null;
	}
	
    public String upload(ArquivoUpload arquivoUpload) throws IOException {
    	
    	Part file = arquivoUpload.getArquivo();
    	String diretorio = arquivoUpload.getCaminho();
    	String codigoCliente = arquivoUpload.getCodigoCliente();
    	String fileName = getFilename(file);
    	
    	String path = diretorio + codigoCliente + "/" + fileName;
    	
    	InputStream inputStream = file.getInputStream();          
    	FileOutputStream outputStream = new FileOutputStream(fileName);
         
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
    	
    	return fileName;
	}  
 
	public static String getFilename(Part part) {  
	   for (String cd : part.getHeader("content-disposition").split(";")) {  
    	   if (cd.trim().startsWith("filename")) {  
        	   String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");  
               return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.  
           }  
       }  
       return null;  
	}     
	
	
}