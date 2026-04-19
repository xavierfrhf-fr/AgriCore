package agricore.projet.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import agricore.projet.dto.ressource.request.RessourceRequestDTO;
import agricore.projet.dto.ressource.response.RessourceResponseDTO;
import agricore.projet.exception.RessourceNotFoundException;
import agricore.projet.model.NomRessource;
import agricore.projet.model.Ressource;
import agricore.projet.repository.IDAORessource;
import agricore.projet.repository.IDAOZone;

@Service
public class RessourceService {
    private static final Logger logger = LoggerFactory.getLogger(RessourceService.class);

    private final IDAORessource daoRessource;
    private final ZoneService zoneService;

    public RessourceService(IDAORessource daoRessource, IDAOZone daoZone, ZoneService zoneService) {
        this.daoRessource = daoRessource;
        this.zoneService = zoneService;
    }

    public List<RessourceResponseDTO> getAll() {
        List<RessourceResponseDTO> ressources = daoRessource.findAll()
                .stream()
                .map(RessourceResponseDTO::convert)
                .toList();

        logger.trace("{} ressources recuperees par le findAll()", ressources.size());
        return ressources;
    }

    public RessourceResponseDTO getById(Integer id) {
        return daoRessource.findById(id)
                .map(RessourceResponseDTO::convert)
                .map(r -> {
                    logger.trace("Ressource recuperee par findById() id {}: {}", id, r);
                    return r;
                })
                .or(() -> {
                    logger.warn("Aucune ressource trouvee par findById() id {}", id);
                    return Optional.empty();
                })
                .orElseThrow(() -> new RessourceNotFoundException(id));
    }

    public RessourceResponseDTO create(RessourceRequestDTO request) {
        if (ressourceAlreadyExists(request)) {
            throw new RuntimeException("Ressource existe déjà"); // Exception custom ou alors transformer en patch pour
                                                                 // modifier quantité ?
        }

        Ressource ressource = new Ressource();
        ressource.setZone(zoneService.findZoneThatStoreRessources(request.getNom())
                .orElseThrow(
                        () -> new RuntimeException("Aucune zone ne permet de stocker : " + request.getNom().name())));
        ressource.setNom(request.getNom());
        ressource.setQuantite(request.getQuantite());
        ressource.setPrix(request.getPrix());
        ressource.setStockMin(request.getStockMin());
        RessourceResponseDTO ressourceResponse = RessourceResponseDTO.convert(daoRessource.save(ressource));
        logger.trace("Ressource cree par create() : {}", ressourceResponse);
        return ressourceResponse;
    }

    public RessourceResponseDTO patch(Integer id, RessourceRequestDTO request) {
        Ressource ressource = findByIdOrThrow(id);
        logger.trace("Ressource avant patch() : {}", ressource);
        if (request.getNom() != null || request.getNom() != ressource.getNom()) {
            logger.error("Impossible de changer le type d'une ressource déjà instancié !");
        }
        if (request.getQuantite() != null)
            ressource.setQuantite(request.getQuantite());
        if (request.getPrix() != null)
            ressource.setPrix(request.getPrix());
        if (request.getStockMin() != null)
            ressource.setStockMin(request.getStockMin());

        RessourceResponseDTO ressourceResponse = RessourceResponseDTO.convert(daoRessource.save(ressource));
        logger.trace("Ressource apres patch() : {}", ressourceResponse);
        return ressourceResponse;
    }

    public RessourceResponseDTO update(Integer id, RessourceRequestDTO request) {
        Ressource ressource = findByIdOrThrow(id);
        logger.trace("Ressource avant update() : {}", ressource);
        if (request.getNom() != ressource.getNom()) {
            logger.error("Impossible de changer le type d'une ressource déjà instancié ! (modification ignorée)");
        }
        ressource.setQuantite(request.getQuantite());
        ressource.setPrix(request.getPrix());
        ressource.setStockMin(request.getStockMin());
        RessourceResponseDTO ressourceResponse = RessourceResponseDTO.convert(daoRessource.save(ressource));
        logger.trace("Ressource apres update() : {}", ressourceResponse);
        return ressourceResponse;
    }

    public void deleteById(Integer id) {
        findByIdOrThrow(id);
        logger.trace("Suppression de la ressource id {}", id);
        daoRessource.deleteById(id);
    }

    public boolean ressourceAlreadyExists(NomRessource nomRessource) {
        // Indique si une ressource existe déjà en BDD
        // Attention ne prends pas en compte la possiblité d'avoir plusieurs fermier
        // (gérant plusieurs fermes dans la même BDD)
        return daoRessource.findAll()
                .stream()
                .anyMatch(ressource -> ressource.getNom().equals(nomRessource));
    }

    public boolean ressourceAlreadyExists(RessourceRequestDTO request) {
        return ressourceAlreadyExists(request.getNom());
    }

    private Ressource findByIdOrThrow(Integer id) {
        return daoRessource.findById(id)
                .or(() -> {
                    logger.warn("Ressource introuvable pour l'id {}", id);
                    return Optional.empty();
                })
                .orElseThrow(() -> new RessourceNotFoundException(id));
    }

}
