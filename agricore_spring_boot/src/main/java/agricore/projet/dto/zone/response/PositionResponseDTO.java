package agricore.projet.dto.zone.response;

import agricore.projet.model.zone.Zone;
import agricore.projet.model.zone.position.CellOffset;
import agricore.projet.model.zone.position.Position;
import agricore.projet.model.zone.position.Rotation;

import java.util.List;

public class PositionResponseDTO {
    private Integer anchorX;
    private Integer anchorY;
    private Rotation rotation;
    private List<CellOffset> cells;

    public PositionResponseDTO(Integer anchorX, Integer anchorY, Rotation rotation) {
        this.anchorX = anchorX;
        this.anchorY = anchorY;
        this.rotation = rotation;
    }

    public PositionResponseDTO() {}

    public Integer getAnchorX() {
        return anchorX;
    }

    public void setAnchorX(Integer anchorX) {
        this.anchorX = anchorX;
    }

    public Integer getAnchorY() {
        return anchorY;
    }

    public void setAnchorY(Integer anchorY) {
        this.anchorY = anchorY;
    }

    public Rotation getRotation() {
        return rotation;
    }

    public void setRotation(Rotation rotation) {
        this.rotation = rotation;
    }

    public List<CellOffset> getCells() {
        return cells;
    }

    public void setCells(List<CellOffset> cells) {
        this.cells = cells;
    }

    public static PositionResponseDTO convert(Zone zone){
        Position position = zone.getPosition();
        PositionResponseDTO response = new PositionResponseDTO();
        response.setAnchorX(position.getAnchorX());
        response.setAnchorY(position.getAnchorY());
        response.setRotation(position.getRotation());

        response.setCells(zone.getNomZone()
                              .getZoneShape()
                              .getShape()
                              .stream()
                              .toList()
        );

        return response;
    }

}

