package agricore.projet.services;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

import agricore.projet.contexts.DataContext;
import agricore.projet.dto.zone.response.*;
import agricore.projet.model.zone.NomZone;
import agricore.projet.model.zone.TypeZone;
import agricore.projet.model.zone.position.Position;
import agricore.projet.model.zone.position.Rotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import agricore.projet.dto.zone.request.PositionRequestDTO;
import agricore.projet.dto.zone.request.ZoneRequestDTO;
import agricore.projet.exception.InvalidZonePositionException;
import agricore.projet.exception.UniqueZoneAlreadyExistException;
import agricore.projet.exception.ZoneNotFoundException;
import agricore.projet.model.zone.Zone;
import agricore.projet.repository.IDAOUtilisateur;
import agricore.projet.repository.IDAOZone;

@Service
public class ZoneService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final IDAOZone daoZone;
    private final IDAOUtilisateur daoUtilisateur;
    private final PositionService positionService;
    private final DataContext dataContext;

    public ZoneService(IDAOZone daoZone, IDAOUtilisateur daoUtilisateur, PositionService positionService, DataContext dataContext) {
        this.daoZone = daoZone;
        this.daoUtilisateur = daoUtilisateur;
        this.positionService = positionService;
        this.dataContext = dataContext;
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
        /*
        List<ZoneResponseDTO> result = daoZone.findAll()
                .stream()
                .map(ZoneResponseDTO::convert)
                .toList();
        */
        List<ZoneResponseDTO> result = dataContext.getZones()
                        .stream()
                        .map(ZoneResponseDTO::convert)
                        .toList();
        logger.trace("find all zones ({} zones trouvees)", result.size());
        return result;
    }

    public int create(ZoneRequestDTO request) {
        if (request.getNomZone().isZoneUnique()) {
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

        zone = daoZone.save(zone);
        logger.trace("Creation de {} a {}", zone.toString(), zone.getPosition().toString());
        return zone.getId();
    }

    public int patch(ZoneRequestDTO request, int id) {
        Zone zone = daoZone.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Echec update partielle, car la zone avec id {} n'existe pas", id);
                    return new ZoneNotFoundException(id);
                });
        if (request.getNomZone() != null) {
            zone.setNomZone(request.getNomZone());
        }
        if (request.getPosition() != null) {
            zone.setPosition(PositionRequestDTO.convert(request.getPosition()));
            if (!positionService.zoneCanBeMoved(zone)) {
                throw new InvalidZonePositionException(
                        "Zone cannot be added due to invalid position (out of map or overlapping with other zones)");
            }

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

    public List<ZoneResponseDTO> getZoneByName(String name) {
                logger.trace("find by name des {}", name);

        List<ZoneResponseDTO> result = daoZone.findByName(NomZone.valueOf(name))
                .stream()
                .map(ZoneResponseDTO::convert)
                .toList();
        logger.trace("find zones by name {} ({} zones trouvees)", name, result.size());
        return result;
    }


    public List<ZoneResponseDTO> getAllZoneWithRelation(){
        List<ZoneResponseDTO> result = new ArrayList<>();

        result.addAll(this.getAllZoneWithAnimals());
//        System.out.println(this.getAllZoneWithAnimals());
        result.addAll(this.getAllZoneWithRessources());
//        System.out.println(this.getAllZoneWithRessources());
        result.addAll(this.getAllZoneWithPlants());
//        System.out.println(this.getAllZoneWithPlants());
        result.addAll(this.getAllZoneWithVehicules());
        System.out.println(this.getAllZoneWithVehicules());
//        for (ZoneResponseDTO dto : result) {
//            System.out.println(""+dto.getId()+dto.getTypeZone()+dto.getNomZone());
//        }

        return result;
    }

    public List<ZoneWithVehiculesResponseDTO> getAllZoneWithVehicules(){
        return daoZone
                .findByNomZonesWithVehicule(NomZone.getNomZonesByTypeZone(TypeZone.UTILITY))
                .stream()
                .map(ZoneWithVehiculesResponseDTO::convert)
                .toList();
    }

    public List<ZoneWithAnimalsResponseDTO> getAllZoneWithAnimals(){
        return daoZone
                .findByNomZonesWithAnimal(NomZone.getNomZonesByTypeZone(TypeZone.ANIMAL))
                .stream()
                .map(ZoneWithAnimalsResponseDTO::convert)
                .toList();
    }

    public List<ZoneWithRessourcesResponseDTO> getAllZoneWithRessources(){
        return daoZone
                .findByNomZonesWithRessource(NomZone.getNomZonesByTypeZone(TypeZone.STORAGE))
                .stream()
                .map(ZoneWithRessourcesResponseDTO::convert)
                .toList();
    }

    public List<ZoneWithPlantsResponseDTO> getAllZoneWithPlants(){
        return daoZone
                .findByNomZonesWithPlant(NomZone.getNomZonesByTypeZone(TypeZone.PLANTS))
                .stream()
                .map(ZoneWithPlantsResponseDTO::convert)
                .toList();
    }

    public boolean createWithRandomPos(NomZone nomZone) {
        ZoneRequestDTO request = new ZoneRequestDTO();
        request.setNomZone(nomZone);
        int numberOfTrials = 20;
        for (int i = 0; i < numberOfTrials; i++) {
            request.setPosition(this.generateRandomPosition());
            try{
                this.create(request);
                return true;
            }catch(InvalidZonePositionException e){
                System.out.println("Echec du placement de la zone avec des positions aléatoire (tentative n°"+i+"/"+numberOfTrials+")");
            }
        }
        return false;
    }

    public PositionRequestDTO generateRandomPosition() {
        return new PositionRequestDTO(
                RandomGenerator.getDefault().nextInt(0, Position.mapSize.x()),
                RandomGenerator.getDefault().nextInt(0, Position.mapSize.y()),
                Rotation.DEG_0
        );
    }
}