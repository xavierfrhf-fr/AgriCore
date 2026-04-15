package agricore.projet.dto.zone.request;

import agricore.projet.model.NomZone;
import agricore.projet.model.Zone;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.validation.annotation.Validated;

//TODO
public class ZoneRequestDTO {
    @Valid
    @NotNull(message = "Position requise")
    private PositionRequestDTO position;
    @Min
    private NomZone nomZone;
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
