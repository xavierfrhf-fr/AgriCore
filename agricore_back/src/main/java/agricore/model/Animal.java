package agricore.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="animal")
public class Animal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="animal_id")
	private Integer id;
	
	private boolean male;
	
	@Column(name="date_naissance")
	private LocalDate dateNaissance;
	
	@Column(name="date_vaccination")
	private LocalDate dateVaccination;
	
	@Enumerated(EnumType.STRING)
	private EspeceAnimal espece;

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

	public Animal() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	@Override
	public String toString() {
		return "Animal [id=" + id + ", male=" + male + ", dateNaissance=" + dateNaissance + ", dateVaccination="
				+ dateVaccination + ", espece=" + espece + "]";
	}
	
	public void vacciner() {
		dateVaccination = dateVaccination.plusYears(1);
	}
	
	public LocalDate rappelVaccin() {
		LocalDate dateRappel = dateVaccination.minusMonths(1);
		return dateRappel;
	}
	
}
