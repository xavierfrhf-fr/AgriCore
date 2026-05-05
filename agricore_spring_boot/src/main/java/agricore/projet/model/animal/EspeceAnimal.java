package agricore.projet.model.animal;

import agricore.projet.model.ressource.NomRessource;
import agricore.projet.model.zone.NomZone;
import agricore.projet.util.CheminAsset;

public enum EspeceAnimal {
	COCHON(CheminAsset.ANIMAL + "cochon.png",NomZone.ETABLE, AnimalProduct.sameForMaleAndFemale(null, "Cochon")),
	VACHE(CheminAsset.ANIMAL + "vache.png",NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.LAIT_VACHE, "Vache", null, "Taureau")),
	MOUTON(CheminAsset.ANIMAL + "mouton.png",NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.LAIT_BREBIS, "Brebis", NomRessource.LAINE, "Mouton")),
	CHEVRE(CheminAsset.ANIMAL + "chevre.png", NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.LAIT_CHEVRE, "Chèvre", null, "Bouc")),
	POULE(CheminAsset.ANIMAL + "poule.png",NomZone.POULAILLER, AnimalProduct.differentForMaleAndFemale(NomRessource.OEUF_POULE, "Poule", null, "Coq")),
	CANNE(CheminAsset.ANIMAL + "canne.png",NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.OEUF_CANNE, "Canne", NomRessource.PLUME, "Canard")),
	OIE(CheminAsset.ANIMAL + "oie.png",NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.OEUF_OIE, "Oie", NomRessource.PLUME, "Jars")),
	CHEVAL(CheminAsset.ANIMAL + "cheval.png",NomZone.ETABLE, AnimalProduct.sameForMaleAndFemale(null, "Cheval")),
	ANE(CheminAsset.ANIMAL + "ane.png",NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.LAIT_ANESSE, "Anesse", null, "Ane")),
	LAPIN(CheminAsset.ANIMAL + "lapin.png",NomZone.ETABLE, AnimalProduct.sameForMaleAndFemale(null, "Lapin"));

	private final String pathSprite;
	private final NomZone allowedZone;
	private final AnimalProduct product;

	private EspeceAnimal(String pathSprite, NomZone allowedZone, AnimalProduct product) {
		this.pathSprite = pathSprite;
		this.allowedZone = allowedZone;
		this.product = product;
	}

	public NomZone getAllowedZone() {
		return allowedZone;
	}

	public AnimalProduct getProduct() {
		return product;
	}

	public String getPathSprite() {
		return pathSprite;
	}

	
	
}
