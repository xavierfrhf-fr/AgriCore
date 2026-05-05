package agricore.projet.model;

import agricore.projet.model.zone.NomZone;

public enum EspeceAnimal {
	Cochon(NomZone.ETABLE, TypeVehicule.TRACTEUR),
	Vache(NomZone.ETABLE, TypeVehicule.TRACTEUR),
	Mouton(NomZone.ETABLE, TypeVehicule.TRACTEUR),
	Poule(NomZone.POULAILLER, TypeVehicule.TRACTEUR),
	Canard(NomZone.ETABLE, TypeVehicule.TRACTEUR),
	Oie(NomZone.ETABLE, TypeVehicule.TRACTEUR),
	Cheval(NomZone.ETABLE, TypeVehicule.TRACTEUR),
	Ane(NomZone.ETABLE, TypeVehicule.TRACTEUR),
	Lapin(NomZone.ETABLE, TypeVehicule.TRACTEUR);

	private final NomZone allowedZone;

	public TypeVehicule vehiculeAchatRequis;

	private EspeceAnimal(NomZone allowedZone, TypeVehicule vehiculeAchatRequis) {
		this.allowedZone = allowedZone;
		this.vehiculeAchatRequis = vehiculeAchatRequis;
	}

	public NomZone getAllowedZone() {
		return allowedZone;
	}

	public TypeVehicule getVehiculeAchatRequis() {
		return vehiculeAchatRequis;
	}

	

}
