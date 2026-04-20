package agricore.projet.dto.ressource.request;

import agricore.projet.model.NomRessource;

public class RessourceRequestDTO {
    // pas d id pour la request
    private NomRessource nom;
    private Integer quantite;
    private PrixRequestDTO prixLot;
    private Integer stockMin;

    public RessourceRequestDTO(NomRessource nom, Integer quantite, PrixRequestDTO prixLot, Integer stockMin) {
        this.nom = nom;
        this.quantite = quantite;
        this.prixLot = prixLot;
        this.stockMin = stockMin;
    }

    public RessourceRequestDTO() {}

    public NomRessource getNom() {
        return nom;
    }

    public void setNom(NomRessource nom) {
        this.nom = nom;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public PrixRequestDTO getPrixLot() {
        return prixLot;
    }

    public void setPrixLot(PrixRequestDTO prixLot) {
        this.prixLot = prixLot;
    }

    public Integer getStockMin() {
        return stockMin;
    }

    public void setStockMin(Integer stockMin) {
        this.stockMin = stockMin;
    }

}
