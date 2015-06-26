package mb;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import model.CargaArquivo;
import model.SituacaoCarga;
import facade.CargaArquivoFacade;

@ManagedBean(name="pesquisaCargaBean")
@RequestScoped
public class PesquisaCargaBean extends mb.ManagedBean{

	private Long numeroLote;
	
	private String descricao;
	
	private String usuario;
	
	private String dataCargaInicio;
	
	private String dataCargaFim;
	
	private SituacaoCarga situacao;
	
	private List<CargaArquivo> listaCargas;
	
	@EJB
	private CargaArquivoFacade cargaArquivoFacade;
	
	/**
     * Construtor
     */
    public PesquisaCargaBean(){
    	System.out.println(">> PesquisaCargaBean()");
    	
    	numeroLote = null;
    	descricao = null;
    	usuario = null;
    	dataCargaInicio = null;
    	dataCargaFim = null;
    	situacao = SituacaoCarga.PENDENTE;
    }

    public String pesquisar(){
    	System.out.println(">> pesquisar()");
  	
    	if(usuario==null || usuario.isEmpty()){
    		if(!isUsuarioAdmin()){
    			usuario = getUsuarioLogado().getLogin();
    		}
    	}
    	
    	listaCargas = cargaArquivoFacade.filtrar(numeroLote, descricao, situacao, dataCargaInicio, dataCargaFim, usuario);
    	
    	return "listarCargasArquivo";
    }
    
    public List<CargaArquivo> getListaCargas() {
		return listaCargas;
	}

	public void setListaCargas(List<CargaArquivo> listaCargas) {
		this.listaCargas = listaCargas;
	}

	public Long getNumeroLote() {
		return numeroLote;
	}

	public void setNumeroLote(Long numeroLote) {
		this.numeroLote = numeroLote;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public SituacaoCarga getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoCarga situacao) {
		this.situacao = situacao;
	}

	public String getDataCargaInicio() {
		return dataCargaInicio;
	}

	public void setDataCargaInicio(String dataCargaInicio) {
		this.dataCargaInicio = dataCargaInicio;
	}

	public String getDataCargaFim() {
		return dataCargaFim;
	}

	public void setDataCargaFim(String dataCargaFim) {
		this.dataCargaFim = dataCargaFim;
	}

	public SituacaoCarga[] getSituacoes() {
		return SituacaoCarga.values();
	}

}