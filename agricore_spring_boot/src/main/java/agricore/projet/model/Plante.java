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
	private LocalDateTime datePlantation;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(100)")
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
	private double croissance; //Croissance (entre 0 et 100)
	private boolean mature;
	//Valeurs seuil avant arrosage urgent
	//L'idée de cet attribut est de determiner un seuil (constant chez toutes les plantes donc static)
	//Permettant de calculer un temps avant arrosage (gardant une marge pour pas que la plante meurt)
	private static double SEUIL_HUMIDITE_CRITIQUE = 20.;

	public Plante() {}

	public Plante(Integer id, LocalDateTime datePlantation, EspecePlante espece, Zone zone,
			LocalDateTime dernierUpdate) {
		super();
		this.id = id;
		this.datePlantation = datePlantation;
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

	public void setDatePlantation(LocalDateTime datePlantation) {
		this.datePlantation = datePlantation;
	}

	public double getCroissance() {
		return croissance;
	}

	public void setCroissance(double croissance) {
		this.croissance = croissance;
	}

	public LocalDateTime getDatePlantation() {
		return datePlantation;
	}

	public boolean isMature() {
		return mature;
	}

	public void setMature(boolean mature) {
		this.mature = mature;
	}

	public void updateHumidite(boolean arrosage) {
		if (mature){
			return;
		}
		long deltaSeconde = ChronoUnit.SECONDS.between(dernierUpdate, LocalDateTime.now());
		if (deltaSeconde >= 1) {
			humidite -= espece.getConsommationEauParMin() * deltaSeconde / 60; //On soustrait à l'humidité actuel, la conso par minute * le temps en min depuis dernier update
			humidite = Math.max(humidite, 0.); //On borne, pour ne pas avoir humidite < 0
			if (humidite >= 0.){
				croissance += this.getCroissanceParMin() * deltaSeconde / 60;

			}else if(!arrosage){
				if(this.getEspece().isTree()){
					croissance = 70;
				} else {
					croissance = 0;
				}

			}
			if (croissance >= 100){
				mature = true;
				croissance = 100;
			}

			this.dernierUpdate = LocalDateTime.now();
		}
	}

	private double getCroissanceParMin() {
		return 100./this.espece.getTempsPousseMinute();
	}

	public double getCroissanceParSecond() {
		return this.getCroissanceParMin()/60;
	}

	public void arroser(){
		this.updateHumidite(true);
		humidite = 100.;
	}
}
