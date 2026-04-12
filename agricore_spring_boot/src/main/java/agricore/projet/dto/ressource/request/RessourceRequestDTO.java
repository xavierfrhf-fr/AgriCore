package agricore.projet.dto.ressource.request;

import agricore.projet.model.NomRessource;

public class RessourceRequestDTO {
    // pas d id pour la request
    private NomRessource nom;
    private int quantite;
    private double prix;
    private int stockMin;
    private Integer zoneId; // juste l id pas Zone entier ou nomZone

    public NomRessource getNom() {
        return nom;
    }

    public void setNom(NomRessource nom) {
        this.nom = nom;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getStockMin() {
        return stockMin;
    }

    public void setStockMin(int stockMin) {
        this.stockMin = stockMin;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

}
