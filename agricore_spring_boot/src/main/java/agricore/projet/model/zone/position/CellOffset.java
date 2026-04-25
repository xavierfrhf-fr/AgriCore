package agricore.projet.model.zone.position;

import jakarta.persistence.Embeddable;

public record CellOffset(
        //Stock des positions relatives à anchorX et anchorY (l'ancre est stocké dans l'objet Position qui est embarqué en BDD)
        //Un ensemble de CellOffset représente une forme (de batiment)
        //Le set CellOffset est stocké dans l'objet ZoneShape

        //Le record CellGridPosition est une copie de l'enum CellOffset,
        //2 Record disctinct sont utilisé pour ne pas confondre une forme (CellOffSet) et des positions sur la carte (CellGridPosition)

        int x,
        int y
) {

    public CellOffset rotate(Rotation rotation) {
        return switch (rotation) {
            case DEG_O -> this;
            case DEG_90 -> new CellOffset(-y, x);
            case DEG_180 -> new CellOffset(-x, -y);
            case DEG_270 -> new CellOffset(y, -x);
        };

    }

    public CellGridPosition convertToGridPosition(int anchorX, int anchorY, Rotation rotation) {
        CellOffset rotatedCell = this.rotate(rotation);
        return new CellGridPosition(anchorX + rotatedCell.x(), anchorY + rotatedCell.y());
    }
}
