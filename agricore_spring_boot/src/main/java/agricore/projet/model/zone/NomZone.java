package agricore.projet.model.zone;

import agricore.projet.model.ressource.NomRessource;
import agricore.projet.model.zone.position.ZoneShape;

public enum NomZone {
	CHAMPS(ZoneShape.rectangle(4, 4),
			true),
	POULAILLER(ZoneShape.rectangle(4, 2),
			false),
	ETABLE(ZoneShape.rectangle(3, 3),
			false),
	SILO(ZoneShape.rectangle(1, 3),
			false),
	CUVE(ZoneShape.rectangle(2, 2),
			false),
	STOCK_DE_FRUIT(ZoneShape.rectangle(1, 1),
			false),
	CHAMBRE_FROIDE(ZoneShape.rectangle(3, 3),
			false);

	private final ZoneShape zoneShape;
	private final boolean autorisePlant;

	private NomZone(ZoneShape zoneShape, boolean autorisePlant) {
		this.zoneShape = zoneShape;
		this.autorisePlant = autorisePlant;
	}

	public ZoneShape getZoneShape() {
		return zoneShape;
	}

	public boolean isAutorisePlant() {
		return autorisePlant;
	}

}
