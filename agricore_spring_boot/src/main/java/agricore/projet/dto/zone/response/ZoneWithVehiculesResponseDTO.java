package agricore.projet.dto.zone.response;

import agricore.projet.dto.vehicule.response.VehiculeResponseDTO;
import agricore.projet.model.zone.NomZone;
import agricore.projet.model.zone.Zone;

import java.util.List;

public class ZoneWithVehiculesResponseDTO {
    private Integer id;
    private PositionResponseDTO position;
    private NomZone nomZone;
    private Integer fermierId;
    private List<VehiculeResponseDTO> vehicules;

    public ZoneWithVehiculesResponseDTO() {}

    public ZoneWithVehiculesResponseDTO(Integer id, PositionResponseDTO position, NomZone nomZone, Integer fermierId, List<VehiculeResponseDTO> vehicules) {
        this.id = id;
        this.position = position;
        this.nomZone = nomZone;
        this.fermierId = fermierId;
        this.vehicules = vehicules;
    }

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

    public Integer getFermierId() {
        return fermierId;
    }

    public void setFermierId(Integer fermierId) {
        this.fermierId = fermierId;
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
