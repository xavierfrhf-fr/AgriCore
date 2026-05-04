package agricore.projet.model;

public enum EspecePlante {
	Maïs(5, 0.05, TypeVehicule.TRACTEUR), // le champ pousse en 5 mois, il consomme 50 000L/jour et sa fréquence
					// d'arrosage de base(sans pluie) est tous les 7 jours
	Tournesol(5, 0.02,TypeVehicule.TRACTEUR),
	Blé(8, 0.01, TypeVehicule.MOISSONNEUSE),
	Colza(10, 0.01, TypeVehicule.MOISSONNEUSE),
	Pommier(10, 0.01, TypeVehicule.TRACTEUR),
	Fraisier(10, 0.01, TypeVehicule.PICKUP),
	Poirier(10, 0.01, TypeVehicule.UTILITAIRE);

	private int tempsPousseMois;
	private double consommationEauParMin; // en litres

	public TypeVehicule vehiculeRequis;

	private EspecePlante(int tempsPousseMois, double consommationEauParMin, TypeVehicule vehiculeRequis) {
		this.tempsPousseMois = tempsPousseMois;
		this.consommationEauParMin = consommationEauParMin;
		this.vehiculeRequis = vehiculeRequis;
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

	


	

}
