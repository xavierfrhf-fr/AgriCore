package agricore.projet.services;


import agricore.projet.dto.zone.request.PositionRequestDTO;
import agricore.projet.dto.zone.request.ZoneRequestDTO;
import agricore.projet.dto.zone.response.ZoneResponseDTO;
import agricore.projet.exception.ZoneNotFoundException;
import agricore.projet.model.Zone;
import agricore.projet.repository.IDAOZone;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZoneService {

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
        return ZoneResponseDTO.convert(daoZone.findById(id)
                .orElseThrow(() -> new ZoneNotFoundException(id)));
    }

    public List<ZoneResponseDTO> getAllZone() {
        return daoZone.findAll()
                .stream()
                .map(ZoneResponseDTO::convert)
                .toList();
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

        return daoZone.save(zone).getId();
    }


    public int patch(ZoneRequestDTO request, int id) {// VERIFIER si update partielle marche comme ca ?? De plus les relations ManyToOne / OneToOne (en lazy seront elle ecrasé ??)
        Zone zone = daoZone.findById(id)
                .orElseThrow(() -> new ZoneNotFoundException(id));
        if (request.getNomZone() != null) {
            zone.setNomZone(request.getNomZone());
        }
        if (request.getPosition() != null) {
            zone.setPosition(PositionRequestDTO.convert(request.getPosition()));
        }
        return daoZone.save(zone).getId();
    }

    public int put(ZoneRequestDTO request, int id) {//Ne doit pas être utilisé ! Va ecraser les autres realtions existantes (animaux, ressources, vehicules)
        Zone zone = new Zone();
        zone.setNomZone(request.getNomZone());
        zone.setPosition(PositionRequestDTO.convert(request.getPosition()));
        return daoZone.save(zone).getId();
    }

    public void delete(int id) {
        daoZone.deleteById(id);//TODO Ca passera jamais, a cause de toutes les relations
    }
}