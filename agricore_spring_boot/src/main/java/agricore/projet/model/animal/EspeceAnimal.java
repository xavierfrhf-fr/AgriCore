package agricore.projet.model.animal;

import agricore.projet.model.ressource.NomRessource;
import agricore.projet.model.zone.NomZone;
import agricore.projet.util.CheminAsset;

public enum EspeceAnimal {
	COCHON(NomZone.ETABLE, AnimalProduct.sameForMaleAndFemale(null, "Cochon", CheminAsset.ANIMAL + "cochon.png")),
	VACHE(NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.LAIT_VACHE, "Vache", CheminAsset.ANIMAL + "vache.png", null, "Taureau", CheminAsset.ANIMAL + "taureau.png")),
	MOUTON(NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.LAIT_BREBIS, "Brebis", CheminAsset.ANIMAL + "brebis.png", NomRessource.LAINE, "Mouton", CheminAsset.ANIMAL + "mouton.png")),
	CHEVRE(NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.LAIT_CHEVRE, "Chèvre", CheminAsset.ANIMAL + "chevre.png", null, "Bouc", CheminAsset.ANIMAL + "bouc.png")),
	POULE(NomZone.POULAILLER, AnimalProduct.differentForMaleAndFemale(NomRessource.OEUF_POULE, "Poule", CheminAsset.ANIMAL + "poule.png", null, "Coq", CheminAsset.ANIMAL + "coq.png")),
	CANNE(NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.OEUF_CANNE, "Canne", CheminAsset.ANIMAL + "canne.png", NomRessource.PLUME, "Canard",CheminAsset.ANIMAL + "canard.png")),
	OIE(NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.OEUF_OIE, "Oie", CheminAsset.ANIMAL + "oie.png", NomRessource.PLUME, "Jars", CheminAsset.ANIMAL + "oie.png")),
	CHEVAL(NomZone.ETABLE, AnimalProduct.sameForMaleAndFemale(null, "Cheval",CheminAsset.ANIMAL + "cheval.png")),
	ANE(NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.LAIT_ANESSE, "Anesse",CheminAsset.ANIMAL + "ane.png", null, "Ane",CheminAsset.ANIMAL + "ane.png")),
	LAPIN(NomZone.ETABLE, AnimalProduct.sameForMaleAndFemale(null, "Lapin",CheminAsset.ANIMAL + "lapin.png"));

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
