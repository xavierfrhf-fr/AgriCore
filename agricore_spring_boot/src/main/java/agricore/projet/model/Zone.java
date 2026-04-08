package agricore.projet.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import agricore.projet.view.Views;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="zone")
public class Zone {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="zone_id")
	@JsonView(Views.Common.class)
	private Integer id;
	
	@Embedded
	@JsonView(Views.Common.class)
	private Position position;
	
	@Enumerated(EnumType.STRING) 
	@Column(nullable = false)
	@JsonView(Views.Common.class)
	private NomZone nomZone;
	
	
	//---- Mapping avec les autres classes
	@OneToOne
	@JsonView(Views.Zone.class)
	private Fermier fermier;
	
	@OneToMany(mappedBy = "zone")
	@JsonView(Views.ZoneWithAnimal.class)
	private List<Animal> animals = new ArrayList<>();
	
	/*
	@OneToOne(mappedBy = "zone")
	@JsonView(Views.Zone.class)
	private Plante plante;
	*/
	
	@OneToMany(mappedBy = "zone")
	@JsonView(Views.ZoneWithVehicule.class)
	private List<Vehicule> vehicules = new ArrayList<>();
	
	@OneToMany(mappedBy = "zone")
	@JsonView(Views.ZoneWithRessource.class)
	private List<Ressource> ressources = new ArrayList<>();
	
	
	//J'ai pas mis les plantes et animaux, dans constructeur (je pense on les rajoutes par la suite) mais Spring en auras besoin ??
	public Zone() {
	}

	public Zone(Position position, NomZone nomZone) {
		super();
		this.position = position;
		this.nomZone = nomZone;
	}
	public Zone(Integer id, Position position, NomZone nomZone) {
		super();
		this.id = id;
		this.position = position;
		this.nomZone = nomZone;
	}

	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public NomZone getNomZone() {
		return nomZone;
	}

	public void setNomZone(NomZone nomZone) {
		this.nomZone = nomZone;
	}
	
	public List<Animal> getAnimals() {
		return animals;
	}

	public void setAnimals(List<Animal> animals) {
		this.animals = animals;
	}

	/*
	public Plante getPlante() {
		return plante;
	}

	public void setPlante(Plante plante) {
		this.plante = plante;
	}
	*/
	
	public List<Vehicule> getVehicules() {
		return vehicules;
	}

	public void setVehicules(List<Vehicule> vehicules) {
		this.vehicules = vehicules;
	}

	public List<Ressource> getRessources() {
		return ressources;
	}

	public void setRessources(List<Ressource> ressources) {
		this.ressources = ressources;
	}

	
	public boolean addVehicule(Vehicule v) {
		//Check si le batiment permet les vehicules ? A ajouter dans enum NomZone ??
		vehicules.add(v);
		v.setZone(this);
		return true;
	}
	
	public boolean addRessource(Ressource r) {
		if (nomZone.isAutoriseStorage()) {
			ressources.add(r);
			r.setZone(this);
			return true;
		}
		return false;
	}
	
	

	//Methode supplementaire
	public boolean addAnimal(Animal a) {
		if (nomZone.isAutoriseAni()) {
		    animals.add(a);
		    a.setZone(this); 
		    return true;
		}
		return false;

	}
	
	
/*
	public boolean planterPlante(Plante p) {
		if (nomZone.isAutorisePlant()) {
			plante = p;
			p.setZone(this);
			return true;
		}
		return false;
	}
	*/
	
	
	
	

}
