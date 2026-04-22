package agricore.projet.model;

import java.util.Optional;

public enum Unite {
    GRAMME("g","masse", 1, false),
    KILOGRAM("Kg","masse", 1000, true),
    LITRE("L","volume", 1000, true),
    BOUTEILLE("Bouteille (75cl)", "volume", 750, false),
    ARBITRAIRE("Unité","arbitraire", 1, true),
    DOUZAINE("Douzaine", "arbitraire", 12, false);

    private final String affichage;//Utilisé pour l'affichage de l'unité considéré
    private final String type;//Permet de savoir si deux unités sont convertibles ensemble
    private final int value;//Facteur de conversion entre unité convertibles (masse en gramme, volume en mL, arbitraire en 1 unité)
    private final boolean reference; //Unité de référence (pour prix au Kg ou au L)

    Unite(String affichage, String type, int value, boolean reference) {
        this.affichage = affichage;
        this.type = type;
        this.value = value;
        this.reference = reference;
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

    public boolean isReference() {
        return reference;
    }

    public double convertirVers(Unite unite, double quantite){
        if (this.type.equals(unite.type)){
            return quantite * this.value / unite.value;
        }else{
            throw new IllegalArgumentException("Conversion impossible");
        }
    }

    public static Unite getReference(Unite unite) {
        for (Unite u : Unite.values()){
            if (u.getType().equals(unite.type) && u.isReference()){
                return u;
            }
        }
        throw new IllegalArgumentException("Conversion impossible");//Ne devrais jamais arrivé car 1 référence par type
    }
}
