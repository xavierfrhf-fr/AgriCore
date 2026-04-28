package agricore.projet.dto.zone.response;

import agricore.projet.model.zone.position.Position;
import agricore.projet.model.zone.position.Rotation;

public class PositionResponseDTO {
    private Integer anchorX;
    private Integer anchorY;
    private Rotation rotation;

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

    public static PositionResponseDTO convert(Position position){
        PositionResponseDTO response = new PositionResponseDTO();
        response.setAnchorX(position.getAnchorX());
        response.setAnchorY(position.getAnchorY());
        response.setRotation(position.getRotation());
        return response;
    }

}

