package agricore.projet.model.ressource;

import agricore.projet.model.zone.NomZone;
import agricore.projet.util.CheminAsset;

public enum NomRessource {
    POMME("pomme",
            CheminAsset.RESSOURCE + "pomme.png", Unite.KILOGRAM, NomZone.STOCK_DE_FRUIT),
    POIRE("poire",
            CheminAsset.RESSOURCE + "poire.png", Unite.KILOGRAM, NomZone.STOCK_DE_FRUIT),
    FRAISE("fraise",
            CheminAsset.RESSOURCE + "fraise.png", Unite.KILOGRAM, NomZone.STOCK_DE_FRUIT),
    LAIT_VACHE("lait de vache",
            CheminAsset.RESSOURCE + "lait.png", Unite.LITRE, NomZone.CUVE),
    LAIT_BREBIS("lait de brebis",
            CheminAsset.RESSOURCE + "lait.png", Unite.LITRE, NomZone.CUVE),
    LAIT_ANESSE("lait d'anesse",
            CheminAsset.RESSOURCE + "lait.png", Unite.LITRE, NomZone.CUVE),
    FROMAGE("fromage",
            CheminAsset.RESSOURCE + "fromage.png", Unite.KILOGRAM, NomZone.CHAMBRE_FROIDE),
    MAIS("mais",
            CheminAsset.RESSOURCE + "mais.png", Unite.KILOGRAM, NomZone.SILO),
    TOURNESOL("tournesol",
            CheminAsset.RESSOURCE + "fromage.png", Unite.KILOGRAM, NomZone.SILO),
    BLE("blé",
            CheminAsset.RESSOURCE + "ble.png",
            Unite.KILOGRAM, NomZone.SILO),
    COLZA("colza",
            CheminAsset.RESSOURCE + "colza.png",
            Unite.KILOGRAM, NomZone.SILO),
    FARINE_BLE("farine de blé",
            CheminAsset.RESSOURCE + "farine-blé.png",
            Unite.KILOGRAM, NomZone.SILO),
    PATE("pâte",
            CheminAsset.RESSOURCE + "fromage.png",
            Unite.ARBITRAIRE, NomZone.CHAMBRE_FROIDE),
    JUS_POMME("jus de pomme",
            CheminAsset.RESSOURCE + "jus-pomme.png",
            Unite.LITRE, NomZone.CUVE),
    OEUF_POULE("oeuf de poule", CheminAsset.RESSOURCE + "oeuf.png", Unite.ARBITRAIRE, NomZone.CHAMBRE_FROIDE),
    OEUF_OIE("oeuf d'oie", CheminAsset.RESSOURCE + "oeuf.png", Unite.ARBITRAIRE, NomZone.CHAMBRE_FROIDE),
    OEUF_CANNE("oeuf de canne", CheminAsset.RESSOURCE + "oeuf.png", Unite.ARBITRAIRE, NomZone.CHAMBRE_FROIDE),
    PLUME("plume", CheminAsset.RESSOURCE + "plume.png", Unite.KILOGRAM, NomZone.HANGAR),
    LAINE("laine", CheminAsset.RESSOURCE + "laine.png", Unite.KILOGRAM, NomZone.HANGAR),
    ESSENCE("essence", CheminAsset.RESSOURCE + "", Unite.LITRE, NomZone.RESERVOIR_ESSENCE);

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
}
