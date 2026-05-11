package agricore.projet.model.animal;

import agricore.projet.model.ressource.NomRessource;

public class AnimalDimorphisme {
    private NomRessource maleRessource;
    private NomRessource femaleRessource;

    private Integer maleProductionTime;
    private Integer femaleProductionTime;

    private String malePathSprite;
    private String femalePathSprite;
    
    private String maleNomAffichage;
    private String femaleNomAffichage;

    public static AnimalDimorphisme sameForMaleAndFemale(NomRessource ressource, String nomAffichage, String pathSprite, Integer productionTime) {
        AnimalDimorphisme product = new AnimalDimorphisme();
        product.maleRessource = ressource;
        product.femaleRessource = ressource;
        product.maleNomAffichage = nomAffichage;
        product.femaleNomAffichage = nomAffichage;
        product.malePathSprite = pathSprite;
        product.femalePathSprite = pathSprite;
        product.maleProductionTime = productionTime;
        product.femaleProductionTime = productionTime;
        return product;
    }

    public static AnimalDimorphisme sameForMaleAndFemale(String nomAffichage, String pathSprite) {
        AnimalDimorphisme product = new AnimalDimorphisme();
        product.maleRessource = null;
        product.femaleRessource = null;
        product.maleNomAffichage = nomAffichage;
        product.femaleNomAffichage = nomAffichage;
        product.malePathSprite = pathSprite;
        product.femalePathSprite = pathSprite;
        product.maleProductionTime = null;
        product.femaleProductionTime = null;
        return product;
    }

    public static AnimalDimorphisme differentForMaleAndFemale(NomRessource femaleRessource, String femaleNomAffichage, String femalePathSprite, Integer femaleProductionTime , NomRessource maleRessource, String maleNomAffichage,  String malePathSprite, Integer maleProductionTime) {
        AnimalDimorphisme product = new AnimalDimorphisme();
        product.femaleRessource = femaleRessource;
        product.femaleNomAffichage = femaleNomAffichage;
        product.maleRessource = maleRessource;
        product.maleNomAffichage = maleNomAffichage;
        product.malePathSprite = malePathSprite;
        product.femalePathSprite = femalePathSprite;
        product.maleProductionTime = maleProductionTime;
        product.femaleProductionTime = femaleProductionTime;
        return product;
    }

    public static AnimalDimorphisme differentForMaleAndFemale(String femaleNomAffichage, String femalePathSprite , String maleNomAffichage,  String malePathSprite) {
        AnimalDimorphisme product = new AnimalDimorphisme();
        product.femaleRessource = null;
        product.femaleNomAffichage = femaleNomAffichage;
        product.maleRessource = null;
        product.maleNomAffichage = maleNomAffichage;
        product.malePathSprite = malePathSprite;
        product.femalePathSprite = femalePathSprite;
        product.maleProductionTime = null;
        product.femaleProductionTime = null;
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

    public Integer getProductionTime(boolean isMale){
        return isMale ? maleProductionTime : femaleProductionTime;
    }
}