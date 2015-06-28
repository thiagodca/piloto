package negocio;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import model.ArquivoTemporario;
import validation.Validator;
import dao.ArquivoTemporarioDAO;

@Stateless
public class ArquivoTemporarioBusiness implements IArquivoTemporarioBusiness {

	@EJB
	ArquivoTemporarioDAO arquivoTemporarioDAO;
	
	@EJB
	Validator va;
			
    /* (non-Javadoc)
	 * @see negocio.IArquivoTemporarioBusiness#revalidarArquivoTemporario(model.ArquivoTemporario)
	 */
    @Override
	public void revalidarArquivoTemporario(ArquivoTemporario arquivoTemporario) {
    	
    	try{
    		System.out.println("ArquivoId: "+arquivoTemporario.getId());
    		
			va.validate(arquivoTemporario);

			arquivoTemporario.setIndErro("N");
			arquivoTemporario.setMsgErro(null);
			
    	} catch(Exception e){
    		e.printStackTrace();

    		arquivoTemporario.setMsgErro("" + e.getMessage());
    		arquivoTemporario.setIndErro("S");
    	}

    	arquivoTemporarioDAO.update(arquivoTemporario);
    }

}
