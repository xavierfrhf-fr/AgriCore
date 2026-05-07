package agricore.projet.dto.data;

import agricore.projet.model.animal.EspeceAnimal;

public class AnimalDataDTO {
    private String espece;
    private String nomAffichageMale;
    private String nomAffichageFemelle;

    public AnimalDataDTO() {
    }

    public static AnimalDataDTO convert(EspeceAnimal especeAnimal) {
        AnimalDataDTO animalDataDTO = new AnimalDataDTO();
        animalDataDTO.setEspece(especeAnimal.name());
        animalDataDTO.setNomAffichageMale(especeAnimal.getDimorphisme().getNomAffichage(true));
        animalDataDTO.setNomAffichageFemelle(especeAnimal.getDimorphisme().getNomAffichage(false));
        return animalDataDTO;
    }

    public String getEspece() {
        return espece;
    }

    public void setEspece(String espece) {
        this.espece = espece;
    }

    public String getNomAffichageMale() {
        return nomAffichageMale;
    }

    public void setNomAffichageMale(String nomAffichageMale) {
        this.nomAffichageMale = nomAffichageMale;
    }

    public String getNomAffichageFemelle() {
        return nomAffichageFemelle;
    }

    public void setNomAffichageFemelle(String nomAffichageFemelle) {
        this.nomAffichageFemelle = nomAffichageFemelle;
    }

    

}
