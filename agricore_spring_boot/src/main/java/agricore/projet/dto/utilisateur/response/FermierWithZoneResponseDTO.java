package agricore.projet.dto.utilisateur.response;

import org.springframework.beans.BeanUtils;

import agricore.projet.model.Fermier;

public class FermierWithZoneResponseDTO {
	private Integer fermierId;
	private Integer ZoneId;
	public Integer getFermierId() {
		return fermierId;
	}
	public Integer getZoneId() {
		return ZoneId;
	}
	public void setFermierId(Integer fermierId) {
		this.fermierId = fermierId;
	}
	public void setZoneId(Integer zoneId) {
		ZoneId = zoneId;
	}
	
	public static FermierWithZoneResponseDTO convert (Fermier fermier) {
		FermierWithZoneResponseDTO response = new FermierWithZoneResponseDTO();
		BeanUtils.copyProperties(fermier, response);
		response.setZoneId(fermier.getId());
		return response;
		
	}

}
