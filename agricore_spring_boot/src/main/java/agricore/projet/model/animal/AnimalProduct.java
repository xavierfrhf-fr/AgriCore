package agricore.projet.model.animal;

import agricore.projet.model.ressource.NomRessource;

public class AnimalProduct {
    private NomRessource maleRessource;
    private NomRessource femaleRessource;
    
    private String malenomAffichage;
    private String femalenomAffichage;

    public static AnimalProduct sameForMaleAndFemale(NomRessource ressource, String nomAffichage) {
        AnimalProduct product = new AnimalProduct();
        product.maleRessource = ressource;
        product.femaleRessource = ressource;
        product.malenomAffichage = nomAffichage;
        product.femalenomAffichage = nomAffichage;
        return product;
    }

    public static AnimalProduct differentForMaleAndFemale(NomRessource femaleRessource, String femalenomAffichage, NomRessource maleRessource, String malenomAffichage) {
        AnimalProduct product = new AnimalProduct();
        product.femaleRessource = femaleRessource;
        product.femalenomAffichage = femalenomAffichage;
        product.maleRessource = maleRessource;
        product.femalenomAffichage = malenomAffichage;
        return product;
    }

    public String getnomAffichage(boolean isMale){
        return isMale ? malenomAffichage : femalenomAffichage;
    }

    public NomRessource getNomRessource(boolean isMale){
        return isMale ? maleRessource : femaleRessource;
    }
}