package agricore.projet.dto.response;

import agricore.projet.model.NomZone;
import agricore.projet.model.Zone;

public class ZoneResponseDTO {
    private Integer id;
    //private PositionDto position;
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

    public static ZoneResponseDTO convert (Zone zone){
        ZoneResponseDTO response = new ZoneResponseDTO();
        response.setId(zone.getId());
        response.setNomZone(zone.getNomZone());
        return response;
    }

}
