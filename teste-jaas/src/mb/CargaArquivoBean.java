package mb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import model.ArquivoTemporario;
import model.CargaArquivo;
import model.Usuario;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import facade.ArquivoTemporarioFacade;
import facade.ArquivoUploadFacade;
import facade.CargaArquivoFacade;
import facade.CargaAutomaticaFacade;
import facade.TipoDocumentoFacade;
import facade.UsuarioFacade;

@ManagedBean(name="cargaArquivoBean")
@ViewScoped
public class CargaArquivoBean{

    @EJB
    private ArquivoUploadFacade arquivoUploadFacade;

    @EJB
    private ArquivoTemporarioFacade arquivoTemporarioFacade;
    
    @EJB
    private CargaAutomaticaFacade cargaAutomaticaFacade;

    @EJB
    private CargaArquivoFacade cargaArquivoFacade;

    @EJB
    private TipoDocumentoFacade tipoDocumentoFacade;

    @EJB
    private UsuarioFacade usuarioFacade;

    private UploadedFile arquivo;
    
	private long cargaArquivoId;
	
	private CargaArquivo cargaArquivo;
	
	private List<ArquivoTemporario> listaArquivosTemporarios;
	
	private List<ArquivoTemporario> listaArquivosTemporariosErro;
	
	private boolean cargaIniciada;
    
	private ArquivoTemporario arquivoTempErro;
	
	private List<UploadedFile> arquivosUpload;
	
    /**
     * Construtor
     */
    public CargaArquivoBean(){
    	System.out.println(">> CargaArquivoBean()");
    	System.out.println("ID:"+cargaArquivoId);
    	System.out.println("cargaArquivo="+cargaArquivo);
    }
    
    // Abrir a tela de carga a partir da pesquisa
    public void abrir(){
    	System.out.println(">> abrir()");
    	System.out.println("cargaArquivoId="+cargaArquivoId);
    	
    	montaDadosTela();
    }
    
    public void inicio(){
    	System.out.println(">> inicio()");
    	
    	// Grava uma nova carga
    	cargaArquivo = cargaArquivoFacade.iniciarCarga("CargaAutomatica", getUsuarioLogado());    	
    	
    	cargaArquivoId = cargaArquivo.getId();
    	System.out.println("cargaArquivoId="+cargaArquivoId);
    	
    }
    
    public void carregaDocumentos(FileUploadEvent event){
    	System.out.println(">> carregaDocumentos()");
    	System.out.println("ID:"+cargaArquivoId);
    	
    	this.arquivo = event.getFile();
    	System.out.println("arquivo.getFileName(): "+arquivo.getFileName());
    	
    	if(arquivosUpload==null){
    		arquivosUpload = new ArrayList<UploadedFile>();
    	}
    	arquivosUpload.add(arquivo);
    }

    public void upload(){
    	System.out.println(">> upload()");
    	System.out.println("ID:"+cargaArquivoId);
    	
    	for(UploadedFile file : arquivosUpload){
    		cargaAutomaticaFacade.uploadArquivoCarga(file, cargaArquivoId);
    	}
    	
    	montaDadosTela();
    }
    
	public void montaDadosTela() {
		System.out.println(">> montaDadosTela()");
		System.out.println("cargaArquivoId: "+cargaArquivoId);
		
		cargaArquivo = cargaArquivoFacade.buscarCargaArquivoPorId(cargaArquivoId);
    	listaArquivosTemporarios = arquivoTemporarioFacade.listarPorCargaEIndErro(cargaArquivoId, "N");
    	listaArquivosTemporariosErro = arquivoTemporarioFacade.listarPorCargaEIndErro(cargaArquivoId, "S");
    	
    	cargaIniciada = true;
	}
    
    public void finalizarCarga(long cargaArquivoId) {
    	System.out.println(">> finalizarCarga()");
    	System.out.println("cargaArquivoId: "+cargaArquivoId);
    	
    	cargaArquivoFacade.finalizarCarga(cargaArquivoId, getUsuarioLogado());
    	
    	cargaIniciada = false;
    	listaArquivosTemporarios = new ArrayList<ArquivoTemporario>();
    	listaArquivosTemporariosErro = new ArrayList<ArquivoTemporario>();
    }

    public void gravaDocumentos(){
    	System.out.println(">> gravaDocumentos()");
    	
    	try{
    		cargaAutomaticaFacade.gravarDocumentos(cargaArquivoId);
    	} catch (Exception e) {
    		sendErrorMessageToUser(e.getMessage());
    	}
    	
    	finalizarCarga(cargaArquivoId);
    	
    	sendInfoMessageToUser("Carga finalizada com sucesso");
    	
    }
    
