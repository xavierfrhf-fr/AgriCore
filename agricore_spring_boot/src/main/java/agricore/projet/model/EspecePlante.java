package agricore.projet.model;

import agricore.projet.model.ressource.NomRessource;
import agricore.projet.model.zone.NomZone;
import agricore.projet.util.CheminAsset;

public enum EspecePlante {
	MAIS("Maïs",
			CheminAsset.PLANTE+".png",
			5,
			0.05,
			NomZone.CHAMPS,
			TypeVehicule.TRACTEUR,
			NomRessource.MAIS,
			25
	),
	TOURNESOL("Tournesol",
			CheminAsset.PLANTE+".png",
			5,
			0.02,
			NomZone.CHAMPS,
			TypeVehicule.TRACTEUR,
			NomRessource.TOURNESOL,
			25
	),
	BLE("Blé",
			CheminAsset.PLANTE+".png",
			8,
			0.01,
			NomZone.CHAMPS,
			TypeVehicule.MOISSONNEUSE,
			NomRessource.BLE,
			50
	),
	COLZA("Colza",
			CheminAsset.PLANTE+".png",
			10,
			0.01,
			NomZone.CHAMPS,
			TypeVehicule.MOISSONNEUSE,
			NomRessource.COLZA,
			10
	),
	POMMIER("Pommier",
			CheminAsset.PLANTE+".png",
			10,
			0.01,
			NomZone.CHAMPS,
			TypeVehicule.TRACTEUR,
			NomRessource.POMME,
			50
	),
	FRAISIER("Faisier",
			CheminAsset.PLANTE+".png",
			10,
			0.01,
			NomZone.CHAMPS,
			TypeVehicule.PICKUP,
			NomRessource.FRAISE,
			75
	),
	POIRIER("Poirier",
			CheminAsset.PLANTE+".png",
			10,
			0.01,
			NomZone.CHAMPS,
			TypeVehicule.UTILITAIRE,
			NomRessource.POIRE,
			50
	);
	

	private int tempsPousseMinute;
	private double consommationEauParMin; // en litres
	private NomZone allowedZone;
	private String nomAffichage;
	private String pathSprite;

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
						 int quantite) {
		this.nomAffichage = nomAffichage;
		this.pathSprite = pathSprite;
		this.tempsPousseMinute = tempsPousseMinute;
		this.consommationEauParMin = consommationEauParMin;
		this.vehiculeRequis = vehiculeRequis;
		this.allowedZone = allowedZone;
		this.ressourceProduite = ressourceProduite;
		this.quantite = quantite;

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

	public int getQuantite() {
		return quantite;
	}
}
