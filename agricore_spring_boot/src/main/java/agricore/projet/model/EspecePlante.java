package agricore.projet.model;

import agricore.projet.model.zone.NomZone;

public enum EspecePlante {
	MAIS("Maïs",5, 0.05, NomZone.CHAMPS, TypeVehicule.TRACTEUR), // le champ pousse en 5 mois, il consomme 50 000L/jour et sa fréquence
					// d'arrosage de base(sans pluie) est tous les 7 jours
	TOURNESOL("Tournesol",5, 0.02, NomZone.CHAMPS,TypeVehicule.TRACTEUR),
	BLE("Blé",8, 0.01, NomZone.CHAMPS, TypeVehicule.MOISSONNEUSE),
	COLZA("Colza",10, 0.01, NomZone.CHAMPS, TypeVehicule.MOISSONNEUSE),
	POMMIER("Pommier",10, 0.01, NomZone.CHAMPS, TypeVehicule.TRACTEUR),
	FRAISIER("Faisier",10, 0.01, NomZone.CHAMPS, TypeVehicule.PICKUP),
	POIRIER("Poirier",10, 0.01, NomZone.CHAMPS, TypeVehicule.UTILITAIRE);
	

	private int tempsPousseMois;
	private double consommationEauParMin; // en litres
	private NomZone allowedZone;
	private String nomAffichage;

	public TypeVehicule vehiculeRequis;

	private EspecePlante(String nomAffichage, int tempsPousseMois, double consommationEauParMin, NomZone allowedZone, TypeVehicule vehiculeRequis) {
		this.nomAffichage = nomAffichage;
		this.tempsPousseMois = tempsPousseMois;
		this.consommationEauParMin = consommationEauParMin;
		this.vehiculeRequis = vehiculeRequis;
		this.allowedZone = allowedZone;
	}

	public String getNomAffichage() {
		return nomAffichage;
	}

	public int getTempsPousseMois() {
		return tempsPousseMois;
	}

	public double getConsommationEauParMin() {
		return consommationEauParMin;
	}

	public TypeVehicule getVehiculeRequis() {
		return vehiculeRequis;
	}

	public NomZone getAllowedZone() {
		return allowedZone;
	}

}
