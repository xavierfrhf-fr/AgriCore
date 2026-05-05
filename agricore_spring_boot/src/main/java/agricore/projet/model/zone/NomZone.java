package agricore.projet.model.zone;

import agricore.projet.model.zone.position.ZoneShape;
import agricore.projet.util.CheminAsset;

public enum NomZone {
	CHAMPS("champ",
			CheminAsset.ZONE + "CHAMP.png",
			ZoneShape.rectangle(4, 3),
			"Zone de culture pour produire céréales et légumes.",
			TypeZone.PLANTS),

	POULAILLER("poulailler",
			CheminAsset.ZONE + "poulailler.png",
			ZoneShape.rectangle(2, 2),
			"Abri pour élever des poules et produire des œufs.",
			TypeZone.ANIMAL),

	ETABLE("étable",
			CheminAsset.ZONE + "etable.png",
			ZoneShape.rectangle(2, 3),
			"Bâtiment pour héberger le bétail comme vaches ou moutons.",
			TypeZone.ANIMAL),

	SILO("silo",
			CheminAsset.ZONE + "silo.png",
			ZoneShape.rectangle(1, 3),
			"Structure de stockage vertical pour grains et récoltes.",
			TypeZone.STORAGE),

	CUVE("cuve",
			CheminAsset.ZONE + "CUVE.png",
			ZoneShape.rectangle(3, 2),
			"Réservoir pour stocker liquides comme eau ou carburant.",
			TypeZone.STORAGE),

	STOCK_DE_FRUIT("stockage de fruit",
			CheminAsset.ZONE + "STOCK_FRUIT.png",
			ZoneShape.rectangle(2, 2),
			"Espace dédié à la conservation des fruits récoltés.",
			TypeZone.STORAGE),

	CUISINE("cuisine",
			CheminAsset.ZONE + "CUISINE.png",
			ZoneShape.rectangle(3,2),
			"Lieu de transformation des produits pour créer des plats ou recettes.",
			TypeZone.UTILITY),

	MOULIN("moulin",
			CheminAsset.ZONE + "MOULIN.png",
			ZoneShape.rectangle(2,3),
			"Permet de transformer les céréales en farine.",
			TypeZone.UTILITY),

	FROMAGERIE("fromagerie",
			CheminAsset.ZONE + "FROMAGERIE.png",
			ZoneShape.rectangle(3,2),
			"Production de fromage à partir du lait collecté.",
			TypeZone.UTILITY),

	PRESSOIR("pressoir",
			CheminAsset.ZONE + "PRESSOIR.png",
			ZoneShape.rectangle(3,2),
			"Extraction de jus ou huile à partir de fruits ou graines.",
			TypeZone.UTILITY),

	CHAMBRE_FROIDE("chambre froide",
			CheminAsset.ZONE + "CHAMBRE_FROIDE.png",
			ZoneShape.rectangle(3, 3),
			"Espace réfrigéré pour conserver les produits périssables.",
			TypeZone.STORAGE),

	HANGAR("hangar",
			CheminAsset.ZONE + "HANGAR.png",
			ZoneShape.rectangle(3,3),
			"Grand abri pour stocker matériel et véhicules agricoles.",
			TypeZone.STORAGE),

	RESERVOIR_ESSENCE("réservoir à essence",
			CheminAsset.ZONE + "CITERNE.png",
			ZoneShape.rectangle(3, 3),
			"Stockage sécurisé de carburant pour machines agricoles.",
			TypeZone.STORAGE),

	VERGER("verger",
			CheminAsset.ZONE + "VERGER.png",
			ZoneShape.rectangle(3,2),
			"Zone plantée d’arbres fruitiers pour récolte saisonnière.",
			TypeZone.PLANTS),

	SERRE("serre",
			CheminAsset.ZONE + "serre.png",
			ZoneShape.rectangle(2,3),
			"Espace protégé pour cultiver plantes hors saison.",
			TypeZone.PLANTS),

	VOLIERE("voliere",
			CheminAsset.ZONE+"VOLIERE.png",
			ZoneShape.rectangle(4,1),
			"Enclos pour élever oiseaux en toute sécurité.",
			TypeZone.ANIMAL);

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
