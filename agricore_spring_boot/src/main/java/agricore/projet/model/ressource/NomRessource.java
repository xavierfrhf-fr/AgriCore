package agricore.projet.model.ressource;

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
    FarineBlé(Unite.KILOGRAM),
    Pate(Unite.ARBITRAIRE),
    JusDePomme(Unite.LITRE);

    private final Unite uniteStockage;

    private NomRessource(Unite uniteStockage) {
        this.uniteStockage = uniteStockage;
    }

    public Unite getUniteStockage() {
        return uniteStockage;
    }

}
