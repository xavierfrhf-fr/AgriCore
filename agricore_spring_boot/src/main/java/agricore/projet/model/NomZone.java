package agricore.projet.model;

public enum NomZone {
	CHAMPS(false, true, false),
	POULAILLER(true, false, false),
	ETABLE(true, false, false),
	SILO(false, false, true),
	CUVE(false, false, true);
	
	
	private final boolean autoriseAni;
	private final boolean autorisePlant;
	private final boolean autoriseStorage;
	//private final String imageLink //Je pose ca là au cas où
	//private final Set<NomRessource> autoriseRessources //Si on veut un jour définir une collection de ressource pour chaque bat.
	
	private NomZone(boolean autoriseAni, boolean autorisePlant, boolean autoriseStorage) {
		this.autoriseAni = autoriseAni;
		this.autorisePlant = autorisePlant;
		this.autoriseStorage = autoriseStorage;
	}

	public boolean isAutoriseAni() {
		return autoriseAni;
	}

	public boolean isAutorisePlant() {
		return autorisePlant;
	}

	public boolean isAutoriseStorage() {
		return autoriseStorage;
	}
	
	
	
	

}
