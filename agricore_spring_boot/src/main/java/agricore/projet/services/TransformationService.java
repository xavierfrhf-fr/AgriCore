package agricore.projet.services;

import agricore.projet.dto.ressource.request.PrixRequestDTO;
import agricore.projet.dto.ressource.request.RessourceRequestDTO;
import agricore.projet.dto.ressource.request.TransformationRequestDTO;
import agricore.projet.model.ressource.NomRessource;
import agricore.projet.model.ressource.PrixLot;
import agricore.projet.model.ressource.Ressource;
import agricore.projet.model.ressource.Transformation;
import agricore.projet.repository.IDAORessource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class TransformationService {

    private final IDAORessource daoRessource;
    private final RessourceService ressourceService;
    public TransformationService(IDAORessource daoRessource, RessourceService ressourceService) {
        this.daoRessource = daoRessource;
        this.ressourceService = ressourceService;
    }

    private int getMaxTransformation(TransformationRequestDTO request) {
        Transformation transformation = Transformation.getTransformationByOutput(request.getProduct());

        int targetQuantity = request.getDesiredQuantity();
        int outputPerTransfo = transformation.getOutput().get(request.getProduct());
        int transfoNeeded = (int) Math.ceil((double) targetQuantity / outputPerTransfo);// arrondi supérieur -> si je veux 3 fromages et qu'un craft en donne 2 -> faire 2 crafts.

        // 4. Calculer combien de crafts sont techniquement POSSIBLES avec le stock
        int maxTransfoPossible = transformation.getInput()
                .entrySet()
                .stream()
                .mapToInt(entry -> {
                    int stockDispo = getStockAvailable(entry.getKey(), request.isBypassStockMin());
                    return stockDispo / entry.getValue();
                })
                .min()
                .orElse(0);

        return applyPartial(maxTransfoPossible, transfoNeeded, request.isPartial());
    }

    private int applyPartial(int maxPossible, int needed, boolean isPartial) {
        if (!isPartial && maxPossible < needed) {
            return 0; // Pas assez de ressource pour produire la quantité désiré (et transformation partielle désactivé)
        }
        return Math.min(maxPossible, needed);
    }

    private int getStockAvailable(NomRessource nomRessource, boolean bypassStockMin) {
        return daoRessource.findByNom(nomRessource)
                .map(r -> calculateAvailable(r, bypassStockMin))
                .orElse(0);
    }

    private int calculateAvailable(Ressource r, boolean bypass) {
        if (bypass) {
            return Math.max(0, r.getQuantite());
        }
        return Math.max(0, r.getQuantite() - r.getStockMin());
    }

    public void performTransformation(TransformationRequestDTO request) {
        int nbTransfo = getMaxTransformation(request);
        Transformation transformation = Transformation.getTransformationByOutput(request.getProduct());
        for (NomRessource nomRessource : transformation.getOutput().keySet()){
            createRessourceIfNotExist(nomRessource);
        }
        if (nbTransfo > 0){
            transformation.getInput().entrySet().forEach(entry -> {
                changeQuantity(entry.getKey(), -entry.getValue()*nbTransfo);
            });
            transformation.getOutput().entrySet().forEach(entry -> {
                changeQuantity(entry.getKey(), entry.getValue()*nbTransfo);
            });
        }

    }

    private void changeQuantity(NomRessource nomRessource, int quantity) {
        daoRessource
            .findByNom(nomRessource)
            .map (ressource ->  {
                ressource.setQuantite(ressource.getQuantite()+quantity);
                daoRessource.save(ressource);
                return null;
            });

    }

    private void createRessourceIfNotExist(NomRessource r) {
        if (!ressourceService.ressourceAlreadyExists(r)){
            RessourceRequestDTO  request = new RessourceRequestDTO();
            request.setNom(r);
            request.setStockMin(0);
            request.setQuantite(0);
            PrixRequestDTO prixLot = new PrixRequestDTO(BigDecimal.valueOf(1),1,r.getUniteStockage());
            request.setPrixLot(prixLot);
            ressourceService.create(request);
        }
    }
}
