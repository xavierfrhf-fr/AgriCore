package agricore.projet.model.animal;

import agricore.projet.model.ressource.NomRessource;
import agricore.projet.model.zone.NomZone;

public enum EspeceAnimal {
	Cochon(NomZone.ETABLE, AnimalProduct.sameForMaleAndFemale(null, "Cochon")),
	Vache(NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.LAIT_VACHE, "Vache", null, "Taureau")),
	Mouton(NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.LAIT_BREBIS, "Brebis", NomRessource.LAINE, "Mouton")),
	Poule(NomZone.POULAILLER, AnimalProduct.differentForMaleAndFemale(NomRessource.OEUF_POULE, "Poule", null, "Coq")),
	Canne(NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.OEUF_CANNE, "Canne", NomRessource.PLUME, "Canard")),
	Oie(NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.OEUF_OIE, "Oie", NomRessource.PLUME, "Jars")),
	Cheval(NomZone.ETABLE, AnimalProduct.sameForMaleAndFemale(null, "Cheval")),
	Ane(NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.LAIT_ANESSE, "Anesse", null, "Ane")),
	Lapin(NomZone.ETABLE, AnimalProduct.sameForMaleAndFemale(null, "Lapin"));

	private final NomZone allowedZone;
	private final AnimalProduct product;

	private EspeceAnimal(NomZone allowedZone, AnimalProduct product) {
		this.allowedZone = allowedZone;
		this.product = product;
	}

	public NomZone getAllowedZone() {
		return allowedZone;
	}

	public AnimalProduct getProduct() {
		return product;
	}
	
}
