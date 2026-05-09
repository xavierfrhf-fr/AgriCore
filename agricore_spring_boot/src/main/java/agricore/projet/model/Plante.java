package agricore.projet.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import agricore.projet.model.zone.Zone;
import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

@Entity
@Table(name="plante")
public class Plante {

	private static final double TIME_STEP_MINUTE = 1.0;
	private static final double CROISSANCE_ARBRE_APRES_RECOLTE = 80.;

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

	public void updateHumidite() {
		LocalDateTime now = LocalDateTime.now();
		if (dernierUpdate == null) {
			dernierUpdate = now;
			return;
		}

		double deltaMinute = Duration.between(dernierUpdate, now).toMillis() / 60000.0;

		while (deltaMinute > 0) {
			double step = Math.min(TIME_STEP_MINUTE, deltaMinute);
			applyTimeStep(step);
			deltaMinute -= step;
		}

		dernierUpdate = now;
	}

	private void applyTimeStep(double stepMinute) {
		boolean avaitDeLEau = (this.humidite > 0);

		consommerEau(stepMinute);

		if (avaitDeLEau && !this.mature) {
			augmenterCroissance(stepMinute);
		}

		if (this.croissance >= 100) {
			this.croissance = 100;
			this.mature = true;
		}
	}

	private void consommerEau(double stepMinute) {
		double consommation = this.espece.getConsommationEauParMin() * stepMinute;
		this.humidite = Math.max(0., this.humidite - consommation);
	}

	private void augmenterCroissance(double stepMinute) {
		double deltaCroissance = getCroissanceParMin() * stepMinute;
		this.croissance = Math.min(100., this.croissance + deltaCroissance);
	}

	public void arroser(){
		this.humidite = 100;
	}

	public void recolter(){
		if (this.espece.isTree()){
			this.croissance = CROISSANCE_ARBRE_APRES_RECOLTE;
		}else{
			this.croissance = 0.;
		}
		this.mature = false;
		this.dernierUpdate = LocalDateTime.now();
	}




	private double getCroissanceParMin() {
		return 100./this.espece.getTempsPousseMinute();
	}

	public double getCroissanceParSecond() {
		return this.getCroissanceParMin()/60;
	}
}
