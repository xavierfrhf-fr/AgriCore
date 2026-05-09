package agricore.projet.dto.zone.response;

import agricore.projet.dto.ressource.response.RessourceResponseDTO;
import agricore.projet.model.zone.NomZone;
import agricore.projet.model.zone.Zone;

import java.util.List;

public class ZoneWithRessourcesResponseDTO extends ZoneResponseDTO {
    private List<RessourceResponseDTO> ressources;

    public ZoneWithRessourcesResponseDTO() {}

    public ZoneWithRessourcesResponseDTO(Integer id, PositionResponseDTO position, NomZone nomZone, List<RessourceResponseDTO> ressources) {
        super(id, position, nomZone);
        this.ressources = ressources;
    }

    public List<RessourceResponseDTO> getRessources() {
        return ressources;
    }

    public void setRessources(List<RessourceResponseDTO> ressources) {
        this.ressources = ressources;
    }

    public static ZoneWithRessourcesResponseDTO convert (Zone zone){
        ZoneWithRessourcesResponseDTO response = new ZoneWithRessourcesResponseDTO();
        response.setId(zone.getId());
        response.setPosition(PositionResponseDTO.convert(zone));
        response.setNomZone(zone.getNomZone());
        response.setRessources(zone.getRessources()
                .stream()
                .map(RessourceResponseDTO::convert)
                .toList());
        return response;
    }
}
