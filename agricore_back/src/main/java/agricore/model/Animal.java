package agricore.model;

import java.time.LocalDate;

public class Animal {

	private Integer id;
	private boolean male;
	private LocalDate dateNaissance;
	private LocalDate dateVaccination;
	
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
	
	
}
