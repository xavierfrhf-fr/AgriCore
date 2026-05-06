package agricore.projet.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Client extends Utilisateur {

	@Column(nullable = false)
	private String mail;

	public Client() {}

	public Client(Integer id, String login, String password,  String nom, String prenom, String mail) {
		super(id, login, password, nom, prenom);
		this.mail = mail;
	}

	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	

	@Override
	public String toString() {
		return "Client [id=" + id + ", login=" + login + ", password=" + password + "]";
	}

	
}
