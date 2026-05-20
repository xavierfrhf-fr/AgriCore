package agricore.projet.model.flux;

import agricore.projet.contexts.UpdateContext;
import agricore.projet.dto.serviceDTO.RessourceDelta;
import agricore.projet.model.ressource.NomRessource;

import java.util.List;

public interface RessourceProducingFlux {
    NomRessource producedRessource();

    int producedQuantity(UpdateContext updateContext);

    default List<RessourceDelta> productionDeltas(UpdateContext updateContext) {
        return List.of(
                new RessourceDelta(producedRessource(), producedQuantity(updateContext))
        );
    }
}
