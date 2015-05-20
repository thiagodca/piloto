package mb;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import util.UtilData;
import model.ArquivoAnexo;
import model.Documento;
import facade.ArquivoAnexoFacade;
import facade.DocumentoFacade;

@ManagedBean(name="arquivoAnexoBean")
@RequestScoped
public class ArquivoAnexoBean {

	private static final String LISTAR_DOCUMENTOS 	= "listarDocumentos";
    private static final String INCLUIR_ARQUIVOANEXO= "incluirArquivoAnexo";
    private static final String CONTINUAR_NA_PAGINA = null;

	@EJB
	private ArquivoAnexoFacade arquivoAnexoFacade;
	
	@EJB
	private DocumentoFacade documentoFacade;
	
	private ArquivoAnexo arquivoAnexo;
    
    private Documento documento;
    
	public ArquivoAnexo getArquivoAnexo(){
		if(arquivoAnexo == null){
			arquivoAnexo = new ArquivoAnexo();
		}
		return arquivoAnexo;
	}
	
    public String incluirArquivoAnexoInicio(){
        return INCLUIR_ARQUIVOANEXO;
    }
        
    public String incluirArquivoAnexoFim(){
        try {
        	
            Documento umDocumento = documentoFacade.buscar(documento.getId());
            arquivoAnexo.setDocumento(umDocumento);
            arquivoAnexo.setDataInclusao(UtilData.getDataAtual());
            arquivoAnexoFacade.salvar(arquivoAnexo);
            
        } catch (EJBException e) {
            sendErrorMessageToUser("Error. Check if the weight is above 0 or call the adm");
 
            return CONTINUAR_NA_PAGINA;
        }       
 
        sendInfoMessageToUser("Operation Complete: Create");
 
        return INCLUIR_ARQUIVOANEXO;
    }
	
    public String listarDocumentos(){
        return LISTAR_DOCUMENTOS;
    }
    
    public List<ArquivoAnexo> getListaArquivosAnexos(){
    	if(documento != null){
    		return arquivoAnexoFacade.listarTodos(documento.getId());	
    	}
		return null;
	}
    
	public void setArquivoAnexo(ArquivoAnexo arquivoAnexo){
		this.arquivoAnexo = arquivoAnexo;
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

	public Documento getDocumento() {
		if(documento == null){
			documento = new Documento();
		}
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}
}


