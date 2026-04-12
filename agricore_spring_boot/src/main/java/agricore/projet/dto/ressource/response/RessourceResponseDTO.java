package agricore.projet.dto.ressource.response;

import org.springframework.beans.BeanUtils;

import agricore.projet.model.NomRessource;
import agricore.projet.model.Ressource;

public class RessourceResponseDTO {
    private Integer id;
    private NomRessource nom;
    private int quantite;
    private double prix;
    private int stockMin;
    private Integer zoneId;
    private String zoneNom; // Pour eviter une seconde requete pour le nom que l on voudra afficher

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getZoneNom() {
        return zoneNom;
    }

    public void setZoneNom(String zoneNom) {
        this.zoneNom = zoneNom;
    }

    public static RessourceResponseDTO convert(Ressource r) {
        RessourceResponseDTO response = new RessourceResponseDTO();
        response.setId(r.getId());
        response.setNom(r.getNom());
        response.setQuantite(r.getQuantite());
        response.setPrix(r.getPrix());
        response.setStockMin(r.getStockMin());
        response.setZoneId(r.getZone().getId());
        response.setZoneNom(r.getZone().getNomZone().toString());
        return response;
    }
}
