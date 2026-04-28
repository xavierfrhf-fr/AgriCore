package agricore.projet.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import agricore.projet.dto.zone.request.PositionRequestDTO;
import agricore.projet.dto.zone.request.ZoneRequestDTO;
import agricore.projet.dto.zone.response.ZoneResponseDTO;
import agricore.projet.dto.zone.response.ZoneWithAnimalsResponseDTO;
import agricore.projet.dto.zone.response.ZoneWithRessourcesResponseDTO;
import agricore.projet.dto.zone.response.ZoneWithVehiculesResponseDTO;
import agricore.projet.exception.InvalidZonePositionException;
import agricore.projet.exception.UniqueZoneAlreadyExistException;
import agricore.projet.exception.ZoneNotFoundException;
import agricore.projet.model.Fermier;
import agricore.projet.model.ressource.NomRessource;
import agricore.projet.model.zone.Zone;
import agricore.projet.repository.IDAOUtilisateur;
import agricore.projet.repository.IDAOZone;

@Service
public class ZoneService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final IDAOZone daoZone;
    private final IDAOUtilisateur daoUtilisateur;
    private final PositionService positionService;

    public ZoneService(IDAOZone daoZone, IDAOUtilisateur daoUtilisateur, PositionService positionService) {
        this.daoZone = daoZone;
        this.daoUtilisateur = daoUtilisateur;
        this.positionService = positionService;
    }

    public ZoneResponseDTO getZoneById(Integer id) {
        logger.trace("find by id de la zone {}", id);
        return ZoneResponseDTO.convert(daoZone.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Zone avec id {} n'existe pas", id);
                    return new ZoneNotFoundException(id);
                }));
    }

    public List<ZoneResponseDTO> getAllZone() {
        List<ZoneResponseDTO> result = daoZone.findAll()
                .stream()
                .map(ZoneResponseDTO::convert)
                .toList();
        logger.trace("find all zones ({} zones trouvees)", result.size());
        return result;
    }

    public int create(ZoneRequestDTO request) {
        if (NomRessource.isZoneUnique(request.getNomZone())) {
            if (daoZone.existsByNomZone(request.getNomZone())) {
                throw new UniqueZoneAlreadyExistException(
                        "A zone of type: " + request.getNomZone() + " already exists");
            }
        }
        Zone zone = ZoneRequestDTO.convert(request);
        if (!positionService.zoneCanBeAdded(zone)) {
            throw new InvalidZonePositionException(
                    "Zone cannot be added due to invalid position (out of map or overlapping with other zones)");
        }
        // TODO a revoir (cast en fermier, exception custom)
        zone.setFermier((Fermier) daoUtilisateur
                .findById(request.getFermierId())
                .orElseThrow(NullPointerException::new));// TODO utiliser exception custom

        zone = daoZone.save(zone);
        logger.trace("Creation de {} a {}", zone.toString(), zone.getPosition().toString());
        return zone.getId();
    }

    public int patch(ZoneRequestDTO request, int id) {// VERIFIER si update partielle marche comme ca ?? De plus les
                                                      // relations ManyToOne / OneToOne (en lazy seront elle ecrasé ??)
        Zone zone = daoZone.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Echec update partielle, car la zone avec id {} n'existe pas", id);
                    return new ZoneNotFoundException(id);
                });
        if (request.getNomZone() != null) {
            zone.setNomZone(request.getNomZone());
        }
        if (request.getPosition() != null) {
            if (!positionService.zoneCanBeMoved(zone)) {
                throw new InvalidZonePositionException(
                        "Zone cannot be added due to invalid position (out of map or overlapping with other zones)");
            }
            zone.setPosition(PositionRequestDTO.convert(request.getPosition()));
        }
        if (request.getFermierId() != null) {
            if (request.getFermierId() != zone.getFermier().getId()) {
                logger.warn(
                        "Changement de fermier dans la zone avec l'id {} cela peut amener a des comportements incohérents",
                        id);
            }
            zone.setFermier((Fermier) daoUtilisateur
                    .findById(request.getFermierId())
                    .orElseThrow(NullPointerException::new));// TODO utiliser exception custom
        }
        logger.trace("Update partiel de zone ({}, {})", request.toString(), id);
        return daoZone.save(zone).getId();
    }

    public int put(ZoneRequestDTO request, int id) {
        logger.trace("Update complet de zone ({}, {})", request.toString(), id);
        Zone zone = ZoneRequestDTO.convert(request);
        zone.setId(id);
        if (!positionService.zoneCanBeMoved(zone)) {
            throw new InvalidZonePositionException(
                    "Zone cannot be added due to invalid position (out of map or overlapping with other zones)");
        }
        // TODO a revoir (cast en fermier, exception custom)
        zone.setFermier((Fermier) daoUtilisateur
                .findById(request.getFermierId())
                .orElseThrow(NullPointerException::new));

        return daoZone.save(zone).getId();
    }

    public void delete(int id) {
        logger.warn("Suppression de la zone avec l'Id({})", id);
        daoZone.deleteById(id);// TODO Ca passera jamais, a cause de toutes les relations
    }

    public ZoneWithRessourcesResponseDTO getZoneWithRessources(int id) {
        logger.trace("find by id de la zone avec ces ressources {}", id);
        return ZoneWithRessourcesResponseDTO.convert(daoZone.findByIdWithRessource(id)
                .orElseThrow(() -> {
                    logger.warn("Zone avec id {} n'existe pas", id);
                    return new ZoneNotFoundException(id);
                }));
    }

    public ZoneWithVehiculesResponseDTO getZoneWithVehicules(int id) {
        logger.trace("find by id de la zone avec ces vehicules {}", id);
        return ZoneWithVehiculesResponseDTO.convert(daoZone.findByIdWithVehicule(id)
                .orElseThrow(() -> {
                    logger.warn("Zone avec id {} n'existe pas", id);
                    return new ZoneNotFoundException(id);
                }));
    }

    public ZoneWithAnimalsResponseDTO getZoneWithAnimals(Integer id) {
        logger.trace("find by id de la zone avec ces animaux {}", id);
        return ZoneWithAnimalsResponseDTO.convert(daoZone.findByIdWithAnimal(id)
                .orElseThrow(() -> {
                    logger.warn("Zone avec id {} n'existe pas", id);
                    return new ZoneNotFoundException(id);
                }));
    }
}