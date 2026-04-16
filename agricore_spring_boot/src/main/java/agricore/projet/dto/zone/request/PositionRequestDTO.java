package agricore.projet.dto.zone.request;

import agricore.projet.model.Position;
import jakarta.validation.constraints.Min;

public class PositionRequestDTO {
    @Min(value=0, message="positions ne peuvent pas être négatives")
    private Integer posX;
    @Min(value=0, message="positions ne peuvent pas être négatives")
    private Integer posY;
    @Min(value=0, message="tailles ne peuvent pas être négatives")
    private Integer tailleX;
    @Min(value=0, message="tailles ne peuvent pas être négatives")
    private Integer tailleY;

    public PositionRequestDTO() {}

    public PositionRequestDTO(Integer posX, Integer posY, Integer tailleX, Integer tailleY) {
        this.posX = posX;
        this.posY = posY;
        this.tailleX = tailleX;
        this.tailleY = tailleY;
    }

    public Integer getPosX() {
        return posX;
    }

    public void setPosX(Integer posX) {
        this.posX = posX;
    }

    public Integer getPosY() {
        return posY;
    }

    public void setPosY(Integer posY) {
        this.posY = posY;
    }

    public Integer getTailleX() {
        return tailleX;
    }

    public void setTailleX(Integer tailleX) {
        this.tailleX = tailleX;
    }

    public Integer getTailleY() {
        return tailleY;
    }

    public void setTailleY(Integer tailleY) {
        this.tailleY = tailleY;
    }

    public static Position convert(PositionRequestDTO request){
        Position position = new Position();
        position.setPosX(request.getPosX());
        position.setPosY(request.getPosY());
        position.setTailleX(request.getTailleX());
        position.setTailleY(request.getTailleY());
        return position;
    }


}
