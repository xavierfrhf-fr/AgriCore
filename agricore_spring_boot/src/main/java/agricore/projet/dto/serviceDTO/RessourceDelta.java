package agricore.projet.dto.serviceDTO;

import agricore.projet.model.ressource.NomRessource;

public record RessourceDelta(
        NomRessource nomRessource,
        int quantite
) {
    public boolean isProduction() {
        return quantite > 0;
    }

    public boolean isConsumption() {
        return quantite < 0;
    }

    public int absoluteQuantity() {
        return Math.abs(quantite);
    }
}
