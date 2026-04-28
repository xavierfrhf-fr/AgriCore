package agricore.projet.dto.zone.response;

import agricore.projet.dto.ressource.response.RessourceResponseDTO;
import agricore.projet.model.zone.NomZone;
import agricore.projet.model.zone.Zone;

import java.util.List;

public class ZoneWithRessourcesResponseDTO {
    private Integer id;
    private PositionResponseDTO position;
    private NomZone nomZone;
    private Integer fermierId;
    private List<RessourceResponseDTO> ressources;

    public ZoneWithRessourcesResponseDTO() {}

    public ZoneWithRessourcesResponseDTO(Integer id, PositionResponseDTO position, NomZone nomZone, Integer fermierId, List<RessourceResponseDTO> ressources) {
        this.id = id;
        this.position = position;
        this.nomZone = nomZone;
        this.fermierId = fermierId;
        this.ressources = ressources;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public NomZone getNomZone() {
        return nomZone;
    }

    public void setNomZone(NomZone nomZone) {
        this.nomZone = nomZone;
    }

    public PositionResponseDTO getPosition() {
        return position;
    }

    public Integer getFermierId() {
        return fermierId;
    }

    public void setFermierId(Integer fermierId) {
        this.fermierId = fermierId;
    }

    public void setPosition(PositionResponseDTO position) {
        this.position = position;
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
        response.setFermierId(zone.getFermier().getId());
        response.setPosition(PositionResponseDTO.convert(zone));
        response.setNomZone(zone.getNomZone());
        response.setRessources(zone.getRessources()
                .stream()
                .map(RessourceResponseDTO::convert)
                .toList());
        return response;
    }
}
