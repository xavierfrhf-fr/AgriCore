package agricore.projet.model;

public enum TypeVehicule {

	UTILITAIRE("utilitaire",null),
	TRACTEUR("tracteur",null),
	MOISSONNEUSE("moissonneuse",null),
	PICKUP("Pick-Up",null);
	
	private String nomAffichage;
	private String pathSprite;

	private TypeVehicule(String nomAffichage, String pathSprite) {
		this.nomAffichage = nomAffichage;
		this.pathSprite = pathSprite;
	}

	public String getNomAffichage() {
		return nomAffichage;
	}

	public String getPathSprite() {
		return pathSprite;
	}
}
