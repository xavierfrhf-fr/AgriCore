package agricore.projet.dto.utilisateur.response;

import java.util.List;

import org.springframework.beans.BeanUtils;

import agricore.projet.dto.zone.response.ZoneResponseDTO;
import agricore.projet.model.Fermier;

public class FermierWithZoneResponseDTO {
	private Integer Id;
	private String login;
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
