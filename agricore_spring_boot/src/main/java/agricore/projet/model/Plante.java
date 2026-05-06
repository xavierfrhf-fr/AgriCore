package agricore.projet.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import agricore.projet.model.zone.Zone;
import jakarta.persistence.*;

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
	@OneToOne
    @JoinColumn(name="zone_id")
    private Zone zone;
	

	//Gestion de l'humidité des plantes
	//L'idée est d'avoir une valeur (attribut "humidite") qui représente le % d'eau de la plante (entre 0-100)
	//Si la valeurs atteint 0 la plante meurt (sans réel mort de la plante prévus, juste créer des alertes pour l'arrosage)
	//Dans l'enum EspecePlante est stocké un attribut "consommationEauParMin" relatif à la conso d'eau de chaque espèce
	//Pour permettre la MaJ de "humidite", un "dernierUpdate" permet de calculer le temps depuis la dernière update de "humidite"
	@Column(name="dernier_update")
	private LocalDateTime dernierUpdate;

	@Column(name="humidite")
	private double humidite; //Humidite de la plante (entre 0 et 100)

	//Valeurs seuil avant arrosage urgent
	//L'idée de cet attribut est de determiner un seuil (constant chez toutes les plantes donc static)
	//Permettant de calculer un temps avant arrosage (gardant une marge pour pas que la plante meurt)
	private static double SEUIL_HUMIDITE_CRITIQUE = 20.;

	public Plante() {}

	public Plante(Integer id, LocalDate datePlantation, LocalDate dateRecolte, EspecePlante espece, Zone zone,
			LocalDateTime dernierUpdate) {
		super();
		this.id = id;
		this.datePlantation = datePlantation;
		this.dateRecolte = dateRecolte;
		this.espece = espece;
		this.zone = zone;
		this.dernierUpdate = dernierUpdate;
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

	public LocalDateTime getDernierUpdate() {
		return dernierUpdate;
	}

	public void setDernierUpdate(LocalDateTime dernierUpdate) {
		this.dernierUpdate = dernierUpdate;
	}

	public double getHumidite() {
		return humidite;
	}

	public void setHumidite(double humidite) {
		this.humidite = humidite;
	}

	public void updateHumidite() {
		long deltaMinute = ChronoUnit.MINUTES.between(dernierUpdate, LocalDateTime.now());
		if (deltaMinute >= 1) {
			humidite = humidite - espece.getConsommationEauParMin() * deltaMinute; //On soustrait à l'humidité actuel, la conso par minute * le temps en min depuis dernier update
			humidite = Math.max(humidite, 0.); //On borne, pour ne pas avoir humidite < 0
			this.dernierUpdate = LocalDateTime.now();
		}
	}

	public void arroser(){
		this.dernierUpdate = LocalDateTime.now();
		humidite = 100.;
	}

	public void arroser(long pourcentage){
		//Surcharge pour prendre en compte un arrosage incomplet (pour implémentation ulterieure de faible pluie par ex)
		this.dernierUpdate = LocalDateTime.now();
		humidite += pourcentage;
		humidite = Math.min(humidite, 100.);//On borne, pour ne pas avoir humidite > 100
	}

	public int tempsAvantMortEnJour(){
		updateHumidite();
		double deltaMinute = (humidite) / espece.getConsommationEauParMin();
		return (int) deltaMinute / 1440;
	}

	public int tempsAvantArrosageEnJour(){
		updateHumidite();
		if (humidite < SEUIL_HUMIDITE_CRITIQUE) {
			return 0;
		}
		double deltaMinute = (humidite - SEUIL_HUMIDITE_CRITIQUE) / espece.getConsommationEauParMin();
		return (int) deltaMinute / 1440;
	}

	public int tempsAvantRecolteEnJour(){
		return (int) LocalDate.now().until(datePlantation.plusMonths(espece.getTempsPousseMois()), ChronoUnit.DAYS);
	}

	

	

	
}
