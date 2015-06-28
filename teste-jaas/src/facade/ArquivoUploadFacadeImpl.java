package facade;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import model.ArquivoCarga;
import model.ArquivoUpload;
import model.Parametro;
import model.TipoParametro;

import org.apache.commons.io.FileUtils;
import org.primefaces.model.UploadedFile;

import dao.ParametroDAO;

@Stateless
public class ArquivoUploadFacadeImpl implements ArquivoUploadFacade{
	
	@EJB
	private ParametroDAO parametroDAO;

	@Override
    public void upload(ArquivoCarga arquivoCarga) throws IOException {
		ArquivoUpload au = new ArquivoUpload(arquivoCarga.getArquivo());
		au.setPasta(arquivoCarga.getPasta());
		this.upload(au);
	}
	
	@Override
    public void upload(ArquivoUpload arquivoUpload) throws IOException {
    	
    	UploadedFile file = arquivoUpload.getArquivo();
    	String fileName = arquivoUpload.getNome();
    	String diretorio = buscarDiretorio();
    	String pasta = arquivoUpload.getPasta();
    	    	
    	String path = diretorio + pasta + "/" + fileName;
    	    	
    	InputStream inputStream = file.getInputstream();
    	
    	File newFile = new File(diretorio + pasta + "/" + fileName);
    	FileOutputStream outputStream = FileUtils.openOutputStream(newFile);

    	//new FileOutputStream(path);
         
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
	
	@Override
	public void moveArquivo(ArquivoCarga arquivoCarga) throws Exception {

		String fileName = arquivoCarga.getNome();
		
		try{
			String pathFrom = buscarDiretorio() + arquivoCarga.getPasta() + "/" + fileName;
			String pathTo 	= buscarDiretorio() + arquivoCarga.getCpfCnpjCliente() + "/";
			
			System.out.println("Movendo arquivo de: "+pathFrom);
			System.out.println("para: "+pathTo);
			
			File fileFrom = new File(pathFrom);
			File fileTo = new File(pathTo);
			
			FileUtils.moveFileToDirectory(fileFrom, fileTo, true);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao mover arquivo " + fileName, e.getCause());
		}
				 
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