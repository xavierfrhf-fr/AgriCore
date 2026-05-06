package agricore.projet.dto.utilisateur.request;

import org.springframework.beans.BeanUtils;
import agricore.projet.model.Client;

public class ClientRequestDTO {
	
	private String login;
	private String password;
	private String nom;
	private String prenom;
	private String email;
	
	public String getLogin() {
		return login;
	}
	public String getPassword() {
		return password;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public static ClientRequestDTO convert (Client client) {
		ClientRequestDTO request = new ClientRequestDTO();
		BeanUtils.copyProperties(client, request);
		return request;
	}

}
