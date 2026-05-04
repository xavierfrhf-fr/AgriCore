package agricore.projet.model.zone;

import agricore.projet.model.ressource.NomRessource;
import agricore.projet.model.zone.position.ZoneShape;

public enum NomZone {
	CHAMPS("champ",
			null,
			ZoneShape.rectangle(4, 4),
			TypeZone.PLANTS),
	POULAILLER("poulailler",
			null,
			ZoneShape.rectangle(4, 2),
			TypeZone.ANIMAL),
	ETABLE("étable",
			null,
			ZoneShape.rectangle(3, 3),
			TypeZone.ANIMAL),
	SILO("silo",
			null,
			ZoneShape.rectangle(1, 3),
			TypeZone.STORAGE),
	CUVE("cuve",
			null,
			ZoneShape.rectangle(2, 2),
			TypeZone.STORAGE),
	STOCK_DE_FRUIT("stockage de fruit",
			null,
			ZoneShape.rectangle(1, 1),
			TypeZone.STORAGE),
	CUISINE("cuisine",
			null,
			ZoneShape.rectangle(2,2),
			TypeZone.UTILITY),
	MOULIN("moulin",
			null,
			ZoneShape.rectangle(2,2),
			TypeZone.UTILITY),
	FROMAGERIE("fromagerie",
			null,
			ZoneShape.rectangle(2,2),
			TypeZone.UTILITY),
	PRESSOIR("pressoir",
			null,
			ZoneShape.rectangle(2,2),
			TypeZone.UTILITY),
	CHAMBRE_FROIDE("chambre froide",
			null,
			ZoneShape.rectangle(3, 3),
			TypeZone.STORAGE),
	RESERVOIR_ESSENCE("réservoir à essence",
			null,
			ZoneShape.rectangle(3,3),
			TypeZone.STORAGE);

	private final ZoneShape zoneShape;
	private final String nomAffichage;
	private final String pathSprite;
	private final TypeZone typeZone;

	private NomZone(String nomAffichage, String pathSprite, ZoneShape zoneShape, TypeZone typeZone) {
		this.zoneShape = zoneShape;
		this.nomAffichage = nomAffichage;
		this.pathSprite = pathSprite;
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

	public boolean isZoneUnique(){
		return (this.typeZone.equals(TypeZone.STORAGE) || this.typeZone.equals(TypeZone.UTILITY));
	}

}
