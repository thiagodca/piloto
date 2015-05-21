package main;
import model.Perfil;
import model.Usuario;
import facade.PerfilFacade;
import facade.UsuarioFacade;


public class Teste {

	public static void main(String[] args) {
		
		Usuario usuario = new Usuario();
		usuario.setLogin("teste");
		usuario.setSenha("6f2fddcf0b9649838ab0a7b458e2f0dfca3e3a7e62bf30ca2b128b56a723d268");
		
		Perfil perfil = new PerfilFacade().buscar("DIGITADOR");
		usuario.setPerfil(perfil);
		
		new UsuarioFacade().inserir(usuario);
		
	}

}
