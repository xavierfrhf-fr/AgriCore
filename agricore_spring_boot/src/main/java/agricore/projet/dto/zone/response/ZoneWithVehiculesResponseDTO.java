package agricore.projet.dto.zone.response;

import agricore.projet.dto.vehicule.response.VehiculeResponseDTO;
import agricore.projet.model.zone.NomZone;
import agricore.projet.model.zone.Zone;

import java.util.List;

public class ZoneWithVehiculesResponseDTO extends ZoneResponseDTO {

    private List<VehiculeResponseDTO> vehicules;

    public ZoneWithVehiculesResponseDTO() {}

    public ZoneWithVehiculesResponseDTO(Integer id, PositionResponseDTO position, NomZone nomZone, Integer fermierId, List<VehiculeResponseDTO> vehicules) {
        super(id, position, nomZone, fermierId);
        this.vehicules = vehicules;
    }

    public List<VehiculeResponseDTO> getVehicules() {
        return vehicules;
    }

    public void setVehicules(List<VehiculeResponseDTO> vehicules) {
        this.vehicules = vehicules;
    }

    public static ZoneWithVehiculesResponseDTO convert(Zone zone){
        ZoneWithVehiculesResponseDTO response = new ZoneWithVehiculesResponseDTO();
        response.setId(zone.getId());
        response.setPosition(PositionResponseDTO.convert(zone));
        response.setNomZone(zone.getNomZone());
        response.setFermierId(zone.getFermier().getId());
        response.setVehicules(zone.getVehicules()
                        .stream()
                        .map(VehiculeResponseDTO::convert)
                        .toList()
                );
        return response;
    }
}
