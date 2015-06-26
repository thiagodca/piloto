package model;

import org.primefaces.model.UploadedFile;

public class ArquivoCarga {

	public ArquivoCarga(UploadedFile arquivo) {
		this.arquivo = arquivo;
		
		inicializar();
		
	}
	
	private String nome;
	
	private String caminho;
	
	private String codigoTipo;
	
	private String cpfCnpjCliente;
	
	private String descricao;
	
	private UploadedFile arquivo;
	
	private long tamanho;
	
	private Usuario usuario;
	
	private String pasta;

	private String msgErro;
	
	private long cargaArquivoId;
	
	private void inicializar(){
	     
	   String filename = arquivo.getFileName();
	   this.caminho = filename;
       this.nome = filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1);

	}
	
	public void carregarParametros() throws Exception {
       String[] clienteTipo = this.nome.split("_");
       
       this.tamanho = arquivo.getSize();
       
       try{
    	   this.cpfCnpjCliente = clienteTipo[0];
    	   this.codigoTipo = clienteTipo[1];
                  	
       } catch (Exception e) {
    	   throw new Exception("Nome do arquivo fora do padrao.");
       }
       
       // Descricao nao obrigatorio
       try{
    	   this.descricao = clienteTipo[3];
       } catch (Exception e) {}
       
	}
	
	public String getNome() {
		return this.nome;
	}

	public String getCaminho() {
		return this.caminho;
	}

	public UploadedFile getArquivo() {
		return arquivo;
	}

	public String getCpfCnpjCliente() {
		return cpfCnpjCliente;
	}

	public String getCodigoTipo() {
		return codigoTipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public long getTamanho() {
		return tamanho;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getMsgErro() {
		return msgErro;
	}

	public void setMsgErro(String msgErro) {
		this.msgErro = msgErro;
	}

	public String getPasta() {
		return pasta;
	}

	public void setPasta(String pasta) {
		this.pasta = pasta;
	}

	public long getCargaArquivoId() {
		return cargaArquivoId;
	}

	public void setCargaArquivoId(long cargaArquivoId) {
		this.cargaArquivoId = cargaArquivoId;
	}	
}
