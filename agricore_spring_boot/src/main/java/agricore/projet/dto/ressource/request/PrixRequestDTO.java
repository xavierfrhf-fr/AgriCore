package agricore.projet.dto.ressource.request;

import agricore.projet.model.Unite;

import java.math.BigDecimal;

public class PrixRequestDTO {
    private BigDecimal prixPar;
    private Integer quantiteLot;
    private Unite unite;

    public PrixRequestDTO() {
    }

    public PrixRequestDTO(BigDecimal prixPar, Integer quantiteLot, Unite unite) {
        this.prixPar = prixPar;
        this.quantiteLot = quantiteLot;
        this.unite = unite;
    }

    public BigDecimal getPrixPar() {
        return prixPar;
    }

    public void setPrixPar(BigDecimal prix) {
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
