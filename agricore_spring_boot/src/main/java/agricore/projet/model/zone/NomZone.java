package agricore.projet.model.zone;

import agricore.projet.model.ressource.NomRessource;
import agricore.projet.model.zone.position.ZoneShape;

public enum NomZone {
	CHAMPS("champ",
			null,
			ZoneShape.rectangle(4, 4),
			true),
	POULAILLER("poulailler",
			null,
			ZoneShape.rectangle(4, 2),
			false),
	ETABLE("étable",
			null,
			ZoneShape.rectangle(3, 3),
			false),
	SILO("silo",
			null,
			ZoneShape.rectangle(1, 3),
			false),
	CUVE("cuve",
			null,
			ZoneShape.rectangle(2, 2),
			false),
	STOCK_DE_FRUIT("stockage de fruit",
			null,
			ZoneShape.rectangle(1, 1),
			false),
	CUISINE("cuisine",
			null,
			ZoneShape.rectangle(2,2),
			false),
	MOULIN("moulin",
			null,
			ZoneShape.rectangle(2,2),
			false),
	FROMAGERIE("fromagerie",
			null,
			ZoneShape.rectangle(2,2),
			false),
	PRESSOIR("pressoir",
			null,
			ZoneShape.rectangle(2,2),
			false),
	CHAMBRE_FROIDE("chambre froide",
			null,
			ZoneShape.rectangle(3, 3),
			false);

	private final ZoneShape zoneShape;
	private final boolean autorisePlant;
	private final String nomAffichage;
	private final String pathSprite;

	private NomZone(String nomAffichage, String pathSprite, ZoneShape zoneShape, boolean autorisePlant) {
		this.zoneShape = zoneShape;
		this.autorisePlant = autorisePlant;
		this.nomAffichage = nomAffichage;
		this.pathSprite = pathSprite;

	}

	public ZoneShape getZoneShape() {
		return zoneShape;
	}

	public boolean isAutorisePlant() {
		return autorisePlant;
	}

	public String getNomAffichage() {
		return nomAffichage;
	}

	public String getPathSprite() {
		return pathSprite;
	}

}
