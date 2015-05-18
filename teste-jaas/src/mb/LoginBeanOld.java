package mb;
import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import sun.security.krb5.KrbException;
import util.CriptografiaUtil;

@ManagedBean
@SessionScoped
public class LoginBeanOld {

	private String userid;
	private String password;
	private boolean usuarioLogado;
	
	//@EJB
	//private BuscaUsuarioDAO buscaUsuarioDAO;
	
	public String login() {
		
		if(this.usuarioLogado){
			return "sucessoLogin";
		}
		
		if (userid == null || userid.trim().isEmpty()
				|| password == null || password.trim().isEmpty()) {
			return "erroLogin";
		}else {
			//Busca usuário por userName
			/*Usuario usuario = buscaUsuarioDAO. buscarUsuarioPorUserName(userName);
			
			if (usuario == null) {
				return "erroLogin";
			}
			
			//Criptografa a senha informada na página de login
			String senhaCriptografada = CriptografiaUtil. gerarSenhaSHA256(password);
			if (!usuario.getSenha().equals(senhaCriptografada)) {
				return "erroLogin";
			}*/
			System.out.println("userid="+userid);
			System.out.println("password="+password);
			String senhaCriptografada = CriptografiaUtil. gerarSenhaSHA256(password);
			System.out.println("SenhaCriptografada="+senhaCriptografada);
			try {
				//Registra no domínio de segurança o usuário e a senha
				FacesContext fc = FacesContext.getCurrentInstance();
				HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
				request.login(userid, password);
			} catch (Throwable e) {
				Throwable t = e.getCause();
				  if (t instanceof IOException) {
				      System.out.println( "Network issue");
				  } else if (t instanceof KrbException) {
				      System.out.println( "Kerberos issue");
				  } else if (t != null) {
				      System.out.println("Not found="+t.getClass());
				  }

				e.printStackTrace();
				return "erroLogin";
			}
		}
		this.usuarioLogado = true;
		return "sucessoLogin";
	}
	
	public String logout() {
		System.out.println("Saindo do sistema");
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			HttpServletRequest r = (HttpServletRequest) fc.getExternalContext().getRequest();
			r.logout();
			this.usuarioLogado = false;
		} catch (ServletException e) {
			e.printStackTrace();
		}
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.invalidate();
		return "logout";
	}
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}


