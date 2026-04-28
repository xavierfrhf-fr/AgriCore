package agricore.projet.model;

import agricore.projet.model.zone.NomZone;

public enum EspeceAnimal {
	Cochon(NomZone.ETABLE, true),
	Vache(NomZone.ETABLE, false),
	Mouton(NomZone.ETABLE, true),
	Poule(NomZone.POULAILLER, false),
	Coq(NomZone.POULAILLER, true),
	Canne(NomZone.ETABLE, false),
	Canard(NomZone.ETABLE, true),
	Oie(NomZone.ETABLE, false),
	Cheval(NomZone.ETABLE, true),
	Ane(NomZone.ETABLE, true),
	Lapin(NomZone.ETABLE, true);

	private final NomZone allowedZone;
	private final boolean male;

	private EspeceAnimal(NomZone allowedZone, boolean male) {
		this.allowedZone = allowedZone;
		this.male = male;
	}

	public NomZone getAllowedZone() {
		return allowedZone;
	}

	public boolean isMale() {
		return male;
	}
	
}
