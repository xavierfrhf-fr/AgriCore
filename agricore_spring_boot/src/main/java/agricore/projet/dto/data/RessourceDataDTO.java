package agricore.projet.dto.data;

import agricore.projet.model.ressource.NomRessource;

public class RessourceDataDTO {
    private String nom;
    private String nomAffichage;
    private String pathSprite;
    private String nomAffichageUnite; // Autre chose de Unite ?
    private String nomAffichageZone; // Autre chose de Zone ?
    private String pathSpriteZone;

    public RessourceDataDTO() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNomAffichage() {
        return nomAffichage;
    }

    public void setNomAffichage(String nomAffichage) {
        this.nomAffichage = nomAffichage;
    }

    public String getPathSprite() {
        return pathSprite;
    }

    public void setPathSprite(String pathSprite) {
        this.pathSprite = pathSprite;
    }

    public String getNomAffichageUnite() {
        return nomAffichageUnite;
    }

    public void setNomAffichageUnite(String nomAffichageUnite) {
        this.nomAffichageUnite = nomAffichageUnite;
    }

    public String getNomAffichageZone() {
        return nomAffichageZone;
    }

    public void setNomAffichageZone(String nomAffichageZone) {
        this.nomAffichageZone = nomAffichageZone;
    }

    public String getPathSpriteZone() {
        return pathSpriteZone;
    }

    public void setPathSpriteZone(String pathSpriteZone) {
        this.pathSpriteZone = pathSpriteZone;
    }

    public static RessourceDataDTO convert(NomRessource nomRessource) {
        RessourceDataDTO dto = new RessourceDataDTO();
        dto.setNom(nomRessource.name());
        dto.setNomAffichage(nomRessource.getNomAffichage());
        dto.setPathSprite(nomRessource.getPathSprite());
        dto.setNomAffichageUnite(nomRessource.getUniteStockage().getAffichage());
        dto.setNomAffichageZone(nomRessource.getZoneStockage().getNomAffichage());
        dto.setPathSpriteZone(nomRessource.getZoneStockage().getPathSprite());
        return dto;
    }

}
