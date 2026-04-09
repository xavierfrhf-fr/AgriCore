package agricore.projet.dto.zone.response;

import agricore.projet.dto.vehicule.response.VehiculeResponseDTO;
import agricore.projet.model.NomZone;
import agricore.projet.model.Zone;

import java.util.List;

public class ZoneWithVehiculesResponseDTO {
    private Integer id;
    private PositionResponseDTO position;
    private NomZone nomZone;
    private List<VehiculeResponseDTO> vehicules;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PositionResponseDTO getPosition() {
        return position;
    }

    public void setPosition(PositionResponseDTO position) {
        this.position = position;
    }

    public NomZone getNomZone() {
        return nomZone;
    }

    public void setNomZone(NomZone nomZone) {
        this.nomZone = nomZone;
    }

    public List<VehiculeResponseDTO> getVehicules() {
        return vehicules;
    }

    public void setVehicules(List<VehiculeResponseDTO> vehicules) {
        this.vehicules = vehicules;
    }

    public ZoneWithVehiculesResponseDTO() {
    }

    public static ZoneWithVehiculesResponseDTO convert(Zone zone){
        ZoneWithVehiculesResponseDTO response = new ZoneWithVehiculesResponseDTO();
        response.setId(zone.getId());
        response.setPosition(PositionResponseDTO.convert(zone.getPosition()));
        response.setNomZone(zone.getNomZone());
        response.setVehicules(zone.getVehicules()
                        .stream()
                        .map(VehiculeResponseDTO::convert)
                        .toList()
                );
        return response;
    }
}
