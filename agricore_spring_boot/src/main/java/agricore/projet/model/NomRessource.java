package agricore.projet.model;

public enum NomRessource {
    Pomme("Kg"),
    Poire("Kg"),
    Fraise("Kg"),
    Lait("L"),
    Fromage("Kg"),
    Mais("Kg"),
    Tournesol("Kg"),
    Blé("Kg"),
    Colza("Kg"),
    JusDePomme("L");

    private String unite;

    private NomRessource(String unite){
        this.unite = unite;
    }

    public String getUnite() {
        return unite;
    }

}


