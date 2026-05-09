package agricore.projet.model;

import agricore.projet.util.CheminAsset;

public enum TypeVehicule {

	UTILITAIRE("utilitaire",
			CheminAsset.VEHICULE+"utilitaire.png",
			100,
			14
	),
	TRACTEUR("tracteur",
			CheminAsset.VEHICULE+"tracteur.png",
			400,
			13
	),
	MOISSONNEUSE("moissonneuse",
			CheminAsset.VEHICULE+"moissonneuse.png",
			400,
			14
	),
	PETIT_TRACTEUR("tracteur pour vignes",
			CheminAsset.VEHICULE+"petit_tracteur.png",
			400,
			12
	),
	BETAILLERE("bétaillère",
			CheminAsset.VEHICULE+"betaillere.png",
			400,
			11
	),
	PICKUP("Pick-Up",
			CheminAsset.VEHICULE+"pickup.png",
			80,
			13
	);
	
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
