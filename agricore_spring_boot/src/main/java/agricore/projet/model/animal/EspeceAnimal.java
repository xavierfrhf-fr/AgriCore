package agricore.projet.model.animal;

import agricore.projet.model.TypeVehicule;
import agricore.projet.model.ressource.NomRessource;
import agricore.projet.model.zone.NomZone;
import agricore.projet.util.CheminAsset;

public enum EspeceAnimal {
	Cochon(CheminAsset.ANIMAL + "cochon.png",NomZone.ETABLE, AnimalProduct.sameForMaleAndFemale(null, "Cochon"), TypeVehicule.TRACTEUR),
	Vache(CheminAsset.ANIMAL + "vache.png",NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.LAIT_VACHE, "Vache", null, "Taureau"), TypeVehicule.TRACTEUR),
	Mouton(CheminAsset.ANIMAL + "mouton.png",NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.LAIT_BREBIS, "Brebis", NomRessource.LAINE, "Mouton"), TypeVehicule.TRACTEUR),
	Poule(CheminAsset.ANIMAL + "poule.png",NomZone.POULAILLER, AnimalProduct.differentForMaleAndFemale(NomRessource.OEUF_POULE, "Poule", null, "Coq"), TypeVehicule.TRACTEUR),
	Canne(CheminAsset.ANIMAL + "canne.png",NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.OEUF_CANNE, "Canne", NomRessource.PLUME, "Canard"), TypeVehicule.TRACTEUR),
	Oie(CheminAsset.ANIMAL + "oie.png",NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.OEUF_OIE, "Oie", NomRessource.PLUME, "Jars"), TypeVehicule.TRACTEUR),
	Cheval(CheminAsset.ANIMAL + "cheval.png",NomZone.ETABLE, AnimalProduct.sameForMaleAndFemale(null, "Cheval"), TypeVehicule.TRACTEUR),
	Ane(CheminAsset.ANIMAL + "ane.png",NomZone.ETABLE, AnimalProduct.differentForMaleAndFemale(NomRessource.LAIT_ANESSE, "Anesse", null, "Ane"), TypeVehicule.TRACTEUR),
	Lapin(CheminAsset.ANIMAL + "lapin.png",NomZone.ETABLE, AnimalProduct.sameForMaleAndFemale(null, "Lapin"), TypeVehicule.TRACTEUR);

	private final String pathSprite;
	private final NomZone allowedZone;
	private final AnimalProduct product;
	private TypeVehicule vehiculeAchatRequis;

	private EspeceAnimal(String pathSprite, NomZone allowedZone, AnimalProduct product,TypeVehicule vehiculeAchatRequis) {
		this.pathSprite = pathSprite;
		this.allowedZone = allowedZone;
		this.product = product;
		this.vehiculeAchatRequis = vehiculeAchatRequis;
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

	public TypeVehicule getVehiculeAchatRequis() {
		return vehiculeAchatRequis;
	}

	
	
}
