package agricore.projet.dto.zone.response;

import agricore.projet.model.NomZone;
import agricore.projet.model.Zone;

//TODO
public class ZoneResponseDTO {
    private Integer id;
    private PositionResponseDTO position;
    private NomZone nomZone;
    private Integer fermierId;

    public ZoneResponseDTO() {}

    public ZoneResponseDTO(Integer id, PositionResponseDTO position, NomZone nomZone, Integer fermierId) {
        this.id = id;
        this.position = position;
        this.nomZone = nomZone;
        this.fermierId = fermierId;
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

    public static ZoneResponseDTO convert (Zone zone){
        ZoneResponseDTO response = new ZoneResponseDTO();
        response.setId(zone.getId());
        response.setFermierId(zone.getFermier().getId());
        response.setPosition(PositionResponseDTO.convert(zone.getPosition()));
        response.setNomZone(zone.getNomZone());
        return response;
    }

}
