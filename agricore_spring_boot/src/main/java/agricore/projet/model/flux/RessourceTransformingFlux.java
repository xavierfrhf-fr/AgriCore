package agricore.projet.model.flux;

import agricore.projet.dto.serviceDTO.RessourceDelta;
import agricore.projet.model.ressource.Transformation;

import java.util.List;

public interface RessourceTransformingFlux {

    Transformation getTransformation();

    default List<RessourceDelta> unitRessourceDeltas() {
        return getTransformation().toUnitDeltas();
    }

    default List<RessourceDelta> ressourceDeltas(int transformations) {
        return unitRessourceDeltas()
                .stream()
                .map(delta -> new RessourceDelta(
                        delta.nomRessource(),
                        delta.quantite() * transformations
                ))
                .toList();
    }
}
