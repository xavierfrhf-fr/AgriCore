package agricore.projet.dto.plante.request;

import java.time.LocalDate;
import java.time.LocalDateTime;

import agricore.projet.model.EspecePlante;

//contient ce que le fermier envoie pour ajouter une plante
public class PlanteRequestDTO {
 
    private LocalDate datePlantation;
	private LocalDate dateRecolte;
	private EspecePlante espece;
    private Integer zoneId; 
	private LocalDateTime dernierUpdate;
	private double humidite;

	public PlanteRequestDTO(LocalDate datePlantation, LocalDate dateRecolte, EspecePlante espece, Integer zoneId, LocalDateTime dernierUpdate, double humidite) {
		this.datePlantation = datePlantation;
		this.dateRecolte = dateRecolte;
		this.espece = espece;
		this.zoneId = zoneId;
		this.dernierUpdate = dernierUpdate;
		this.humidite = humidite;
	}

	public PlanteRequestDTO() {}

	public LocalDate getDatePlantation() {
		return datePlantation;
	}
	public void setDatePlantation(LocalDate datePlantation) {
		this.datePlantation = datePlantation;
	}
	public LocalDate getDateRecolte() {
		return dateRecolte;
	}
	public void setDateRecolte(LocalDate dateRecolte) {
		this.dateRecolte = dateRecolte;
	}
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
	public LocalDateTime getDernierUpdate() {
		return dernierUpdate;
	}
	public void setDernierUpdate(LocalDateTime dernierUpdate) {
		this.dernierUpdate = dernierUpdate;
	}
	public double getHumidite() {
		return humidite;
	}
	public void setHumidite(double humidite) {
		this.humidite = humidite;
	}
		
}
