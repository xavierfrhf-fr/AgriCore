package agricore.projet.model.animal;

import agricore.projet.model.ressource.NomRessource;
import agricore.projet.model.zone.NomZone;

public enum EspeceAnimal {
	Cochon(NomZone.ETABLE, AnimalProduct.sameForMaleAndFemale(null, "Cochon")),
	Vache(NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.LaitVache, "Vache", null, "Taureau")),
	Mouton(NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.LaitBrebis, "Brebis", NomRessource.Laine, "Mouton")),
	Poule(NomZone.POULAILLER, AnimalProduct.differentForMaleAndFemale(NomRessource.OeufPoule, "Poule", null, "Coq")),
	Canne(NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.OeufCanne, "Canne", NomRessource.Plume, "Canard")),
	Oie(NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.OeufOie, "Oie", NomRessource.Plume, "Jars")),
	Cheval(NomZone.ETABLE, AnimalProduct.sameForMaleAndFemale(null, "Cheval")),
	Ane(NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.LaitAnesse, "Anesse", null, "Ane")),
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
