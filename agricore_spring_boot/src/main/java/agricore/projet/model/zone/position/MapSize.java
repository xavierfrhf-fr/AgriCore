package agricore.projet.model.zone.position;

import java.util.Set;

public record MapSize(int x, int y) {
    //Definis la taille de la carte (les cellules existantes vont de 0 (INCLUS) à x/y (EXCLUS)
    //L'origine (0,0) est en haut à gauche, son opposé (la case en bas à droite) est là x-1, y-1

    //La carte est instancié est stocké dans Position (static)

    public boolean isPositionInBounds(int xPos, int yPos) {
        //Vérifie si les positions sont dans les limites de la carte
        return (xPos >= 0 && xPos < this.x && yPos >= 0 && yPos < this.y);
    }

    public boolean isPositionInBounds(CellGridPosition cell) {
        //Surcharge pour prendre une objet CellGridPosition
        return this.isPositionInBounds(cell.x(), cell.y());
    }

    public boolean isPositionInBounds(Set<CellGridPosition> cells) {
        //Surcharge pour tester un ensemble de CellGridPosition
        return cells.stream().anyMatch(this::isPositionInBounds);
    }
}
