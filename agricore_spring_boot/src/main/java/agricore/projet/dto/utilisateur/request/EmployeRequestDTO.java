package agricore.projet.dto.utilisateur.request;

import org.springframework.beans.BeanUtils;
import agricore.projet.model.Employe;

public class EmployeRequestDTO {
	
	private String login;
	private String password;
	private String nom;
	private String prenom;
	
	
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

	public static EmployeRequestDTO convert (Employe employe) {
		EmployeRequestDTO request = new EmployeRequestDTO();
		BeanUtils.copyProperties(employe, request);
		return request;
	}

}
