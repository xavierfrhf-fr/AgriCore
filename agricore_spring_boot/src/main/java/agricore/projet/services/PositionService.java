package agricore.projet.services;

import agricore.projet.model.zone.Zone;
import agricore.projet.model.zone.position.CellGridPosition;
import agricore.projet.model.zone.position.Position;
import agricore.projet.repository.IDAOZone;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PositionService {

    private final IDAOZone daoZone;


    public PositionService(IDAOZone daoZone) {
        this.daoZone = daoZone;
    }

    public Set<CellGridPosition> buildCellSetFromAllZone(){
        Set<CellGridPosition> cellSet = new HashSet<>();
        for(Zone zone : daoZone.findAll()){
            if (!CellGridPosition.addIfNoOverlap(cellSet,zone.getCellGridPositions())){
                throw new RuntimeException("ERROR DURING LOADING ZONE FROM DB");
            }
        }
        return cellSet;
    }

    public Set<CellGridPosition> buildCellSetFromAllZoneExceptOne(int zoneId){
        Set<CellGridPosition> cellSet = new HashSet<>();
        for(Zone zone : daoZone.findAll()){
            if (zone.getId() == zoneId){
                continue;
            }
            if (!CellGridPosition.addIfNoOverlap(cellSet,zone.getCellGridPositions())){
                throw new RuntimeException("ERROR DURING LOADING ZONE FROM DB");
            }
        }
        return cellSet;
    }

    public boolean isPositionInsideMap(Zone zone){
        return Position.mapSize.isPositionInBounds(zone.getCellGridPositions());
    }

    public boolean zoneCanBeAdded(Zone zone){
        if (!isPositionInsideMap(zone)){
            return false;
        }
        return !CellGridPosition.hasOverlap(buildCellSetFromAllZone(),
                zone.getCellGridPositions()
        );
    }

    public boolean zoneCanBeMoved(Zone zone){
        if (!isPositionInsideMap(zone)){
            return false;
        }
        return !CellGridPosition.hasOverlap(
                buildCellSetFromAllZoneExceptOne(zone.getId()),
                zone.getCellGridPositions()
        );
    }
}
