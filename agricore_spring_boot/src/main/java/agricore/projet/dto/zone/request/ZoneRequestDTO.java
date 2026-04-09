package agricore.projet.dto.zone.request;

import agricore.projet.model.NomZone;

//TODO
public class ZoneRequestDTO {
    private PositionRequestDTO position;
    private NomZone nomZone;
    private Integer fermierId;

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
}
