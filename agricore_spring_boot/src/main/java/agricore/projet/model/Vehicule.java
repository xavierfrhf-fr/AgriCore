package agricore.projet.model;

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
@Table(name="vehicule")
public class Vehicule {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="vehicule_id")
	private Integer id;
	
	@Enumerated(EnumType.STRING)
	private TypeVehicule typeVehicule;
	
	private LocalDate dateControleTech;

	private int carburantActuel;
	private int capaciteReservoir;
	private int consoParKm;
	
	@ManyToOne
	@JoinColumn(name="zone_id")
	private Zone zone;
	
	
	
	public Vehicule() {
	}
	
	public Vehicule(Integer id, TypeVehicule typeVehicule, LocalDate dateControleTech, Zone zone, int carburantActuel, int capaciteReservoir, int consoParKm) {
		this.id = id;
		this.typeVehicule = typeVehicule;
		this.dateControleTech = dateControleTech;
		this.zone = zone;
		this.carburantActuel = carburantActuel;
		this.capaciteReservoir = capaciteReservoir;
		this.consoParKm = consoParKm;
	}
	
	public Vehicule( TypeVehicule typeVehicule, LocalDate dateControleTech, Zone zone, int carburantActuel, int capaciteReservoir, int consoParKm) {
		this.typeVehicule = typeVehicule;
		this.dateControleTech = dateControleTech;
		this.zone = zone;
		this.carburantActuel = carburantActuel;
		this.capaciteReservoir = capaciteReservoir;
		this.consoParKm = consoParKm;
	}
	
	public boolean rappelControle() {
		
		if (dateControleTech.isAfter(LocalDate.now())) {
			return true; 
		}
		return false;	
	}
	
	public int delaiAvantControle() {
		
		int delais = (int) LocalDate.now().until(dateControleTech, ChronoUnit.DAYS);
		
		return delais;
	}

	public boolean besoinCarburant(int distanceKm) {
		return carburantActuel >= distanceKm * consoParKm;
	}

	public void consommerCarburant(int distanceKm) {

		int carburantNecessaire = distanceKm * consoParKm;

		if (carburantNecessaire > carburantActuel) {

			throw new RuntimeException("Pas assez de carburant");
			
		}

		carburantActuel -= carburantNecessaire;

	}

	public void fairePlein() {
		carburantActuel = capaciteReservoir;
	}
	


	//Getters et Setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TypeVehicule getTypeVehicule() {
		return typeVehicule;
	}

	public void setTypeVehicule(TypeVehicule typeVehicule) {
		this.typeVehicule = typeVehicule;
	}

	public LocalDate getDateControleTech() {
		return dateControleTech;
	}

	public void setDateControleTech(LocalDate dateControleTech) {
		this.dateControleTech = dateControleTech;
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	

	public int getCarburantActuel() {
		return carburantActuel;
	}

	public void setCarburantActuel(int carburantActuel) {
		this.carburantActuel = carburantActuel;
	}

	public int getCapaciteReservoir() {
		return capaciteReservoir;
	}

	public void setCapaciteReservoir(int capaciteReservoir) {
		this.capaciteReservoir = capaciteReservoir;
	}

	public int getConsoParKm() {
		return consoParKm;
	}

	public void setConsoParKm(int consoParKm) {
		this.consoParKm = consoParKm;
	}

	@Override
	public String toString() {
		return "Vehicule [id=" + id + ", typeVehicule=" + typeVehicule + ", dateControleTech=" + dateControleTech + "]";
	}
	
	
	
}
