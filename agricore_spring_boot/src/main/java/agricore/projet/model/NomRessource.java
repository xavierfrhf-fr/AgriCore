package agricore.projet.model;

public enum NomRessource {
    Pomme(Unite.KILOGRAM),
    Poire(Unite.KILOGRAM),
    Fraise(Unite.KILOGRAM),
    Lait(Unite.LITRE),
    Fromage(Unite.KILOGRAM),
    Mais(Unite.KILOGRAM),
    Tournesol(Unite.KILOGRAM),
    Blé(Unite.KILOGRAM),
    Colza(Unite.KILOGRAM),
    JusDePomme(Unite.KILOGRAM);

    private final Unite uniteStockage;

    private NomRessource(Unite uniteStockage){
        this.uniteStockage = uniteStockage;
    }

    public Unite getUniteStockage() {
        return uniteStockage;
    }

}


