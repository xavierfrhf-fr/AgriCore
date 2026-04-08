package agricore.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="plante")
public class Plante {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private LocalDate datePlantation;
	private LocalDate dateRecolte;
	private EspecePlante espece;
	private Zone zone;
	private LocalDate dernierArrosage;
	private double eauDisponible; 


	public Plante() {}


	public Plante(Integer id, LocalDate datePlantation, LocalDate dateRecolte, EspecePlante espece, Zone zone) {
		super();
		this.id = id;
		this.datePlantation = datePlantation;
		this.dateRecolte = dateRecolte;
		this.espece = espece;
		this.zone = zone;
	}
	public boolean arroser (LocalDate dateArrosage, boolean pluie) {
		
		LocalDate dernier = (dernierArrosage != null) ? dernierArrosage : datePlantation;
	    long joursEcoules = java.time.temporal.ChronoUnit.DAYS.between(dernier, dateArrosage);
	    if (pluie==true) {
	    	 int frequenceReelle =+ espece.getFrequenceArrosageSansPluie();
	    }
	    else {
	    int frequenceReelle = espece.getFrequenceArrosageSansPluie();
	    }
	}
	
	public boolean recolter (LocalDate dateRecolte) {
		long moisEcoules = java.time.temporal.ChronoUnit.MONTHS.between(datePlantation, dateRecolte);
		if ( moisEcoules>= espece.getTempsPousseMois()) {
			this.dateRecolte = dateRecolte;
			return true;
		}

		return false;
	}
	
	public LocalDate rappelRecolte() {
		LocalDate dateRappel = dateRecolte.minusMonths(1);
		return dateRappel;
	}
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


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


	public Zone getZone() {
		return zone;
	}


	public void setZone(Zone zone) {
		this.zone = zone;
	}


	@Override
	public String toString() {
		return "Plante [id=" + id + ", datePlantation=" + datePlantation + ", dateRecolte=" + dateRecolte
				+ ", espece=" + espece + ", zone=" + zone + "]";
	}


}
