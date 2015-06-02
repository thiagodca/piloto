package model;

import org.primefaces.model.UploadedFile;

public class ArquivoUpload {

	public ArquivoUpload(UploadedFile arquivo){
		this.arquivo = arquivo;
		
		obtemNomeCaminho();
	}
	
	private String nome;
	
	private String caminho;
	
	private void obtemNomeCaminho(){
	     
	   String filename = arquivo.getFileName();
	   this.caminho = filename;
       this.nome = filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1);
   
	}
	
	private String codigoCliente;
	
	private UploadedFile arquivo;

	public String getNome() {
		return this.nome;
	}

	public String getCaminho() {
		return this.caminho;
	}

	public UploadedFile getArquivo() {
		return arquivo;
	}

	public String getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}	
}
