package agricore.projet.dto.ressource.request;

import agricore.projet.model.Unite;

import java.math.BigDecimal;

public class PrixRequestDTO {
    private Double prixPar;
    private Integer quantiteLot;
    private Unite unite;

    public PrixRequestDTO() {
    }

    public PrixRequestDTO(Double prixPar, Integer quantiteLot, Unite unite) {
        this.prixPar = prixPar;
        this.quantiteLot = quantiteLot;
        this.unite = unite;
    }

    public Double getPrixPar() {
        return prixPar;
    }

    public void setPrixPar(Double prix) {
        this.prixPar = prix;
    }

    public Integer getQuantiteLot() {
        return quantiteLot;
    }

    public void setQuantiteLot(Integer quantiteLot) {
        this.quantiteLot = quantiteLot;
    }

    public Unite getUnite() {
        return unite;
    }

    public void setUnite(Unite unite) {
        this.unite = unite;
    }

}
