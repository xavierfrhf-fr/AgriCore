package agricore.projet.dto.ressource.request;

import agricore.projet.model.NomRessource;

public class RessourceRequestDTO {
    // pas d id pour la request
    private NomRessource nom;
    private Integer quantite;
    private Double prix;
    private Integer stockMin;

    public RessourceRequestDTO(NomRessource nom, Integer quantite, Double prix, Integer stockMin) {
        this.nom = nom;
        this.quantite = quantite;
        this.prix = prix;
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

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Integer getStockMin() {
        return stockMin;
    }

    public void setStockMin(Integer stockMin) {
        this.stockMin = stockMin;
    }

}
