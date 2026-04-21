package agricore.projet.dto.ressource.response;

import agricore.projet.model.NomRessource;
import agricore.projet.model.NomZone;
import agricore.projet.model.Ressource;

public class RessourceResponseDTO {
    private Integer id;
    private NomRessource nom;
    private String uniteAffichage;
    private int quantite;
    private PrixResponseDTO prixLot;
    private int stockMin;
    private Integer zoneId;
    private NomZone zoneNom; // Pour eviter une seconde requete pour le nom que l on voudra afficher

    public RessourceResponseDTO(Integer id, NomRessource nom, String uniteAffichage, int quantite,
            PrixResponseDTO prixLot, int stockMin, Integer zoneId,
            NomZone zoneNom) {
        this.id = id;
        this.nom = nom;
        this.uniteAffichage = uniteAffichage;
        this.quantite = quantite;
        this.prixLot = prixLot;
        this.stockMin = stockMin;
        this.zoneId = zoneId;
        this.zoneNom = zoneNom;
    }

    public RessourceResponseDTO() {
    }

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

    public String getuniteAffichage() {
        return uniteAffichage;
    }

    public void setuniteAffichage(String uniteAffichage) {
        this.uniteAffichage = uniteAffichage;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public PrixResponseDTO getPrixLot() {
        return prixLot;
    }

    public void setPrixLot(PrixResponseDTO prixLot) {
        this.prixLot = prixLot;
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

    public NomZone getZoneNom() {
        return zoneNom;
    }

    public void setZoneNom(NomZone zoneNom) {
        this.zoneNom = zoneNom;
    }

    @Override
    public String toString() {
        return "RessourceResponseDTO [id=" + id + ", nom=" + nom + ", uniteAffichage=" + uniteAffichage
                + ", quantite="
                + quantite + ", prixLot=" + prixLot + ", stockMin=" + stockMin + ", zoneId=" + zoneId + ", zoneNom="
                + zoneNom + "]";
    }

    public static RessourceResponseDTO convert(Ressource r) {
        RessourceResponseDTO response = new RessourceResponseDTO();
        response.setId(r.getId());
        response.setNom(r.getNom());
        response.setuniteAffichage(r.getNom().getUniteStockage().getAffichage());
        response.setQuantite(r.getQuantite());
        response.setPrixLot(PrixResponseDTO.convert(r.getPrixLot()));
        response.setStockMin(r.getStockMin());
        response.setZoneId(r.getZone().getId());
        response.setZoneNom(r.getZone().getNomZone());
        return response;
    }

}
