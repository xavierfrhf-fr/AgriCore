package agricore.projet.model;

import agricore.projet.model.ressource.NomRessource;
import agricore.projet.model.zone.NomZone;
import agricore.projet.util.CheminAsset;

public enum EspecePlante {
	MAIS("Maïs",
			CheminAsset.PLANTE+"mais.png",
			5,
			25,
			NomZone.CHAMPS,
			TypeVehicule.TRACTEUR,
			NomRessource.MAIS,
			25,
			false
	),
	TOURNESOL("Tournesol",
			CheminAsset.PLANTE+"tournesol.png",
			5,
			15,
			NomZone.CHAMPS,
			TypeVehicule.TRACTEUR,
			NomRessource.TOURNESOL,
			25,
			false
	),
	BLE("Blé",
			CheminAsset.PLANTE+"ble.png",
			8,
			20,
			NomZone.CHAMPS,
			TypeVehicule.MOISSONNEUSE,
			NomRessource.BLE,
			50,
			false
	),
	COLZA("Colza",
			CheminAsset.PLANTE+"colza.png",
			10,
			20,
			NomZone.CHAMPS,
			TypeVehicule.MOISSONNEUSE,
			NomRessource.COLZA,
			10,
			false
	),
	POMMIER("Pommier",
			CheminAsset.PLANTE+".png",
			10,
			5,
			NomZone.CHAMPS,
			TypeVehicule.TRACTEUR,
			NomRessource.POMME,
			50,
			true
	),
	FRAISIER("Faisier",
			CheminAsset.PLANTE+".png",
			10,
			10,
			NomZone.CHAMPS,
			TypeVehicule.PICKUP,
			NomRessource.FRAISE,
			75,
			true
	),
	POIRIER("Poirier",
			CheminAsset.PLANTE+".png",
			10,
			5,
			NomZone.CHAMPS,
			TypeVehicule.UTILITAIRE,
			NomRessource.POIRE,
			50,
			true
	);
	

	private int tempsPousseMinute;
	private double consommationEauParMin; // en litres
	private NomZone allowedZone;
	private String nomAffichage;
	private String pathSprite;
	private boolean tree;

	public TypeVehicule vehiculeRequis;
	private NomRessource ressourceProduite;
	private int quantite;

	private EspecePlante(String nomAffichage,
						 String pathSprite,
						 int tempsPousseMinute,
						 double consommationEauParMin,
						 NomZone allowedZone,
						 TypeVehicule vehiculeRequis,
						 NomRessource ressourceProduite,
						 int quantite,
						 boolean tree) {
		this.nomAffichage = nomAffichage;
		this.pathSprite = pathSprite;
		this.tempsPousseMinute = tempsPousseMinute;
		this.consommationEauParMin = consommationEauParMin;
		this.vehiculeRequis = vehiculeRequis;
		this.allowedZone = allowedZone;
		this.ressourceProduite = ressourceProduite;
		this.quantite = quantite;
		this.tree=tree;

	}

	public String getNomAffichage() {
		return nomAffichage;
	}

	public int getTempsPousseMinute() {
		return tempsPousseMinute;
	}

	public double getConsommationEauParMin() {
		return consommationEauParMin;
	}

	public String getPathSprite() {
		return pathSprite;
	}

	public TypeVehicule getVehiculeRequis() {
		return vehiculeRequis;
	}

	public NomZone getAllowedZone() {
		return allowedZone;
	}

	public NomRessource getRessourceProduite() {
		return ressourceProduite;
	}

	public boolean isTree() {
		return tree;
	}

	public int getQuantite() {
		return quantite;
	}
}
