package agricore.projet.services;

import java.util.List;

import agricore.projet.dto.ressource.request.RessourceRequestDTO;
import agricore.projet.dto.ressource.response.RessourceResponseDTO;
import agricore.projet.model.Ressource;
import agricore.projet.repository.IDAORessource;
import agricore.projet.repository.IDAOZone;

//TODO LOGS ET TESTS
public class RessourceService {
    private final IDAORessource daoRessource;
    private final IDAOZone daoZone;

    public RessourceService(IDAORessource daoRessource, IDAOZone daoZone) {
        this.daoRessource = daoRessource;
        this.daoZone = daoZone;
    }

    public List<RessourceResponseDTO> getAll() {
        return daoRessource.findAll()
                .stream()
                .map(RessourceResponseDTO::convert)
                .toList();
    }

    public RessourceResponseDTO getById(Integer id) {
        return daoRessource.findById(id)
                .map(RessourceResponseDTO::convert)
                .orElse(null);
    }

    public RessourceResponseDTO create(RessourceRequestDTO request) {
        Ressource r = new Ressource();
        r.setNom(request.getNom());
        r.setQuantite(request.getQuantite());
        r.setPrix(request.getPrix());
        r.setStockMin(request.getStockMin());
        r.setZone(daoZone.findById(request.getZoneId()).orElseThrow());
        return RessourceResponseDTO.convert(daoRessource.save(r));
    }

    public RessourceResponseDTO patch(Integer id, RessourceRequestDTO request) {
        Ressource r = daoRessource.findById(id)
                .orElseThrow(() -> new RuntimeException("Ressource introuvable pour l'id " + id));
        if (request.getNom() != null)
            r.setNom(request.getNom());
        if (request.getQuantite() != 0)
            r.setQuantite(request.getQuantite());
        if (request.getPrix() != 0)
            r.setPrix(request.getPrix());
        if (request.getStockMin() != 0)
            r.setStockMin(request.getStockMin());
        if (request.getZoneId() != null)
            r.setZone(daoZone.findById(request.getZoneId()).orElseThrow());
        return RessourceResponseDTO.convert(daoRessource.save(r));
    }

    public RessourceResponseDTO update(Integer id, RessourceRequestDTO request) {
        Ressource r = daoRessource.findById(id)
                .orElseThrow(() -> new RuntimeException("Ressource introuvable pour l'id " + id));
        r.setNom(request.getNom());
        r.setQuantite(request.getQuantite());
        r.setPrix(request.getPrix());
        r.setStockMin(request.getStockMin());
        r.setZone(daoZone.findById(request.getZoneId()).orElseThrow());
        return RessourceResponseDTO.convert(daoRessource.save(r));
    }

    public void deleteById(Integer id) {
        daoRessource.findById(id)
                .orElseThrow(() -> new RuntimeException("Ressource introuvable pour l'id " + id));
        daoRessource.deleteById(id);
    }

}
