package model;
import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQuery(name="ArquivoAnexo.listarPorDocumento", query="select a from ArquivoAnexo a where a.documento.id = :id")
public class ArquivoAnexo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "gen_andocumento")
	@SequenceGenerator(name = "gen_andocumento", sequenceName = "seq_gen_sequence", allocationSize = 1)
	private long id;
	private String nome;
	
	@ManyToOne
	private Documento documento;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataInclusao;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataExclusao;

	@OneToOne
	private Usuario usuarioCriacao;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Calendar getDataInclusao() {
		return dataInclusao;
	}
	public void setDataInclusao(Calendar dataInclusao) {
		this.dataInclusao = dataInclusao;
	}
	public Calendar getDataExclusao() {
		return dataExclusao;
	}
	public void setDataExclusao(Calendar dataExclusao) {
		this.dataExclusao = dataExclusao;
	}
	public Documento getDocumento() {
		return documento;
	}
	public void setDocumento(Documento documento) {
		this.documento = documento;
	}
	public Usuario getUsuarioCriacao() {
		return usuarioCriacao;
	}
	public void setUsuarioCriacao(Usuario usuarioCriacao) {
		this.usuarioCriacao = usuarioCriacao;
	}	 
}
