package agricore.projet.dto.utilisateur.response;

import java.util.List;

import org.springframework.beans.BeanUtils;

import agricore.projet.dto.zone.response.ZoneResponseDTO;
import agricore.projet.model.Fermier;

public class FermierWithZoneResponseDTO {
	private Integer Id;
	private String login;
	private String nom;
	private String prenom;
	private List<ZoneResponseDTO> zones;
	
	
	public Integer getId() {
		return Id;
	}
	
	public String getLogin() {
		return login;
	}

	public List<ZoneResponseDTO> getZones() {
		return zones;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setZones(List<ZoneResponseDTO> zones) {
		this.zones = zones;
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

	public static FermierWithZoneResponseDTO convert (Fermier fermier) {
		FermierWithZoneResponseDTO response = new FermierWithZoneResponseDTO();
		BeanUtils.copyProperties(fermier, response);
		response.setZones(fermier.getZone()
				.stream()
				.map(ZoneResponseDTO::convert)
				.toList());
		return response;
		
	}

}
