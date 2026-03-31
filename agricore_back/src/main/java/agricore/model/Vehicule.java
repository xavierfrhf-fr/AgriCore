package agricore.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name="vehicule")
public class Vehicule {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="vehicule_id")
	private Integer id;
	
	private TypeVehicule typeVehicule;
	
	private LocalDate dateControleTech;
	
	@ManyToOne
	@JoinColumn(name="zone_id")
	private Zone zone;
	
	
	
	public Vehicule() {
	}
	
	public Vehicule(Integer id, TypeVehicule typeVehicule, LocalDate dateControleTech) {
		this.id = id;
		this.typeVehicule = typeVehicule;
		this.dateControleTech = dateControleTech;
	}
	
	public Vehicule( TypeVehicule typeVehicule, LocalDate dateControleTech) {
		this.typeVehicule = typeVehicule;
		this.dateControleTech = dateControleTech;
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

	@Override
	public String toString() {
		return "Vehicule [id=" + id + ", typeVehicule=" + typeVehicule + ", dateControleTech=" + dateControleTech + "]";
	}
	
	
	
}
