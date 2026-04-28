package agricore.projet.model;

import agricore.projet.model.zone.NomZone;

public enum EspeceAnimal {
	Cochon(NomZone.ETABLE),
	Vache(NomZone.ETABLE),
	Mouton(NomZone.ETABLE),
	Poule(NomZone.POULAILLER),
	Canard(NomZone.ETABLE),
	Oie(NomZone.ETABLE),
	Cheval(NomZone.ETABLE),
	Ane(NomZone.ETABLE),
	Lapin(NomZone.ETABLE);

	private final NomZone allowedZone;

	private EspeceAnimal(NomZone allowedZone) {
		this.allowedZone = allowedZone;
	}

	public NomZone getAllowedZone() {
		return allowedZone;
	}
}
