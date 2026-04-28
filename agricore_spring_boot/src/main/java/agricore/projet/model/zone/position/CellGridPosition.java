package agricore.projet.model.zone.position;

import java.util.Set;

public record CellGridPosition(
        //Stock des positions absolues sur la carte.
        //Le record CellOffset est une copie de l'enum CellGridPosition,
        //2 Record disctinct sont utilisé pour ne pas confondre une forme (CellOffSet) et des positions sur la carte (CellGridPosition)

        int x,
        int y
) {
    public static boolean hasOverlap(Set<CellGridPosition> cells1,
                                     Set<CellGridPosition> cells2) {
        return cells1.stream()
                .anyMatch(cells2::contains);
    }

    public static boolean addIfNoOverlap(Set<CellGridPosition> existingSet,
                                         Set<CellGridPosition> setToAdd) {
        if (hasOverlap(existingSet, setToAdd)) {
            return false;
        }

        existingSet.addAll(setToAdd);
        return true;
    }


}
