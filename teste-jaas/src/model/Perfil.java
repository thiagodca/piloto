package model;
import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

@Entity
public class Perfil implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String nomePerfil;
	
	@OneToMany(cascade=CascadeType.ALL)  
    @JoinTable(name="usuario_perfil",  
              joinColumns={@JoinColumn(name="nomeperfil", 
               referencedColumnName="nomeperfil")},  
              inverseJoinColumns={@JoinColumn(name="login", 
                referencedColumnName="login")})  
	private List<Usuario> listaDeUsuarios;
	
	public String getNomePerfil() {
		return nomePerfil;
	}
	public void setNomePerfil(String nomePerfil) {
		this.nomePerfil = nomePerfil;
	}
	public List<Usuario> getListaDeUsuarios() {
		return listaDeUsuarios;
	}
	public void setListaDeUsuarios(List<Usuario> listaDeUsuarios) {
		this.listaDeUsuarios = listaDeUsuarios;
	}
	 
}
