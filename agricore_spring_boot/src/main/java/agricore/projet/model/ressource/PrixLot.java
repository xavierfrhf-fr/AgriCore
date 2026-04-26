package agricore.projet.model.ressource;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Embeddable
public class PrixLot {
    // Doit pouvoir modéliser, par exemple 100g à 2€
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal prixPar;// 2 dans l'exemple

    @Column(nullable = false, name = "quantite_lot")
    private int quantite;// 100 dans l'exemple

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Unite unite;// Unite.GRAMME dans l'exemple

    public PrixLot() {
    }

    public PrixLot(BigDecimal prixPar, int quantite, Unite unite) {
        this.prixPar = scale(prixPar);
        this.quantite = quantite;
        this.unite = unite;
    }

    private BigDecimal scale(BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("Le prix ne peut pas être null");
        }
        return value.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getPrixPar() {
        return prixPar;
    }

    public void setPrixPar(BigDecimal prixPar) {
        this.prixPar = scale(prixPar);
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Unite getUnite() {
        return unite;
    }

    public void setUnite(Unite unite) {
        this.unite = unite;
    }

    public BigDecimal calculerTotal(int nbLots) {
        return prixPar.multiply(BigDecimal.valueOf(nbLots))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getPrixUnitaire() {
        return prixPar.divide(BigDecimal.valueOf(quantite), 4, RoundingMode.HALF_UP);
    }

    public String getAffichage() {
        return prixPar.setScale(2, RoundingMode.HALF_UP) + " € / " + quantite + " " + unite.getAffichage();
    }

    public String getPrixByRef() {
        // Permet d'afficher prix au litre ou prix au kilo ou prix unitaire
        Unite ref = Unite.getReference(unite);

        double quantiteConvertie = unite.convertirVers(ref, quantite);

        // ex: 2€ / (100 g / 1 kg) = 20€/kg
        BigDecimal prixConverti = prixPar
                .divide(BigDecimal.valueOf(quantiteConvertie), 4, RoundingMode.HALF_UP)
                .setScale(2, RoundingMode.HALF_UP);

        return prixConverti + " € / " + ref.getAffichage();
    }

    @Override
    public String toString() {
        return getAffichage();
    }
}
