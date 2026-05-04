package agricore.projet.model.animal;

import agricore.projet.model.ressource.NomRessource;

public class AnimalProduct {
    private NomRessource maleRessource;
    private NomRessource femaleRessource;
    
    private String maleNomAff;
    private String femaleNomAff;

    public static AnimalProduct sameForMaleAndFemale(NomRessource ressource, String nomAff) {
        AnimalProduct product = new AnimalProduct();
        product.maleRessource = ressource;
        product.femaleRessource = ressource;
        product.maleNomAff = nomAff;
        product.femaleNomAff = nomAff;
        return product;
    }

    public static AnimalProduct differentForMaleAndFemale(NomRessource femaleRessource, String femaleNomAff, NomRessource maleRessource, String maleNomAff) {
        AnimalProduct product = new AnimalProduct();
        product.femaleRessource = femaleRessource;
        product.femaleNomAff = femaleNomAff;
        product.maleRessource = maleRessource;
        product.femaleNomAff = maleNomAff;
        return product;
    }

    public String getNomAff(boolean isMale){
        return isMale ? maleNomAff : femaleNomAff;
    }

    public NomRessource getNomRessource(boolean isMale){
        return isMale ? maleRessource : femaleRessource;
    }
}