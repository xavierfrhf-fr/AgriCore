package agricore.projet.dto.animal.request;

import java.time.LocalDate;

import agricore.projet.model.animal.EspeceAnimal;

public class UpdateAnimalRequest {
	
	private boolean male;
	private LocalDate dateNaissance;
	private LocalDate dateVaccination;
	private EspeceAnimal espece;
    private Integer zoneId;

	public UpdateAnimalRequest(boolean male, LocalDate dateNaissance, LocalDate dateVaccination, EspeceAnimal espece, Integer zoneId) {
		this.male = male;
		this.dateNaissance = dateNaissance;
		this.dateVaccination = dateVaccination;
		this.espece = espece;
		this.zoneId = zoneId;
	}

	public UpdateAnimalRequest() {}

	public boolean isMale() {
		return male;
	}
	public void setMale(boolean male) {
		this.male = male;
	}
	public LocalDate getDateNaissance() {
		return dateNaissance;
	}
	public void setDateNaissance(LocalDate dateNaissance) {
		this.dateNaissance = dateNaissance;
	}
	public LocalDate getDateVaccination() {
		return dateVaccination;
	}
	public void setDateVaccination(LocalDate dateVaccination) {
		this.dateVaccination = dateVaccination;
	}
	public EspeceAnimal getEspece() {
		return espece;
	}
	public void setEspece(EspeceAnimal espece) {
		this.espece = espece;
	}
	public Integer getZoneId() {
		return zoneId;
	}
	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}
	
	


}
