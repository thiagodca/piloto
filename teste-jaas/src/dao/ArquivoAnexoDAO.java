package dao;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;

import model.ArquivoAnexo;

@Stateless
public class ArquivoAnexoDAO extends GenericDAO<ArquivoAnexo> {

	public ArquivoAnexoDAO() {
		super(ArquivoAnexo.class);
	}
	
	public List<ArquivoAnexo> listarPorDocumento(long idDocumento){
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("id", idDocumento);
		List<ArquivoAnexo> result = super.findNamedQuery("ArquivoAnexo.listarPorDocumento", parametros);
		return result;
	}

}