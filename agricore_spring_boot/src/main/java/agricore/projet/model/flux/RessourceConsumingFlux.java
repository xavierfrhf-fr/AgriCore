package agricore.projet.model.flux;

import agricore.projet.contexts.UpdateContext;
import agricore.projet.dto.serviceDTO.RessourceDelta;
import agricore.projet.model.ressource.NomRessource;

import java.util.List;

public interface RessourceConsumingFlux {
    NomRessource consumedRessource();

    int consumedQuantity(UpdateContext updateContext);

    default List<RessourceDelta> consumptionDeltas(UpdateContext updateContext) {
        return List.of(
                new RessourceDelta(consumedRessource(), -consumedQuantity(updateContext))
        );
    }
}
