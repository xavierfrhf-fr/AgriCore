package agricore.projet.dto.zone.request;

import agricore.projet.exception.InvalidZoneException;
import agricore.projet.model.NomZone;
import agricore.projet.model.Zone;

//TODO
public class ZoneRequestDTO {
    private PositionRequestDTO position;
    private String nomZone;
    private Integer fermierId;

    public ZoneRequestDTO() {}

    public ZoneRequestDTO(PositionRequestDTO position, String nomZone, Integer fermierId) {
        this.position = position;
        this.nomZone = nomZone;
        this.fermierId = fermierId;
    }

    public PositionRequestDTO getPosition() {
        return position;
    }

    public void setPosition(PositionRequestDTO position) {
        this.position = position;
    }

    public String getNomZone() {
        return nomZone;
    }

    public void setNomZone(String nomZone) {
        this.nomZone = nomZone;
    }

    public Integer getFermierId() {
        return fermierId;
    }

    public void setFermierId(Integer fermierId) {
        this.fermierId = fermierId;
    }

    public static Zone convert(ZoneRequestDTO request) {
        Zone zone = new Zone();
        try{
            zone.setNomZone(NomZone.valueOf(request.getNomZone()));
        }catch(IllegalArgumentException e){
            throw new InvalidZoneException(request.getNomZone());
        }
        zone.setPosition(PositionRequestDTO.convert(request.getPosition()));
        return zone;

    }
}
