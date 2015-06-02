package service;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import model.Documento;
import facade.DocumentoFacade;
 
@ManagedBean(name = "documentoService")
@ApplicationScoped
public class DocumentoService {

	@EJB
	DocumentoFacade documentoFacade;
	
	public List<Documento> obterDocumentos(){
		return documentoFacade.listarTodos();
	}
}
