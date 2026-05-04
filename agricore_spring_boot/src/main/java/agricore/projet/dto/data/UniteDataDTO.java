package agricore.projet.dto.data;

import agricore.projet.model.ressource.Unite;

public class UniteDataDTO {
    private String nom;       // Valeur enum (ex: KILOGRAM) — envoyée dans le DTO de requête
    private String affichage; // Libellé lisible (ex: "Kg") — affiché dans le select

    public UniteDataDTO() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAffichage() {
        return affichage;
    }

    public void setAffichage(String affichage) {
        this.affichage = affichage;
    }

    public static UniteDataDTO convert(Unite unite) {
        UniteDataDTO dto = new UniteDataDTO();
        dto.setNom(unite.name());
        dto.setAffichage(unite.getAffichage());
        return dto;
    }
}
