package agricore.projet.model;

public enum TypeVehicule {

	UTILITAIRE("utilitaire",null,100,14),
	TRACTEUR("tracteur",null,400,30),
	MOISSONNEUSE("moissonneuse",null,400,40),
	PICKUP("Pick-Up",null,80,13);
	
	private String nomAffichage;
	private String pathSprite;

	private int capaciteReservoir;
	private int consoParKm;

	private TypeVehicule(String nomAffichage, String pathSprite, int capaciteReservoir, int consoParKm) {
		this.nomAffichage = nomAffichage;
		this.pathSprite = pathSprite;
		this.capaciteReservoir = capaciteReservoir;
		this.consoParKm = consoParKm;
	}

	public String getNomAffichage() {
		return nomAffichage;
	}

	public String getPathSprite() {
		return pathSprite;
	}

	public int getCapaciteReservoir() {
		return capaciteReservoir;
	}

	public void setCapaciteReservoir(int capaciteReservoir) {
		this.capaciteReservoir = capaciteReservoir;
	}

	public int getConsoParKm() {
		return consoParKm;
	}

	public void setConsoParKm(int consoParKm) {
		this.consoParKm = consoParKm;
	}
}
