package agricore.projet.services;

import agricore.projet.dto.ressource.request.PrixRequestDTO;
import agricore.projet.dto.ressource.request.RessourceRequestDTO;
import agricore.projet.dto.ressource.request.TransformationRequestDTO;
import agricore.projet.dto.ressource.response.TransformationResponseDTO;
import agricore.projet.exception.RessourceNotFoundException;
import agricore.projet.exception.ZoneNotFoundException;
import agricore.projet.model.ressource.NomRessource;
import agricore.projet.model.ressource.PrixLot;
import agricore.projet.model.ressource.Ressource;
import agricore.projet.model.ressource.Transformation;
import agricore.projet.repository.IDAORessource;
import agricore.projet.repository.IDAOZone;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class TransformationService {

    private final IDAORessource daoRessource;
    private final RessourceService ressourceService;
    private final IDAOZone daoZone;
    public TransformationService(IDAORessource daoRessource, RessourceService ressourceService, IDAOZone daoZone) {
        this.daoRessource = daoRessource;
        this.ressourceService = ressourceService;
        this.daoZone = daoZone;
    }

    public TransformationResponseDTO performTransformation(TransformationRequestDTO request) {
        //Méthode principale pour effectuer transformation
        Transformation transformation = Transformation.getTransformationByOutput(request.getProduct());
        if (!daoZone.existsByNomZone(transformation.getRequiredZone())) {//Check required Zone
            throw new ZoneNotFoundException(transformation.getRequiredZone());
        }

        int nbTransfo = getMaxTransformation(request);
        createMissingOutputResources(transformation);//A ameliorer avec try catch, et message d'erreur dans TransfoResponseDTO
        if (nbTransfo > 0){
            transformation.getInput().entrySet().forEach(entry -> {
                changeQuantity(entry.getKey(), -entry.getValue()*nbTransfo);
            });
            transformation.getOutput().entrySet().forEach(entry -> {
                changeQuantity(entry.getKey(), entry.getValue()*nbTransfo);
            });
        }
        return TransformationResponseDTO.generate(request,transformation,nbTransfo);
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
                    int stockDispo = getAvailableStock(entry.getKey(), request.isBypassStockMin());
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

    private int getAvailableStock(NomRessource nomRessource,
                                  boolean bypassStockMin) {
        Ressource ressource = daoRessource.findByNom(nomRessource).orElseThrow(
                () -> new RessourceNotFoundException(nomRessource)
        );
        int reserved = bypassStockMin ? 0 : ressource.getStockMin();
        return Math.max(0, ressource.getQuantite() - reserved);
    }

    private void changeQuantity(NomRessource nomRessource, int varQte) {
        //ATTENTION ne vérifie pas :
        // - Si la ressource existe en BDD
        // - Si la quantité est suffisante en cas de valeurs négative -> utilisation sans vérification préalable peut amener à des quantités négatives
        daoRessource
            .findByNom(nomRessource)
            .map (ressource ->  {
                ressource.setQuantite(ressource.getQuantite()+varQte);
                daoRessource.save(ressource);
                return null;
            });
    }

    private void createMissingOutputResources(Transformation transformation) {
        transformation.getOutput()
                .keySet()
                .forEach(this::createRessourceIfNotExist);
    }

    private void createRessourceIfNotExist(NomRessource r) {
        if (ressourceService.ressourceAlreadyExists(r)) {
            return;
        }
        RessourceRequestDTO  request = new RessourceRequestDTO();
        request.setNom(r);
        request.setStockMin(0);
        request.setQuantite(0);
        request.setPrixLot(
                new PrixRequestDTO(
                        BigDecimal.ONE,
                        1,
                        r.getUniteStockage()
                )
        );
        ressourceService.create(request);//permet de garder les verifs associé aux ressources
    }
}
