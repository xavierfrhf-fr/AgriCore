package agricore.projet.dto.utilisateur.response;

import org.springframework.beans.BeanUtils;
import agricore.projet.model.Fermier;

public class FermierResponseDTO {
	
	private Integer id;
	private String login;
	private String nom;
	private String prenom;

	public Integer getId() {
		return id;
	}
	public String getLogin() {
		return login;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setLogin(String login) {
		this.login = login;
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

	public static FermierResponseDTO convert (Fermier fermier) {
		FermierResponseDTO response = new FermierResponseDTO();
		BeanUtils.copyProperties(fermier, response);
		return response;
	}

}
