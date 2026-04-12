package agricore.projet.services;


import agricore.projet.dto.zone.request.PositionRequestDTO;
import agricore.projet.dto.zone.request.ZoneRequestDTO;
import agricore.projet.dto.zone.response.ZoneResponseDTO;
import agricore.projet.dto.zone.response.ZoneWithRessourcesResponseDTO;
import agricore.projet.dto.zone.response.ZoneWithVehiculesResponseDTO;
import agricore.projet.exception.ZoneNotFoundException;
import agricore.projet.model.Zone;
import agricore.projet.repository.IDAOZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZoneService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final IDAOZone daoZone;
    /*
    private final IDAOUtilisateur daoUtilisateur;

    public ZoneService(IDAOZone daoZone, IDAOUtilisateur daoUtilisateur) {
        this.daoZone = daoZone;
        this.daoUtilisateur = daoUtilisateur;
    }
     */
    public ZoneService(IDAOZone daoZone) {
        this.daoZone = daoZone;
    }

    public ZoneResponseDTO getZoneById(Integer id){
        logger.trace("find by id de la zone {}",id);
        return ZoneResponseDTO.convert(daoZone.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Zone avec id {} n'existe pas", id);
                    return new ZoneNotFoundException(id);}));
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
        Zone zone = new Zone();
        zone.setNomZone(request.getNomZone());
        zone.setPosition(PositionRequestDTO.convert(request.getPosition()));

        /* //TODO a revoir (cast en fermier, exception custom)
        zone.setFermier(daoUtilisateur
                .findById(request.getFermierId())
                .orElseThrow(UtilisateurNotFoundExcetion::new));
        */
        zone = daoZone.save(zone);
        logger.trace("Creation de {} a {}",zone.toString(), zone.getPosition().toString());
        return zone.getId();
    }


    public int patch(ZoneRequestDTO request, int id) {// VERIFIER si update partielle marche comme ca ?? De plus les relations ManyToOne / OneToOne (en lazy seront elle ecrasé ??)

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
        }
        logger.trace("Update partiel de zone ({}, {})", request.toString(), id);
        return daoZone.save(zone).getId();
    }

    public int put(ZoneRequestDTO request, int id) {//Ne doit pas être utilisé ! Va ecraser les autres realtions existantes (animaux, ressources, vehicules)
        logger.warn("Update complet de zone, risque d'ecraser les entity associe (animaux, ressources, vehicules, plantes, fermier ! ({}, {})", request.toString(), id);
        Zone zone = new Zone();
        zone.setNomZone(request.getNomZone());
        zone.setPosition(PositionRequestDTO.convert(request.getPosition()));
        return daoZone.save(zone).getId();
    }

    public void delete(int id) {
        logger.warn("Suppression de la zone avec l'Id({})", id);
        daoZone.deleteById(id);//TODO Ca passera jamais, a cause de toutes les relations
    }

    public ZoneWithRessourcesResponseDTO getZoneWithRessources(int id) {
        logger.trace("find by id de la zone avec ces ressources {}",id);
        return ZoneWithRessourcesResponseDTO.convert(daoZone.findByIdWithRessource(id)
                .orElseThrow(() -> {
                    logger.warn("Zone avec id {} n'existe pas", id);
                    return new ZoneNotFoundException(id);}));

    }

    public ZoneWithVehiculesResponseDTO getZoneWithVehicules(int id) {
        logger.trace("find by id de la zone avec ces vehicules {}",id);
        return ZoneWithVehiculesResponseDTO.convert(daoZone.findByIdWithVehicule(id)
                .orElseThrow(() -> {
                    logger.warn("Zone avec id {} n'existe pas", id);
                    return new ZoneNotFoundException(id);}));

    }
}