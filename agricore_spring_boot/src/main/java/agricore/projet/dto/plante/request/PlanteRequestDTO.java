package agricore.projet.dto.plante.request;

import agricore.projet.model.EspecePlante;

//contient ce que le fermier envoie pour ajouter une plante
public class PlanteRequestDTO {
 
    
	private EspecePlante espece;
    private Integer zoneId; 
	

	public PlanteRequestDTO(EspecePlante espece, Integer zoneId) {
		
		this.espece = espece;
		this.zoneId = zoneId;
		
	}

	public PlanteRequestDTO() {}

	
	public EspecePlante getEspece() {
		return espece;
	}
	public void setEspece(EspecePlante espece) {
		this.espece = espece;
	}
	public Integer getZoneId() {
		return zoneId;
	}
	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}

		
}
