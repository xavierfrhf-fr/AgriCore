package agricore.projet.dto.zone.response;

import agricore.projet.model.NomZone;
import agricore.projet.model.Zone;

//TODO
public class ZoneResponseDTO {
    private Integer id;
    private PositionResponseDTO position;
    private NomZone nomZone;

    //private FermierDto fermier; A voir le quel est mieux
    //private Integer fermierId;


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

    public void setPosition(PositionResponseDTO position) {
        this.position = position;
    }

    public static ZoneResponseDTO convert (Zone zone){
        ZoneResponseDTO response = new ZoneResponseDTO();
        response.setId(zone.getId());
        response.setPosition(PositionResponseDTO.convert(zone.getPosition()));
        response.setNomZone(zone.getNomZone());
        return response;
    }

}
