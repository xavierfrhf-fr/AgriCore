package agricore.projet.model;

import agricore.projet.model.zone.NomZone;

public enum EspecePlante {
	Maïs(5, 0.05, NomZone.CHAMPS, TypeVehicule.TRACTEUR), // le champ pousse en 5 mois, il consomme 50 000L/jour et sa fréquence
					// d'arrosage de base(sans pluie) est tous les 7 jours
	Tournesol(5, 0.02, NomZone.CHAMPS,TypeVehicule.TRACTEUR),
	Blé(8, 0.01, NomZone.CHAMPS, TypeVehicule.MOISSONNEUSE),
	Colza(10, 0.01, NomZone.CHAMPS, TypeVehicule.MOISSONNEUSE),
	Pommier(10, 0.01, NomZone.CHAMPS, TypeVehicule.TRACTEUR),
	Fraisier(10, 0.01, NomZone.CHAMPS, TypeVehicule.PICKUP),
	Poirier(10, 0.01, NomZone.CHAMPS, TypeVehicule.UTILITAIRE);
	

	private int tempsPousseMois;
	private double consommationEauParMin; // en litres
	private NomZone allowedZone;

	public TypeVehicule vehiculeRequis;

	private EspecePlante(int tempsPousseMois, double consommationEauParMin, NomZone allowedZone, TypeVehicule vehiculeRequis) {
		this.tempsPousseMois = tempsPousseMois;
		this.consommationEauParMin = consommationEauParMin;
		this.vehiculeRequis = vehiculeRequis;
		this.allowedZone = allowedZone;
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
