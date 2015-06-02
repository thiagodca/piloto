package util;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import model.TipoDocumento;
import facade.TipoDocumentoFacade;

@ManagedBean
@RequestScoped
public class ObjectConverter implements Converter {

    @EJB
    private TipoDocumentoFacade tipoDocumentoFacade;
	
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try{
        	TipoDocumento tp = tipoDocumentoFacade.buscar(Long.valueOf(value).longValue());
        	System.out.println(">> getAsObject()="+tp.getId());
        	return tp;
        } catch (NumberFormatException e){
        	System.out.println(">> getAsObject()=NULL");
        	return new TipoDocumento();
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        try{
        	//System.out.println(">> getAsString()="+((TipoDocumento) value).getNomeTipo());
        	return String.valueOf(((TipoDocumento) value).getId());
        	//return String.valueOf(((TipoDocumento) value).getNomeTipo());
        } catch (NumberFormatException e){
        	return "";
        }
    }
}
