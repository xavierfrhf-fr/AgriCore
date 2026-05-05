package agricore.projet.model;

import agricore.projet.model.zone.NomZone;

public enum EspecePlante {
	Maïs(5, 0.05, TypeVehicule.TRACTEUR), // le champ pousse en 5 mois, il consomme 50 000L/jour et sa fréquence
	Maïs(5, 0.05, NomZone.CHAMPS), // le champ pousse en 5 mois, il consomme 50 000L/jour et sa fréquence
					// d'arrosage de base(sans pluie) est tous les 7 jours
	Tournesol(5, 0.02,TypeVehicule.TRACTEUR),
	Blé(8, 0.01, TypeVehicule.MOISSONNEUSE),
	Colza(10, 0.01, TypeVehicule.MOISSONNEUSE),
	Pommier(10, 0.01, TypeVehicule.TRACTEUR),
	Fraisier(10, 0.01, TypeVehicule.PICKUP),
	Poirier(10, 0.01, TypeVehicule.UTILITAIRE);
	Tournesol(5, 0.02, NomZone.CHAMPS),
	Blé(8, 0.01, NomZone.CHAMPS),
	Colza(10, 0.01, NomZone.CHAMPS),
	Pommier(10, 0.01, NomZone.CHAMPS),
	Fraisier(10, 0.01, NomZone.CHAMPS),
	Poirier(10, 0.01, NomZone.CHAMPS);

	private int tempsPousseMois;
	private double consommationEauParMin; // en litres
	private NomZone allowedZone;

	public TypeVehicule vehiculeRequis;

	private EspecePlante(int tempsPousseMois, double consommationEauParMin, TypeVehicule vehiculeRequis) {
	private EspecePlante(int tempsPousseMois, double consommationEauParMin, NomZone allowedZone) {
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
