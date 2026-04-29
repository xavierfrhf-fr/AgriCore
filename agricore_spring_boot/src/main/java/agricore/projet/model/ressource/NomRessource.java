package agricore.projet.model.ressource;

import java.util.stream.Stream;

import agricore.projet.model.zone.NomZone;

public enum NomRessource {
    POMME("pomme",
            null,
            Unite.KILOGRAM,
            NomZone.STOCK_DE_FRUIT),
    POIRE("poire",
            null,
            Unite.KILOGRAM, NomZone.STOCK_DE_FRUIT),
    FRAISE("fraise",
            null,
            Unite.KILOGRAM, NomZone.STOCK_DE_FRUIT),
    LAIT("lait",
            null,
            Unite.LITRE, NomZone.CUVE),
    FROMAGE("fromage",
            null,
            Unite.KILOGRAM, NomZone.CHAMBRE_FROIDE),
    MAIS("mais",
            null,
            Unite.KILOGRAM, NomZone.SILO),
    TOURNESOL("tournesol",
            null,
            Unite.KILOGRAM, NomZone.SILO),
    BLE("blé",
            null,
            Unite.KILOGRAM, NomZone.SILO),
    COLZA("colza",
            null,
            Unite.KILOGRAM, NomZone.SILO),
    FARINE_BLE("farine de blé",
            null,
            Unite.KILOGRAM, NomZone.SILO),
    PATE("pâte",
            null,
            Unite.ARBITRAIRE, NomZone.CHAMBRE_FROIDE),
    JUS_POMME("jus de pomme",
            null,
            Unite.LITRE, NomZone.CUVE);

    private final Unite uniteStockage;
    private final NomZone zoneStockage;
    private final String nomAffichage;
    private final String pathSprite;

    private NomRessource(String nomAffichage, String pathSprite, Unite uniteStockage, NomZone zoneStockage) {
        this.nomAffichage = nomAffichage;
        this.pathSprite = pathSprite;
        this.uniteStockage = uniteStockage;
        this.zoneStockage = zoneStockage;
    }

    public Unite getUniteStockage() {
        return uniteStockage;
    }

    public NomZone getZoneStockage() {
        return zoneStockage;
    }

    public String getNomAffichage() {
        return nomAffichage;
    }

    public String getPathSprite() {
        return pathSprite;
    }

    public static boolean isZoneUnique(NomZone zone) { // Les zones de stockage sont uniques, si une zone est une
                                                       // zoneStockage c'est une zone unique
        return Stream.of(NomRessource.values())
                .anyMatch(r -> r.getZoneStockage() == zone);
    }

}
