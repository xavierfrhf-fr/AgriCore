package agricore.projet.model;

public enum EspecePlante {
	Maïs(5,0.05), // le champ pousse en 5 mois, il consomme 50 000L/jour et sa fréquence d'arrosage de base(sans pluie) est tous les 7 jours
	Tournesol(5,0.02),
	Blé(8,0.01),
	Colza(10,0.01);

	private int tempsPousseMois;
	private double consommationEauParMin; //en litres

	private EspecePlante(int tempsPousseMois, double consommationEauParMin) {
		this.tempsPousseMois = tempsPousseMois;
		this.consommationEauParMin = consommationEauParMin;
	}

	public int getTempsPousseMois() {
		return tempsPousseMois;
	}

	public double getConsommationEauParMin() {
		return consommationEauParMin;
	}

}
