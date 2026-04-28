package agricore.projet.model.ressource;

import java.util.stream.Stream;

import agricore.projet.model.zone.NomZone;

public enum NomRessource {
    Pomme(Unite.KILOGRAM, NomZone.STOCK_DE_FRUIT),
    Poire(Unite.KILOGRAM, NomZone.STOCK_DE_FRUIT),
    Fraise(Unite.KILOGRAM, NomZone.STOCK_DE_FRUIT),
    Lait(Unite.LITRE, NomZone.CUVE),
    Fromage(Unite.KILOGRAM, NomZone.CHAMBRE_FROIDE),
    Mais(Unite.KILOGRAM, NomZone.SILO),
    Tournesol(Unite.KILOGRAM, NomZone.SILO),
    Blé(Unite.KILOGRAM, NomZone.SILO),
    Colza(Unite.KILOGRAM, NomZone.SILO),
    FarineBlé(Unite.KILOGRAM, NomZone.SILO),
    Pate(Unite.ARBITRAIRE, NomZone.CHAMBRE_FROIDE),
    JusDePomme(Unite.LITRE, NomZone.CUVE);

    private final Unite uniteStockage;
    private final NomZone zoneStockage;

    private NomRessource(Unite uniteStockage, NomZone zoneStockage) {
        this.uniteStockage = uniteStockage;
        this.zoneStockage = zoneStockage;
    }

    public Unite getUniteStockage() {
        return uniteStockage;
    }

    public NomZone getZoneStockage() {
        return zoneStockage;
    }

    public static boolean isZoneUnique(NomZone zone) { // Les zones de stockage sont uniques, si une zone est une
                                                       // zoneStockage c'est une zone unique
        return Stream.of(NomRessource.values())
                .anyMatch(r -> r.getZoneStockage() == zone);
    }

}
