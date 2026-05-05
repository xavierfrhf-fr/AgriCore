package agricore.projet.model.zone;

import agricore.projet.model.zone.position.ZoneShape;
import agricore.projet.util.CheminAsset;

public enum NomZone {
	CHAMPS("champ",
			CheminAsset.ZONE + "CHAMP.png",
			ZoneShape.rectangle(4, 4),
			"description",
			TypeZone.PLANTS),
	POULAILLER("poulailler",
			CheminAsset.ZONE + "poulailler.png",
			ZoneShape.rectangle(4, 2),
			"description",
			TypeZone.ANIMAL),
	ETABLE("étable",
			CheminAsset.ZONE + "etable.png",
			ZoneShape.rectangle(3, 3),
			"description",
			TypeZone.ANIMAL),
	SILO("silo",
			CheminAsset.ZONE + "silo.png",
			ZoneShape.rectangle(1, 3),
			"description",
			TypeZone.STORAGE),
	CUVE("cuve",
			CheminAsset.ZONE + "CUVE.png",
			ZoneShape.rectangle(2, 2),
			"description",
			TypeZone.STORAGE),
	STOCK_DE_FRUIT("stockage de fruit",
			CheminAsset.ZONE + "STOCK_FRUIT.png",
			ZoneShape.rectangle(1, 1),
			"description",
			TypeZone.STORAGE),
	CUISINE("cuisine",
			CheminAsset.ZONE + "CUISINE.png",
			ZoneShape.rectangle(2,2),
			"description",
			TypeZone.UTILITY),
	MOULIN("moulin",
			CheminAsset.ZONE + "MOULIN.png",
			ZoneShape.rectangle(2,2),
			"description",
			TypeZone.UTILITY),
	FROMAGERIE("fromagerie",
			CheminAsset.ZONE + "FROMAGERIE.png",
			ZoneShape.rectangle(2,2),
			"description",
			TypeZone.UTILITY),
	PRESSOIR("pressoir",
			CheminAsset.ZONE + "PRESSOIR.png",
			ZoneShape.rectangle(2,2),
			"description",
			TypeZone.UTILITY),
	CHAMBRE_FROIDE("chambre froide",
			CheminAsset.ZONE + "CHAMBRE_FROIDE.png",
			ZoneShape.rectangle(3, 3),
			"description",
			TypeZone.STORAGE),
	HANGAR("hangar",
			CheminAsset.ZONE + "HANGAR.png",
			ZoneShape.rectangle(3,3),
			"description",
			TypeZone.STORAGE),
	RESERVOIR_ESSENCE("réservoir à essence",
			null,
			ZoneShape.rectangle(3, 3),
			TypeZone.STORAGE),
	RESERVOIR_ESSENCE("réservoir à essence",
			null,
			ZoneShape.rectangle(3,3),
			TypeZone.STORAGE);

	private final ZoneShape zoneShape;
	private final String nomAffichage;
	private final String description;
	private final String pathSprite;
	private final TypeZone typeZone;

	private NomZone(String nomAffichage, String pathSprite, ZoneShape zoneShape, String description, TypeZone typeZone) {
		this.zoneShape = zoneShape;
		this.nomAffichage = nomAffichage;
		this.pathSprite = pathSprite;
        this.description = description;
        this.typeZone = typeZone;

	}

	public ZoneShape getZoneShape() {
		return zoneShape;
	}

	public TypeZone getTypeZone() {
		return typeZone;
	}

	public String getNomAffichage() {
		return nomAffichage;
	}

	public String getPathSprite() {
		return pathSprite;
	}

	public String getDescription() {
		return description;
	}

	public boolean isZoneUnique(){
		return (this.typeZone.equals(TypeZone.STORAGE) || this.typeZone.equals(TypeZone.UTILITY));
	}

}
