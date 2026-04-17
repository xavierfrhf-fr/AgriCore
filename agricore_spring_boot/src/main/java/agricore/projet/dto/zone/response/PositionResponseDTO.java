package agricore.projet.dto.zone.response;

import agricore.projet.model.Position;

public class PositionResponseDTO {
    private Integer posX;
    private Integer posY;
    private Integer tailleX;
    private Integer tailleY;

    public PositionResponseDTO(Integer posX, Integer posY, Integer tailleX, Integer tailleY) {
        this.posX = posX;
        this.posY = posY;
        this.tailleX = tailleX;
        this.tailleY = tailleY;
    }

    public PositionResponseDTO() {}

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

    public static PositionResponseDTO convert(Position position){
        PositionResponseDTO response = new PositionResponseDTO();
        response.setPosX(position.getPosX());
        response.setPosY(position.getPosY());
        response.setTailleX(position.getTailleX());
        response.setTailleY(position.getTailleY());
        return response;
    }

}

