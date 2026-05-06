package agricore.projet.model.animal;

import agricore.projet.model.TypeVehicule;
import agricore.projet.model.ressource.NomRessource;
import agricore.projet.model.zone.NomZone;
import agricore.projet.util.CheminAsset;

public enum EspeceAnimal {
	COCHON(NomZone.ETABLE, AnimalDimorphisme.sameForMaleAndFemale(null, "Cochon", CheminAsset.ANIMAL + "cochon.png"), TypeVehicule.TRACTEUR, 2),
	VACHE(NomZone.ETABLE, AnimalDimorphisme.differentForMaleAndFemale(NomRessource.LAIT_VACHE, "Vache", CheminAsset.ANIMAL + "vache.png", null, "Taureau", CheminAsset.ANIMAL + "taureau.png"), TypeVehicule.TRACTEUR, 2),
	MOUTON(NomZone.ETABLE, AnimalDimorphisme.differentForMaleAndFemale(NomRessource.LAIT_BREBIS, "Brebis", CheminAsset.ANIMAL + "brebis.png", NomRessource.LAINE, "Mouton", CheminAsset.ANIMAL + "mouton.png"), TypeVehicule.TRACTEUR, 2),
	CHEVRE(NomZone.ETABLE, AnimalDimorphisme.differentForMaleAndFemale(NomRessource.LAIT_CHEVRE, "Chèvre", CheminAsset.ANIMAL + "chevre.png", null, "Bouc", CheminAsset.ANIMAL + "bouc.png"), TypeVehicule.TRACTEUR, 2),
	POULE(NomZone.POULAILLER, AnimalDimorphisme.differentForMaleAndFemale(NomRessource.OEUF_POULE, "Poule", CheminAsset.ANIMAL + "poule.png", NomRessource.PLUME, "Coq", CheminAsset.ANIMAL + "coq.png"), TypeVehicule.TRACTEUR, 1),
	CANNE(NomZone.ETABLE, AnimalDimorphisme.differentForMaleAndFemale(NomRessource.OEUF_CANNE, "Canne", CheminAsset.ANIMAL + "canne.png", NomRessource.PLUME, "Canard",CheminAsset.ANIMAL + "canard.png"), TypeVehicule.TRACTEUR, 2),
	OIE(NomZone.ETABLE, AnimalDimorphisme.differentForMaleAndFemale(NomRessource.OEUF_OIE, "Oie", CheminAsset.ANIMAL + "oie.png", NomRessource.PLUME, "Jars", CheminAsset.ANIMAL + "oie.png"), TypeVehicule.TRACTEUR, 2),
	CHEVAL(NomZone.ETABLE, AnimalDimorphisme.sameForMaleAndFemale(null, "Cheval",CheminAsset.ANIMAL + "cheval.png"), TypeVehicule.TRACTEUR, 3),
	ANE(NomZone.ETABLE, AnimalDimorphisme.differentForMaleAndFemale(NomRessource.LAIT_ANESSE, "Anesse",CheminAsset.ANIMAL + "ane.png", null, "Ane",CheminAsset.ANIMAL + "ane.png"), TypeVehicule.TRACTEUR, 2),
	LAPIN(NomZone.ETABLE, AnimalDimorphisme.sameForMaleAndFemale(null, "Lapin",CheminAsset.ANIMAL + "lapin.png"), TypeVehicule.TRACTEUR, 1);

	private final NomZone allowedZone;
	private final AnimalDimorphisme dimorphisme;
	private TypeVehicule vehiculeAchatRequis;
	private int distanceKmAchat;

	private EspeceAnimal(NomZone allowedZone, AnimalDimorphisme dimorphisme, TypeVehicule vehiculeAchatRequis, int distanceKmAchat) {
		this.allowedZone = allowedZone;
		this.dimorphisme = dimorphisme;
		this.vehiculeAchatRequis = vehiculeAchatRequis;
		this.distanceKmAchat = distanceKmAchat;
	}

	public NomZone getAllowedZone() {
		return allowedZone;
	}

	public AnimalDimorphisme getDimorphisme() {
		return dimorphisme;
	}

	public TypeVehicule getVehiculeAchatRequis() {
		return vehiculeAchatRequis;
	}
	
	public int getDistanceKmAchat() {
		return distanceKmAchat;
	}
	
	
}
