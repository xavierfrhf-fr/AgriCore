package agricore.projet.model.animal;

import agricore.projet.model.ressource.NomRessource;

public class AnimalProduct {
    private NomRessource maleRessource;
    private NomRessource femaleRessource;
    
    private String malePathSprite;
    private String femalePathSprite;
    
    private String maleNomAffichage;
    private String femaleNomAffichage;

    public static AnimalProduct sameForMaleAndFemale(NomRessource ressource, String nomAffichage, String pathSprite) {
        AnimalProduct product = new AnimalProduct();
        product.maleRessource = ressource;
        product.femaleRessource = ressource;
        product.maleNomAffichage = nomAffichage;
        product.femaleNomAffichage = nomAffichage;
        product.malePathSprite = pathSprite;
        product.femalePathSprite = pathSprite;
        return product;
    }

    public static AnimalProduct differentForMaleAndFemale(NomRessource femaleRessource, String femaleNomAffichage, String femalePathSprite, NomRessource maleRessource, String maleNomAffichage,  String malePathSprite) {
        AnimalProduct product = new AnimalProduct();
        product.femaleRessource = femaleRessource;
        product.femaleNomAffichage = femaleNomAffichage;
        product.maleRessource = maleRessource;
        product.maleNomAffichage = maleNomAffichage;
        product.malePathSprite = malePathSprite;
        product.femalePathSprite = femalePathSprite;
        return product;
    }

    public String getNomAffichage(boolean isMale){
        return isMale ? maleNomAffichage : femaleNomAffichage;
    }

    public NomRessource getNomRessource(boolean isMale){
        return isMale ? maleRessource : femaleRessource;
    }

    public String getPathSprite(boolean isMale) {
        return isMale ? malePathSprite : femalePathSprite;
    }
}