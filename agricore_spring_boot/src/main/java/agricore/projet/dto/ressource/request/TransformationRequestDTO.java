package agricore.projet.dto.ressource.request;

import agricore.projet.model.ressource.NomRessource;

public class TransformationRequestDTO {
    NomRessource product;
    int desiredQuantity;
    boolean partial = false; //Si pas assez de ressources dispos ne rien produire
    boolean bypassStockMin = false; //Est-ce que la partie reservé (stockMin) peut être utilisé

    public TransformationRequestDTO() {
    }
    public TransformationRequestDTO(NomRessource product, int desiredQuantity) {
        this.product = product;
        this.desiredQuantity = desiredQuantity;
    }

    public NomRessource getProduct() {
        return product;
    }

    public void setProduct(NomRessource product) {
        this.product = product;
    }

    public int getDesiredQuantity() {
        return desiredQuantity;
    }

    public void setDesiredQuantity(int desiredQuantity) {
        this.desiredQuantity = desiredQuantity;
    }

    public boolean isPartial() {
        return partial;
    }

    public void setPartial(boolean partial) {
        this.partial = partial;
    }

    public boolean isBypassStockMin() {
        return bypassStockMin;
    }

    public void setBypassStockMin(boolean bypassStockMin) {
        this.bypassStockMin = bypassStockMin;
    }
}
