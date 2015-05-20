package mb;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.Part;

public class UtilArquivo {

	public UtilArquivo() {
	}
	
    public String upload(Part file) throws IOException {
    	String fileName = getFilename(file);
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