package agricore.projet.model.animal;

import agricore.projet.model.TypeVehicule;
import agricore.projet.model.ressource.NomRessource;
import agricore.projet.model.zone.NomZone;
import agricore.projet.util.CheminAsset;

public enum EspeceAnimal {
	COCHON(NomZone.ETABLE, AnimalProduct.sameForMaleAndFemale(null, "Cochon", CheminAsset.ANIMAL + "cochon.png"), TypeVehicule.TRACTEUR, 2),
	VACHE(NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.LAIT_VACHE, "Vache", CheminAsset.ANIMAL + "vache.png", null, "Taureau", CheminAsset.ANIMAL + "taureau.png"), TypeVehicule.TRACTEUR, 2),
	MOUTON(NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.LAIT_BREBIS, "Brebis", CheminAsset.ANIMAL + "brebis.png", NomRessource.LAINE, "Mouton", CheminAsset.ANIMAL + "mouton.png"), TypeVehicule.TRACTEUR, 2),
	CHEVRE(NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.LAIT_CHEVRE, "Chèvre", CheminAsset.ANIMAL + "chevre.png", null, "Bouc", CheminAsset.ANIMAL + "bouc.png"), TypeVehicule.TRACTEUR, 2),
	POULE(NomZone.POULAILLER, AnimalProduct.differentForMaleAndFemale(NomRessource.OEUF_POULE, "Poule", CheminAsset.ANIMAL + "poule.png", null, "Coq", CheminAsset.ANIMAL + "coq.png"), TypeVehicule.TRACTEUR, 1),
	CANNE(NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.OEUF_CANNE, "Canne", CheminAsset.ANIMAL + "canne.png", NomRessource.PLUME, "Canard",CheminAsset.ANIMAL + "canard.png"), TypeVehicule.TRACTEUR, 2),
	OIE(NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.OEUF_OIE, "Oie", CheminAsset.ANIMAL + "oie.png", NomRessource.PLUME, "Jars", CheminAsset.ANIMAL + "oie.png"), TypeVehicule.TRACTEUR, 2),
	CHEVAL(NomZone.ETABLE, AnimalProduct.sameForMaleAndFemale(null, "Cheval",CheminAsset.ANIMAL + "cheval.png"), TypeVehicule.TRACTEUR, 3),
	ANE(NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.LAIT_ANESSE, "Anesse",CheminAsset.ANIMAL + "ane.png", null, "Ane",CheminAsset.ANIMAL + "ane.png"), TypeVehicule.TRACTEUR, 2),
	LAPIN(NomZone.ETABLE, AnimalProduct.sameForMaleAndFemale(null, "Lapin",CheminAsset.ANIMAL + "lapin.png"), TypeVehicule.TRACTEUR, 1);

	private final NomZone allowedZone;
	private final AnimalProduct product;
	private TypeVehicule vehiculeAchatRequis;
	private int distanceKmAchat;

	private EspeceAnimal(NomZone allowedZone, AnimalProduct product, TypeVehicule vehiculeAchatRequis, int distanceKmAchat) {
		this.allowedZone = allowedZone;
		this.product = product;
		this.vehiculeAchatRequis = vehiculeAchatRequis;
		this.distanceKmAchat = distanceKmAchat;
	}

	public NomZone getAllowedZone() {
		return allowedZone;
	}

	public AnimalProduct getProduct() {
		return product;
	}

	public TypeVehicule getVehiculeAchatRequis() {
		return vehiculeAchatRequis;
	}
	
	public int getDistanceKmAchat() {
		return distanceKmAchat;
	}
	
	
}
