package agricore.projet.dto.zone.request;

import agricore.projet.model.Position;

public class PositionRequestDTO {
    private Integer posX;
    private Integer posY;
    private Integer tailleX;
    private Integer tailleY;

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

    public PositionRequestDTO() {
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
