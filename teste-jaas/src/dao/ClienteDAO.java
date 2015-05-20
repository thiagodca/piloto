package dao;
import javax.ejb.Stateless;

import model.Cliente;

@Stateless
public class ClienteDAO extends GenericDAO<Cliente> {

	public ClienteDAO() {
		super(Cliente.class);
	}

}