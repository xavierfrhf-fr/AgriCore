package agricore.model;

import java.time.LocalDate;

public class Plante {
	private Integer id;
	private LocalDate dateNaissance;
	private LocalDate derniereVaccination;
	private EspecePlante espece;
	private Zone zone;


	public Plante() {}


	public Plante(Integer id, LocalDate dateNaissance, LocalDate derniereVaccination, EspecePlante espece, Zone zone) {
		super();
		this.id = id;
		this.dateNaissance = dateNaissance;
		this.derniereVaccination = derniereVaccination;
		this.espece = espece;
		this.zone = zone;
	}

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public LocalDate getDateNaissance() {
		return dateNaissance;
	}


	public void setDateNaissance(LocalDate dateNaissance) {
		this.dateNaissance = dateNaissance;
	}


	public LocalDate getDerniereVaccination() {
		return derniereVaccination;
	}


	public void setDerniereVaccination(LocalDate derniereVaccination) {
		this.derniereVaccination = derniereVaccination;
	}


	public EspecePlante getEspece() {
		return espece;
	}


	public void setEspece(EspecePlante espece) {
		this.espece = espece;
	}


	public Zone getZone() {
		return zone;
	}


	public void setZone(Zone zone) {
		this.zone = zone;
	}


	@Override
	public String toString() {
		return "Plante [id=" + id + ", dateNaissance=" + dateNaissance + ", derniereVaccination=" + derniereVaccination
				+ ", espece=" + espece + ", zone=" + zone + "]";
	}


}
