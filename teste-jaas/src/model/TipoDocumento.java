package model;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class TipoDocumento implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "gen_tpdocumento")
	@SequenceGenerator(name = "gen_tpdocumento", sequenceName = "seq_gen_sequence", allocationSize = 1)
	private long id;
	
	@Column(nullable = false)
	private String nomeTipo;
	
	private String descricao;
	
	private String codigo;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNomeTipo() {
		return nomeTipo;
	}
	public void setNomeTipo(String nomeTipo) {
		this.nomeTipo = nomeTipo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public boolean equals(Object object) {
		System.out.println(">> equals()");
		
		if(object instanceof String){
			return (codigo.equals((String) object));
		}
		
        return (object instanceof TipoDocumento) && (id != 0) 
             ? id == ((TipoDocumento) object).id 
             : (object == this);
    }
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
		 
}
