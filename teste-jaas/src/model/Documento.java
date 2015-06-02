package model;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Documento implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "gen_documento")
	@SequenceGenerator(name = "gen_documento", sequenceName = "seq_gen_sequence", allocationSize = 1)
	private long id;
	private String titulo;
	private String descricao;
	
	@ManyToOne
	private Cliente cliente;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataInclusao;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataAlteracao;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataExclusao;
	
	@OneToOne
	private TipoDocumento tipoDocumento;
	
	@OneToMany
	private List<ArquivoAnexo> anexos;
	
	@OneToOne
	private Usuario usuarioCriacao;
	
	@OneToOne
	private Usuario usuarioAlteracao;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Calendar getDataInclusao() {
		return dataInclusao;
	}
	public void setDataInclusao(Calendar dataInclusao) {
		this.dataInclusao = dataInclusao;
	}
	public Calendar getDataAlteracao() {
		return dataAlteracao;
	}
	public void setDataAlteracao(Calendar dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}
	public Usuario getUsuarioCriacao() {
		return usuarioCriacao;
	}
	public void setUsuarioCriacao(Usuario usuarioCriacao) {
		this.usuarioCriacao = usuarioCriacao;
	}
	public Usuario getUsuarioAlteracao() {
		return usuarioAlteracao;
	}
	public void setUsuarioAlteracao(Usuario usuarioAlteracao) {
		this.usuarioAlteracao = usuarioAlteracao;
	}
	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public Calendar getDataExclusao() {
		return dataExclusao;
	}
	public void setDataExclusao(Calendar dataExclusao) {
		this.dataExclusao = dataExclusao;
	}
	public List<ArquivoAnexo> getAnexos() {
		return anexos;
	}
	public void setAnexos(List<ArquivoAnexo> anexos) {
		this.anexos = anexos;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	 
}
