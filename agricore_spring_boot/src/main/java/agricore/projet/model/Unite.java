package agricore.projet.model;

public enum Unite {
    GRAMME("g","masse", 1),
    KILOGRAM("Kg","masse", 1000),
    LITRE("L","volume", 1000),
    BOUTEILLE("Bouteille (75cl)", "volume", 750),
    ARBITRAIRE("Unité","arbitraire", 1),
    DOUZAINE("Douzaine", "arbitraire", 12);

    private final String affichage;//Utilisé pour l'affichage de l'unité considéré
    private final String type;//Permet de savoir si deux unités sont convertibles ensemble
    private final int value;//Facteur de conversion entre unité convertibles (masse en gramme, volume en mL, arbitraire en 1 unité)
    
    Unite(String affichage, String type, int value){
        this.affichage = affichage;
        this.type = type;
        this.value = value;
    }

    public String getAffichage(){
        return affichage;
    }

    public String getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public double convertirVers(Unite unite, double quantite){
        if (this.type.equals(unite.type)){
            return quantite * this.value / unite.value;
        }else{
            throw new IllegalArgumentException("Conversion impossible");
        }
    }
}
