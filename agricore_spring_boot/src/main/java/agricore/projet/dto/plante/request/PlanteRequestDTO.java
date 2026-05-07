package agricore.projet.dto.plante.request;

import agricore.projet.model.EspecePlante;

//contient ce que le fermier envoie pour ajouter une plante
public class PlanteRequestDTO {

	private EspecePlante espece;

	public PlanteRequestDTO(EspecePlante espece) {
		this.espece = espece;
	}

	public PlanteRequestDTO() {}

	public EspecePlante getEspece() {
		return espece;
	}
	public void setEspece(EspecePlante espece) {
		this.espece = espece;
	}

		
}
