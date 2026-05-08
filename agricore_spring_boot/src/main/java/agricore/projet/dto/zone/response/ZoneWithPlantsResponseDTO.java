package agricore.projet.dto.zone.response;

import agricore.projet.dto.plante.response.PlanteResponseDTO;
import agricore.projet.model.Plante;
import agricore.projet.model.zone.NomZone;
import agricore.projet.model.zone.Zone;

public class ZoneWithPlantsResponseDTO extends ZoneResponseDTO{
    private PlanteResponseDTO plante;

    public ZoneWithPlantsResponseDTO(Integer id, PositionResponseDTO position, NomZone nomZone, PlanteResponseDTO plante) {
        super(id, position, nomZone);
        this.plante = plante;
    }

    public ZoneWithPlantsResponseDTO() {
    }

    public PlanteResponseDTO getPlante() {
        return plante;
    }

    public void setPlante(PlanteResponseDTO plante) {
        this.plante = plante;
    }

    public static ZoneWithPlantsResponseDTO convert (Zone zone){
        ZoneWithPlantsResponseDTO response = new ZoneWithPlantsResponseDTO();
        response.setId(zone.getId());
        response.setPosition(PositionResponseDTO.convert(zone));
        response.setNomZone(zone.getNomZone());
        try{
            response.setPlante(PlanteResponseDTO.convert(zone.getPlante()));
        }catch (NullPointerException e){
            response.setPlante(null);
        }

        return response;
    }


}
