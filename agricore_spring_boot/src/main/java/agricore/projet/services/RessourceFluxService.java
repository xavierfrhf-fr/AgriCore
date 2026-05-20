package agricore.projet.services;

import agricore.projet.contexts.DataContext;
import agricore.projet.contexts.UpdateContext;
import agricore.projet.dto.serviceDTO.RessourceDelta;
import agricore.projet.dto.serviceDTO.RessourceOperationResult;
import agricore.projet.model.ressource.NomRessource;
import agricore.projet.model.ressource.Ressource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RessourceFluxService {

    private final RessourceService ressourceService;

    public RessourceFluxService(RessourceService ressourceService) {
        this.ressourceService = ressourceService;
    }

    public RessourceOperationResult addResource(
            NomRessource nomRessource,
            int quantity,
            UpdateContext updateContext,
            DataContext dataContext
    ) {
        try {
            Ressource ressource = ressourceService.getOrCreate(nomRessource, dataContext);

            ressource.setQuantite(ressource.getQuantite() + quantity);

            return RessourceOperationResult.valid(ressource);
        } catch (RuntimeException e) {
            dataContext.addResourceCreationFailed(nomRessource);

            return RessourceOperationResult.failure(e.getMessage());
        }
    }

    public RessourceOperationResult consumeResource(
            NomRessource nomRessource,
            int quantity,
            DataContext dataContext
    ) {
        Optional<Ressource> optional = dataContext.getRessourceByNom(nomRessource);

        if (optional.isEmpty()) {
            return RessourceOperationResult.failure("Ressource absente : " + nomRessource);
        }

        Ressource ressource = optional.get();

        if (ressource.getQuantite() < quantity) {
            return RessourceOperationResult.failure("Quantité insuffisante : " + nomRessource);
        }

        ressource.setQuantite(ressource.getQuantite() - quantity);

        return RessourceOperationResult.valid(ressource);
    }

    public RessourceOperationResult applyAtomicDeltas(
            List<RessourceDelta> deltas,
            DataContext dataContext
    ) {
        RessourceOperationResult validation = validateDeltas(deltas, dataContext);

        if (!validation.success()) {
            return validation;
        }

        return applyDeltas(deltas, dataContext);
    }

    private RessourceOperationResult validateDeltas(
            List<RessourceDelta> deltas,
            DataContext dataContext
    ) {
        Map<NomRessource, Integer> totalByRessource = mergeDeltas(deltas);

        for (Map.Entry<NomRessource, Integer> entry : totalByRessource.entrySet()) {
            NomRessource nomRessource = entry.getKey();
            int delta = entry.getValue();

            if (delta < 0) {
                RessourceOperationResult check =
                        canConsume(nomRessource, Math.abs(delta), dataContext);

                if (!check.success()) {
                    return check;
                }
            }

            if (delta > 0) {
                RessourceOperationResult check =
                        canProduce(nomRessource, dataContext);

                if (!check.success()) {
                    return check;
                }
            }
        }

        return RessourceOperationResult.valid();
    }

    private RessourceOperationResult applyDeltas(
            List<RessourceDelta> deltas,
            DataContext dataContext
    ) {
        Map<NomRessource, Integer> totalByRessource = mergeDeltas(deltas);

        for (Map.Entry<NomRessource, Integer> entry : totalByRessource.entrySet()) {
            NomRessource nomRessource = entry.getKey();
            int delta = entry.getValue();

            Ressource ressource = ressourceService
                    .getOrCreate(nomRessource, dataContext);

            ressource.setQuantite(ressource.getQuantite() + delta);
        }

        return RessourceOperationResult.valid();
    }

    private RessourceOperationResult canConsume(
            NomRessource nomRessource,
            int quantity,
            DataContext dataContext
    ) {
        Optional<Ressource> optional = dataContext.getRessourceByNom(nomRessource);

        if (optional.isEmpty()) {
            return RessourceOperationResult.failure(
                    "Ressource absente : " + nomRessource
            );
        }

        Ressource ressource = optional.get();

        if (ressource.getQuantite() < quantity) {
            return RessourceOperationResult.failure(
                    "Stock insuffisant : " + nomRessource
            );
        }

        return RessourceOperationResult.valid();
    }

    private RessourceOperationResult canProduce(
            NomRessource nomRessource,
            DataContext dataContext
    ) {
        RessourceOperationResult result =
                ressourceService.getOrCreateSafe(nomRessource, dataContext);

        if (!result.success()) {
            return result;
        }

        return RessourceOperationResult.valid();
    }

    private Map<NomRessource, Integer> mergeDeltas(List<RessourceDelta> deltas) {
        Map<NomRessource, Integer> result = new HashMap<>();

        for (RessourceDelta delta : deltas) {
            result.merge(
                    delta.nomRessource(),
                    delta.quantite(),
                    Integer::sum
            );
        }

        return result;
    }

    public int computeMaxTransformations(
            List<RessourceDelta> unitDeltas,
            DataContext dataContext
    ) {
        int max = Integer.MAX_VALUE;

        for (RessourceDelta delta : unitDeltas) {
            if (!delta.isConsumption()) {
                continue;
            }

            Optional<Ressource> optional = dataContext.getRessourceByNom(delta.nomRessource());

            if (optional.isEmpty()) {
                return 0;
            }

            int available = optional.get().getQuantite();
            int required = delta.absoluteQuantity();

            if (required <= 0) {
                continue;
            }

            max = Math.min(max, available / required);
        }

        if (max == Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }

        return max;
    }
}