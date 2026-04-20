package agricore.projet.dto.ressource.response;

import agricore.projet.model.PrixLot;
import agricore.projet.model.Unite;

public class PrixResponseDTO {
    double prixPar;
    Unite unite;
    int quantiteLot;
    String affPrix;
    String affPrixUnitaire;

    public PrixResponseDTO() {
    }

    public PrixResponseDTO(double prixPar, Unite unite, int quantiteLot, String affPrix, String affPrixUnitaire) {
        this.prixPar = prixPar;
        this.unite = unite;
        this.quantiteLot = quantiteLot;
        this.affPrix = affPrix;
        this.affPrixUnitaire = affPrixUnitaire;
    }

    public double getPrixPar() {
        return prixPar;
    }

    public void setPrixPar(double prixPar) {
        this.prixPar = prixPar;
    }

    public Unite getUnite() {
        return unite;
    }

    public void setUnite(Unite unite) {
        this.unite = unite;
    }

    public int getQuantiteLot() {
        return quantiteLot;
    }

    public void setQuantiteLot(int quantiteLot) {
        this.quantiteLot = quantiteLot;
    }

    public String getAffPrix() {
        return affPrix;
    }

    public void setAffPrix(String affPrix) {
        this.affPrix = affPrix;
    }

    public String getAffPrixUnitaire() {
        return affPrixUnitaire;
    }

    public void setAffPrixUnitaire(String affPrixUnitaire) {
        this.affPrixUnitaire = affPrixUnitaire;
    }

    public static PrixResponseDTO convert(PrixLot prixLot){
        PrixResponseDTO response = new PrixResponseDTO();
        response.setPrixPar(prixLot.getPrixPar().doubleValue());
        response.setUnite(prixLot.getUnite());
        response.setQuantiteLot(prixLot.getQuantite());
        response.setAffPrix(prixLot.getAffichage());
        response.setAffPrixUnitaire(prixLot.getPrixByRef());
        return response;
    }
}
