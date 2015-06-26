package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class ArquivoTemporario {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "gen_anarqtemp")
	@SequenceGenerator(name = "gen_anarqtemp", sequenceName = "seq_gen_sequence", allocationSize = 1)
	private long id;
	
	private String nome;
	
	private String caminho;
	
	private String codigoTipo;
	
	private String cpfCnpjCliente;
	
	private String descricao;
	
	private long tamanho;
	
	private String pasta;

	private String msgErro;
	
	private String indErro;

	@ManyToOne
	private CargaArquivo cargaArquivo;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public String getCodigoTipo() {
		return codigoTipo;
	}

	public void setCodigoTipo(String codigoTipo) {
		this.codigoTipo = codigoTipo;
	}

	public String getCpfCnpjCliente() {
		return cpfCnpjCliente;
	}

	public void setCpfCnpjCliente(String cpfCnpjCliente) {
		this.cpfCnpjCliente = cpfCnpjCliente;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public long getTamanho() {
		return tamanho;
	}

	public void setTamanho(long tamanho) {
		this.tamanho = tamanho;
	}

	public String getPasta() {
		return pasta;
	}

	public void setPasta(String pasta) {
		this.pasta = pasta;
	}

	public String getMsgErro() {
		return msgErro;
	}

	public void setMsgErro(String msgErro) {
		this.msgErro = msgErro;
	}

	public long getId() {
		return id;
	}

	public CargaArquivo getCargaArquivo() {
		return cargaArquivo;
	}

	public void setCargaArquivo(CargaArquivo cargaArquivo) {
		this.cargaArquivo = cargaArquivo;
	}

	public String getIndErro() {
		return indErro;
	}

	public void setIndErro(String indErro) {
		this.indErro = indErro;
	}

	public void setId(long id) {
		this.id = id;
	}
	
}
