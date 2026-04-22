package agricore.projet.dto.zone.request;

import agricore.projet.model.NomZone;
import agricore.projet.model.Zone;
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
    @NotNull(message = "ID du fermier ne peut pas être null")
    @Min(value = 0, message = "ID du fermier ne peut pas être négatif")
    private Integer fermierId;

    public ZoneRequestDTO() {}

    public ZoneRequestDTO(PositionRequestDTO position, NomZone nomZone, Integer fermierId) {
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

    public static Zone convert(ZoneRequestDTO request) {
        Zone zone = new Zone();
        zone.setNomZone(request.getNomZone());
        zone.setPosition(PositionRequestDTO.convert(request.getPosition()));
        return zone;

    }
}
