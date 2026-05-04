package agricore.projet.model.animal;

import agricore.projet.model.ressource.NomRessource;
import agricore.projet.model.zone.NomZone;
import agricore.projet.util.CheminAsset;

public enum EspeceAnimal {
	Cochon(CheminAsset.ANIMAL + "cochon.png",NomZone.ETABLE, AnimalProduct.sameForMaleAndFemale(null, "Cochon")),
	Vache(CheminAsset.ANIMAL + "vache.png",NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.LAIT_VACHE, "Vache", null, "Taureau")),
	Mouton(CheminAsset.ANIMAL + "mouton.png",NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.LAIT_BREBIS, "Brebis", NomRessource.LAINE, "Mouton")),
	Poule(CheminAsset.ANIMAL + "poule.png",NomZone.POULAILLER, AnimalProduct.differentForMaleAndFemale(NomRessource.OEUF_POULE, "Poule", null, "Coq")),
	Canne(CheminAsset.ANIMAL + "canne.png",NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.OEUF_CANNE, "Canne", NomRessource.PLUME, "Canard")),
	Oie(CheminAsset.ANIMAL + "oie.png",NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.OEUF_OIE, "Oie", NomRessource.PLUME, "Jars")),
	Cheval(CheminAsset.ANIMAL + "cheval.png",NomZone.ETABLE, AnimalProduct.sameForMaleAndFemale(null, "Cheval")),
	Ane(CheminAsset.ANIMAL + "ane.png",NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.LAIT_ANESSE, "Anesse", null, "Ane")),
	Lapin(CheminAsset.ANIMAL + "lapin.png",NomZone.ETABLE, AnimalProduct.sameForMaleAndFemale(null, "Lapin"));

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
