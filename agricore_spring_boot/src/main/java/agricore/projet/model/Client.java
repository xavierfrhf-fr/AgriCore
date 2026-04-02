package agricore.model;

import jakarta.persistence.Entity;

@Entity
public class Client extends Utilisateur {
	
	public Client() {}

	public Client(Integer id, String login, String password) {
		super(id, login, password);
	}
	

	@Override
	public String toString() {
		return "Client [id=" + id + ", login=" + login + ", password=" + password + "]";
	}

	
}
