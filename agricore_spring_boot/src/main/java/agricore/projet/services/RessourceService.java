package agricore.projet.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import agricore.projet.dto.ressource.request.RessourceRequestDTO;
import agricore.projet.dto.ressource.response.RessourceResponseDTO;
import agricore.projet.exception.RessourceNotFoundException;
import agricore.projet.exception.ZoneNotFoundException;
import agricore.projet.model.Ressource;
import agricore.projet.repository.IDAORessource;
import agricore.projet.repository.IDAOZone;

@Service
public class RessourceService {
    private static final Logger logger = LoggerFactory.getLogger(RessourceService.class);

    private final IDAORessource daoRessource;
    private final IDAOZone daoZone;

    public RessourceService(IDAORessource daoRessource, IDAOZone daoZone) {
        this.daoRessource = daoRessource;
        this.daoZone = daoZone;
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
        Ressource ressource = new Ressource();
        ressource.setNom(request.getNom());
        ressource.setQuantite(request.getQuantite());
        ressource.setPrix(request.getPrix());
        ressource.setStockMin(request.getStockMin());
        ressource.setZone(
                daoZone.findById(request.getZoneId())
                        .or(() -> {
                            logger.warn("Zone introuvable pour l'id {}", request.getZoneId());
                            return Optional.empty();
                        })
                        .orElseThrow(() -> new ZoneNotFoundException(request.getZoneId())));

        RessourceResponseDTO ressourceResponse = RessourceResponseDTO.convert(daoRessource.save(ressource));
        logger.trace("Ressource cree par create() : {}", ressourceResponse);
        return ressourceResponse;
    }

    public RessourceResponseDTO patch(Integer id, RessourceRequestDTO request) {
        Ressource ressource = daoRessource.findById(id)
                .or(() -> {
                    logger.warn("Ressource introuvable pour l'id {}", id);
                    return Optional.empty();
                })
                .orElseThrow(() -> new RessourceNotFoundException(id));
        logger.trace("Ressource avant patch() : {}", ressource);
        if (request.getNom() != null)
            ressource.setNom(request.getNom());
        if (request.getQuantite() != null)
            ressource.setQuantite(request.getQuantite());
        if (request.getPrix() != null)
            ressource.setPrix(request.getPrix());
        if (request.getStockMin() != null)
            ressource.setStockMin(request.getStockMin());
        if (request.getZoneId() != null)
            ressource.setZone(
                    daoZone.findById(request.getZoneId())
                            .or(() -> {
                                logger.warn("Zone introuvable pour l'id {}", request.getZoneId());
                                return Optional.empty();
                            })
                            .orElseThrow(() -> new ZoneNotFoundException(request.getZoneId())));
        RessourceResponseDTO ressourceResponse = RessourceResponseDTO.convert(daoRessource.save(ressource));
        logger.trace("Ressource apres patch() : {}", ressourceResponse);
        return ressourceResponse;
    }

    public RessourceResponseDTO update(Integer id, RessourceRequestDTO request) {
        Ressource ressource = daoRessource.findById(id)
                .or(() -> {
                    logger.warn("Ressource introuvable pour l'id {}", id);
                    return Optional.empty();
                })
                .orElseThrow(() -> new RessourceNotFoundException(id));
        logger.trace("Ressource avant update() : {}", ressource);
        ressource.setNom(request.getNom());
        ressource.setQuantite(request.getQuantite());
        ressource.setPrix(request.getPrix());
        ressource.setStockMin(request.getStockMin());
        ressource.setZone(
                daoZone.findById(request.getZoneId())
                        .or(() -> {
                            logger.warn("Zone introuvable pour l'id {}", request.getZoneId());
                            return Optional.empty();
                        })
                        .orElseThrow(() -> new ZoneNotFoundException(request.getZoneId())));
        RessourceResponseDTO ressourceResponse = RessourceResponseDTO.convert(daoRessource.save(ressource));
        logger.trace("Ressource apres update() : {}", ressourceResponse);
        return ressourceResponse;
    }

    public void deleteById(Integer id) {
        daoRessource.findById(id)
                .or(() -> {
                    logger.warn("Ressource introuvable pour deleteById() id {}", id);
                    return Optional.empty();
                })
                .orElseThrow(() -> new RessourceNotFoundException(id));
        logger.trace("Suppression de la ressource id {}", id);
        daoRessource.deleteById(id);
    }

}
