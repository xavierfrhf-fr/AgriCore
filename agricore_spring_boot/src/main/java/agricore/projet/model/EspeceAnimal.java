package agricore.projet.model;

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

	private NomZone allowedZone;

	private EspeceAnimal(NomZone allowedZone) {
		this.allowedZone = allowedZone;
	}
}
