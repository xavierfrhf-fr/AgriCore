package agricore.projet.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import agricore.projet.model.flux.plante.PlanteFlux;
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

	@OneToMany(mappedBy = "plante", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PlanteFlux> flux = new ArrayList<>();



	//Gestion de l'humidité des plantes
	//L'idée est d'avoir une valeur (attribut "humidite") qui représente le % d'eau de la plante (entre 0-100)
	//Si la valeurs atteint 0 la plante meurt (sans réel mort de la plante prévus, juste créer des alertes pour l'arrosage)
	//Dans l'enum EspecePlante est stocké un attribut "consommationEauParMin" relatif à la conso d'eau de chaque espèce
	//Pour permettre la MaJ de "humidite", un "dernierUpdate" permet de calculer le temps depuis la dernière update de "humidite"
//	@Column(name="dernier_update")
//	private LocalDateTime dernierUpdate;

	@Column(name="humidite")
	private double humidite; //Humidite de la plante (entre 0 et 100)
	private double croissance; //Croissance (entre 0 et 100)
	private boolean mature;

	public Plante() {}

	public Plante(Integer id, LocalDateTime datePlantation, EspecePlante espece, Zone zone) {
		super();
		this.id = id;
		this.datePlantation = datePlantation;
		this.espece = espece;
		this.zone = zone;
		//this.dernierUpdate = dernierUpdate;
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
	}

	public void addFlux(PlanteFlux planteFlux) {
		boolean alreadyExist = this.flux.stream()
									.anyMatch(f -> f.getFluxType().equals(planteFlux.getFluxType())
									);
		if (alreadyExist){
			System.out.println("PlanteFlux ("+planteFlux.getFluxType()+")already exist and will be overwritten");
		}

		this.flux.removeIf(f -> f.getFluxType().equals(planteFlux.getFluxType()));

		this.flux.add(planteFlux);
		planteFlux.setPlante(this);
	}



	public double getCroissanceParMin() {
		return 100./this.espece.getTempsPousseMinute();
	}

	public double getCroissanceParSecond() {
		return this.getCroissanceParMin()/60;
	}
}