    /**
     * Revalida os dados do arquivo e persiste as alteracoes
     * @return
     */
    public void atualizarArqTemporarioFim(){
    	System.out.println(">> atualizarArqTemporarioFim()");
        try {
        	ArquivoTemporario umArquivo = arquivoTemporarioFacade.buscar(arquivoTempErro.getId());
        	
        	System.out.println("tipo: "+arquivoTempErro.getCodigoTipo());
        	
        	umArquivo.setCodigoTipo(arquivoTempErro.getCodigoTipo());
        	umArquivo.setCpfCnpjCliente(arquivoTempErro.getCpfCnpjCliente());
        	umArquivo.setDescricao(arquivoTempErro.getDescricao());

        	cargaAutomaticaFacade.revalidarArquivoTemporario(umArquivo);
        	
        } catch (EJBException e) {
        	System.out.println("ERRO ao atualizar");
            sendErrorMessageToUser("Error. Check if the weight is above 0 or call the adm");
        }
 
        // recarrega as tabelas
        montaDadosTela();
        
        sendInfoMessageToUser("Arquivo atualizado com sucesso.");

        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dialogAtualizarTemporario').hide();");
    }

    /**
     * Persiste a exclusao do documento
     * @return
     */
    public void deletarArqTemporarioFim(){
    	System.out.println(">> deletarArqTemporarioFim()");
        try {
        	ArquivoTemporario umArquivo = arquivoTemporarioFacade.buscar(arquivoTempErro.getId());

            arquivoTemporarioFacade.deletar(umArquivo);
            
        } catch (EJBException e) {
            sendErrorMessageToUser("Error. Call the ADM");
            System.out.println("ERRO ao deletar");
            //return CONTINUAR_NA_PAGINA;
        }           
 
        montaDadosTela();
        
        sendInfoMessageToUser("Documento excluido com sucesso.");
 
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dialogDeletarTemporario').hide();");
    }

    private void sendInfoMessageToUser(String message){
        FacesContext context = getContext();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
    }
 
    private void sendErrorMessageToUser(String message){
        FacesContext context = getContext();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
    }
 
    private FacesContext getContext() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context;

    }

    /**
     * Retorna o usuario logado no sistema
     * 
     * @return Usuario
     */
    private Usuario getUsuarioLogado(){
    	System.out.println(">> getUsuarioLogado()");
    	String login = getContext().getExternalContext().getUserPrincipal().getName();
    	Usuario usuario = usuarioFacade.buscar(login);
    	return usuario;
    }
    
    public UploadedFile getArquivo() {
		return arquivo;
	}

	public void setArquivo(UploadedFile arquivo) {
		this.arquivo = arquivo;
	}

	public long getCargaArquivoId() {
		return cargaArquivoId;
	}

	public void setCargaArquivoId(long cargaArquivoId) {
		this.cargaArquivoId = cargaArquivoId;
	}

	public CargaArquivo getCargaArquivo() {
		System.out.println(">> getCargaArquivo()");
		if(cargaArquivo==null){
			cargaArquivo = new CargaArquivo();
		}
		System.out.println(""+cargaArquivo);
		return cargaArquivo;
	}

	public void setCargaArquivo(CargaArquivo cargaArquivo) {
		this.cargaArquivo = cargaArquivo;
	}

	public List<ArquivoTemporario> getListaArquivosTemporarios() {
		return listaArquivosTemporarios;
	}

	public void setListaArquivosTemporarios(
			List<ArquivoTemporario> listaArquivosTemporarios) {
		this.listaArquivosTemporarios = listaArquivosTemporarios;
	}

	public boolean isCargaIniciada() {
		return cargaIniciada;
	}

	public void setCargaIniciada(boolean cargaIniciada) {
		this.cargaIniciada = cargaIniciada;
	}

	public List<ArquivoTemporario> getListaArquivosTemporariosErro() {
		return listaArquivosTemporariosErro;
	}

	public void setListaArquivosTemporariosErro(
			List<ArquivoTemporario> listaArquivosTemporariosErro) {
		this.listaArquivosTemporariosErro = listaArquivosTemporariosErro;
	}

	public ArquivoTemporario getArquivoTempErro() {
		return arquivoTempErro;
	}

	public void setArquivoTempErro(ArquivoTemporario arquivoTempErro) {
		this.arquivoTempErro = arquivoTempErro;
	}
}


