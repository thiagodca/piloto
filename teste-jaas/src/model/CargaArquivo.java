package model;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class CargaArquivo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "gen_ancargaarquivo")
	@SequenceGenerator(name = "gen_ancargaarquivo", sequenceName = "seq_gen_sequence", allocationSize = 1)
	private long id;

	private String descricao;
	
	@OneToMany(mappedBy = "cargaArquivo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ArquivoTemporario> arquivoTemporario;
	
	private long tamanho;
	
	private SituacaoCarga situacao;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataInclusao;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataAlteracao;

	@OneToOne
	private Usuario usuarioInclusao;

	@OneToOne
	private Usuario usuarioAlteracao;

	@OneToMany
	private List<Documento> documento;
	
	public long getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<ArquivoTemporario> getArquivoTemporario() {
		return arquivoTemporario;
	}

	public void setArquivoTemporario(List<ArquivoTemporario> arquivoTemporario) {
		this.arquivoTemporario = arquivoTemporario;
	}

	public long getTamanho() {
		return tamanho;
	}

	public void setTamanho(long tamanho) {
		this.tamanho = tamanho;
	}

	public SituacaoCarga getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoCarga situacao) {
		this.situacao = situacao;
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

	public Usuario getUsuarioInclusao() {
		return usuarioInclusao;
	}

	public void setUsuarioInclusao(Usuario usuarioInclusao) {
		this.usuarioInclusao = usuarioInclusao;
	}

	public Usuario getUsuarioAlteracao() {
		return usuarioAlteracao;
	}

	public void setUsuarioAlteracao(Usuario usuarioAlteracao) {
		this.usuarioAlteracao = usuarioAlteracao;
	}

	public List<Documento> getDocumento() {
		return documento;
	}

	public void setDocumento(List<Documento> documento) {
		this.documento = documento;
	}

	public void setId(long id) {
		this.id = id;
	}
}
