package agricore.projet.model;

import java.util.Set;

public enum NomZone {
	CHAMPS(false, true, Set.of()),
	POULAILLER(true, false, Set.of()),
	ETABLE(true, false, Set.of()),
	SILO(false, false, Set.of(NomRessource.Blé, NomRessource.Colza,NomRessource.Tournesol,NomRessource.Mais)),
	CUVE(false, false, Set.of(NomRessource.Lait,NomRessource.JusDePomme)),
	STOCK_DE_FRUIT(false, false, Set.of(NomRessource.Pomme,NomRessource.Poire,NomRessource.Fraise)),
	CHAMBRE_FROIDE(false, false, Set.of(NomRessource.Fromage));
	
	private final boolean autoriseAni;
	private final boolean autorisePlant;
	private final Set<NomRessource> setRessource;
	//private final String imageLink //Je pose ca là au cas où
	
	
	private NomZone(boolean autoriseAni, boolean autorisePlant, Set<NomRessource> setRessource) {
		this.autoriseAni = autoriseAni;
		this.autorisePlant = autorisePlant;
		this.setRessource = setRessource;
	}

	public boolean isAutoriseAni() {
		return autoriseAni;
	}

	public boolean isAutorisePlant() {
		return autorisePlant;
	}

	public Set<NomRessource> getSetRessource() {
		return setRessource;
	}

	public boolean isZoneUnique(){
		return !setRessource.isEmpty();
	}
}
