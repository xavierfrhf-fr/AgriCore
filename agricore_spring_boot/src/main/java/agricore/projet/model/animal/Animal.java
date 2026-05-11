package agricore.projet.model.animal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import agricore.projet.model.ressource.NomRessource;
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

	private LocalDateTime prochaineProduction;

	private Integer totalProduit;

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

	public Integer getTotalProduit() {
		return totalProduit;
	}

	public void setTotalProduit(Integer totalProduit) {
		this.totalProduit = totalProduit;
	}

	public LocalDateTime getProchaineProduction() {
		return prochaineProduction;
	}

	public void setProchaineProduction(LocalDateTime prochaineProduction) {
		this.prochaineProduction = prochaineProduction;
	}

	@Override
	public String toString() {
		return "Animal [id=" + id + ", dateNaissance=" + dateNaissance + ", dateVaccination="
				+ dateVaccination + ", espece=" + espece + "]";
	}

	public void vacciner() {
		dateVaccination = LocalDate.now();
	}

	public int delaiAvantVaccin() {
		int delais;
		if (dateVaccination == null) {
			delais = 182;
		} else {
			delais = (int) LocalDate.now().until(dateVaccination.plusYears(1), ChronoUnit.DAYS);
		}
		return delais;
	}

	public boolean isMale() {
		return male;
	}

	public int getAge() {
		int age = (int) this.dateNaissance.until(LocalDate.now(), ChronoUnit.YEARS);
		return age;
	}

	public boolean isProducer(){
		return espece.getDimorphisme().getProductionTime(male) != null;
	}

	public NomRessource getNomRessource(){
		return espece.getDimorphisme().getNomRessource(male);
	}

	public int produceRessource(){
		if (!isProducer()){
			return 0;
		}
		LocalDateTime now = LocalDateTime.now();
		if (prochaineProduction == null) {
			prochaineProduction = now.plusMinutes(espece.getDimorphisme().getProductionTime(male));
			return 0;
		}

		int qtyProduce = 0;

		while (!prochaineProduction.isAfter(now)) {
			qtyProduce++;
			prochaineProduction = prochaineProduction.plusMinutes(espece.getDimorphisme().getProductionTime(male));
		}
		totalProduit +=  qtyProduce;
		return qtyProduce;
	}

	public void initializeProduction(){
		if (!isProducer()){
			return;
		}
		if (prochaineProduction == null) {
			prochaineProduction = LocalDateTime.now().plusMinutes(espece.getDimorphisme().getProductionTime(male));
		}
	}

}
