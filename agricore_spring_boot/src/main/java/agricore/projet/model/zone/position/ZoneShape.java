package agricore.projet.model.zone.position;

import java.util.HashSet;
import java.util.Set;

public class ZoneShape {
    //width = x = largeur (horizontal)
    //height = y = hauteur (vertical)

    private Set<CellOffset> shape = new HashSet<>();

    public ZoneShape(Set<CellOffset> shape) {
        this.shape = shape;
    }

    public Set<CellOffset> getShape() {
        return shape;
    }

    public void setShape(Set<CellOffset> shape) {
        this.shape = shape;
    }


    public static ZoneShape rectangle(int width, int height){
        Set<CellOffset> cells = new HashSet<>();
        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                cells.add(new CellOffset(x, y));
            }
        }
        return new ZoneShape(cells);
    }
}
