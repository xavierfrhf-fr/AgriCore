package agricore.projet.dto.ressource.request;

import agricore.projet.model.NomRessource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

public class RessourceRequestDTO {
    // pas d id pour la request
    @NotNull
    private NomRessource nom;
    @NotNull
    private Integer quantite;
    @Valid
    @NotNull
    private PrixRequestDTO prixLot;
    @Min(value = 0, message = "Stock minimal ne peut pas être négatif")
    @NotNull
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
