package model;
import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Usuario implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String login;

	@Column(nullable = false)
	private String senha;
	
	@OneToOne(cascade=CascadeType.ALL)
    @JoinTable(name="usuario_perfil",
              joinColumns={@JoinColumn(name="login",  
               referencedColumnName="login")},  
              inverseJoinColumns={@JoinColumn(name="nomeperfil",   
               referencedColumnName="nomeperfil")})
	private Perfil perfil;
	
	private String nome;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataInclusao;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataAlteracao;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataExclusao;

	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Perfil getPerfil() {
		return perfil;
	}
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
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
	public Calendar getDataAlteracao() {
		return dataAlteracao;
	}
	public void setDataAlteracao(Calendar dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}
	public Calendar getDataExclusao() {
		return dataExclusao;
	}
	public void setDataExclusao(Calendar dataExclusao) {
		this.dataExclusao = dataExclusao;
	}
	 
}
