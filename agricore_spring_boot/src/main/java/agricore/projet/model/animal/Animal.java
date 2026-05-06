package agricore.projet.model.animal;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import agricore.projet.model.zone.Zone;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "animal")
public class Animal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "animal_id")
	private Integer id;

	private boolean male;

	@Column(name = "date_naissance")
	private LocalDate dateNaissance;

	@Column(name = "date_vaccination")
	private LocalDate dateVaccination;

	@Enumerated(EnumType.STRING)
	private EspeceAnimal espece;


	@ManyToOne
	@JoinColumn(name = "zone_id")
	private Zone zone;

	public Animal(Integer id, boolean male, LocalDate dateNaissance, LocalDate dateVaccination, EspeceAnimal espece) {
		this.id = id;
		this.male = male;
		this.dateNaissance = dateNaissance;
		this.dateVaccination = dateVaccination;
		this.espece = espece;
	}

	public Animal(boolean male, LocalDate dateNaissance, LocalDate dateVaccination, EspeceAnimal espece) {
		this.male = male;
		this.dateNaissance = dateNaissance;
		this.dateVaccination = dateVaccination;
		this.espece = espece;
	}

	public Animal() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	@Override
	public String toString() {
		return "Animal [id=" + id + ", dateNaissance=" + dateNaissance + ", dateVaccination="
				+ dateVaccination + ", espece=" + espece + "]";
	}

	public void vacciner() {
		dateVaccination = LocalDate.now().plusYears(1);
	}

	public LocalDate rappelVaccin() {
		LocalDate dateRappel = dateVaccination.minusMonths(1);
		return dateRappel;
	}

	public int delaiAvantVaccin() {
		int delais = (int) LocalDate.now().until(dateVaccination, ChronoUnit.DAYS);
		return delais;
	}

	
	

	// Inutile car fait en bdd ?
	// public boolean deplacer(Zone new_zone) {
	// Zone previous_zone = this.getZone();
	// if (new_zone.addAnimal(this)) {
	// previous_zone.getAnimals().remove(this);
	// return true;
	// }
	// return false;
	// }
	public boolean isMale() {
		return male;
	}

	

}
