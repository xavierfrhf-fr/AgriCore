package agricore.projet.dto.zone.request;

import agricore.projet.model.zone.NomZone;
import agricore.projet.model.zone.Zone;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

//TODO
public class ZoneRequestDTO {
    @Valid
    @NotNull(message = "La position ne peut pas être null")
    private PositionRequestDTO position;
    @NotNull(message = "L'enum nomZone ne peut pas être null")
    private NomZone nomZone;


    public ZoneRequestDTO() {}

    public ZoneRequestDTO(PositionRequestDTO position, NomZone nomZone) {
        this.position = position;
        this.nomZone = nomZone;

    }

    public PositionRequestDTO getPosition() {
        return position;
    }

    public void setPosition(PositionRequestDTO position) {
        this.position = position;
    }

    public NomZone getNomZone() {
        return nomZone;
    }

    public void setNomZone(NomZone nomZone) {
        this.nomZone = nomZone;
    }


    public static Zone convert(ZoneRequestDTO request) {
        Zone zone = new Zone();
        zone.setNomZone(request.getNomZone());
        zone.setPosition(PositionRequestDTO.convert(request.getPosition()));
        return zone;

    }
}
