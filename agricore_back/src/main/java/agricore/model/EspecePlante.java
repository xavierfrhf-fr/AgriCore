package agricore.model;

public enum EspecePlante {
	Maïs(5,50000, 7), // le champ pousse en 5 mois, il consomme 50 000L/jour
	Tournesol(5,40000,7), 
	Blé(8,30000,10), 
	Colza(10,30000,8);

	private int tempsPousseMois;//en cb de mois pousse l'espèce
	private int consommationEauParJour; //en litres
	private int frequenceArrosageSansPluie; //valeur statique

	private EspecePlante(int tempsPousseMois, int consommationEauParJour, int frequenceArrosageSansPluie) {
		this.tempsPousseMois = tempsPousseMois;
		this.consommationEauParJour = consommationEauParJour;
		this.frequenceArrosageSansPluie = frequenceArrosageSansPluie;
	}

	public int getTempsPousseMois() {
		return tempsPousseMois;
	}

	public int getConsommationEauParJour() {
		return consommationEauParJour;
	}

	public int getFrequenceArrosageSansPluie() {
		return frequenceArrosageSansPluie;
	}

	

}
