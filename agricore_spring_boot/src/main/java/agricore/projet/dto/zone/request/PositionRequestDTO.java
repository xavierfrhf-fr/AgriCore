package agricore.projet.dto.zone.request;

import agricore.projet.model.zone.position.Position;
import agricore.projet.model.zone.position.Rotation;
import jakarta.validation.constraints.Min;

public class PositionRequestDTO {

    private Integer anchorX;
    private Integer anchorY;
    private Rotation rotation;

    public PositionRequestDTO() {}

    public PositionRequestDTO(Integer anchorX, Integer anchorY, Rotation rotation) {
        this.anchorX = anchorX;
        this.anchorY = anchorY;
        this.rotation = rotation;
    }

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

    public static Position convert(PositionRequestDTO request){
        Position position = new Position();
        position.setAnchorX(request.getAnchorX());
        position.setAnchorY(request.getAnchorY());
        position.setRotation(request.getRotation());
        return position;
    }


}
