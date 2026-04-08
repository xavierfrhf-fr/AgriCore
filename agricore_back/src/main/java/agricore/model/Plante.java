package agricore.model;

import java.time.LocalDate;

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
@Table(name="plante")
public class Plante {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="plante_id")
	private Integer id;
	@Column(name="date_plantation")
	private LocalDate datePlantation;
	@Column(name="date_recolte")
	private LocalDate dateRecolte;
	@Enumerated(EnumType.STRING)
	private EspecePlante espece;
	@ManyToOne
    @JoinColumn(name="zone_id")
    private Zone zone;
	@Column(name="dernier_arrosage")
	private LocalDate dernierArrosage;



	public Plante() {}


	
	public Plante(Integer id, LocalDate datePlantation, LocalDate dateRecolte, EspecePlante espece, Zone zone,
			LocalDate dernierArrosage) {
		super();
		this.id = id;
		this.datePlantation = datePlantation;
		this.dateRecolte = dateRecolte;
		this.espece = espece;
		this.zone = zone;
		this.dernierArrosage = dernierArrosage;
	}



	public Integer getId() {
		return id;
	}



	public LocalDate getDatePlantation() {
		return datePlantation;
	}



	public LocalDate getDateRecolte() {
		return dateRecolte;
	}



	public EspecePlante getEspece() {
		return espece;
	}



	public Zone getZone() {
		return zone;
	}



	public LocalDate getDernierArrosage() {
		return dernierArrosage;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public void setDatePlantation(LocalDate datePlantation) {
		this.datePlantation = datePlantation;
	}



	public void setDateRecolte(LocalDate dateRecolte) {
		this.dateRecolte = dateRecolte;
	}



	public void setEspece(EspecePlante espece) {
		this.espece = espece;
	}



	public void setZone(Zone zone) {
		this.zone = zone;
	}



	public void setDernierArrosage(LocalDate dernierArrosage) {
		this.dernierArrosage = dernierArrosage;
	}



	@Override
	public String toString() {
		return "Plante [id=" + id + ", datePlantation=" + datePlantation + ", dateRecolte=" + dateRecolte + ", espece="
				+ espece + ", zone=" + zone + ", dernierArrosage=" + dernierArrosage + "]";
	}



	public boolean arroser(LocalDate dateArrosage, boolean pluie) {
		//je veux que la pluie annule l'arrosage et passe à la date suivante
		LocalDate dernier = (dernierArrosage != null) ? dernierArrosage : datePlantation;
	    long joursEcoules = java.time.temporal.ChronoUnit.DAYS.between(dernier, dateArrosage);
	    int frequence = espece.getFrequenceArrosageSansPluie();
	    
	    if (pluie==true) {
	    	 this.dernierArrosage = dateArrosage;
	         return true;
	    }
	    if (joursEcoules >= frequence) {
	        this.dernierArrosage = dateArrosage;
	        return true;
	    }
	    return false;	
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
	
	

}
