package agricore.projet.dto.data;

import agricore.projet.model.zone.NomZone;

import java.util.List;

public record PlanteZoneInfoDTO(
        NomZone nomZone,
        String nomAffZone,
        String pathSpriteZone,
        int totalZone,
        int freeZone,
        int harvestableZone,
        int dryZone,
        List<Integer> harvestablePlantId,
        List<Integer> allPlantId,
        List<String> pathSpritePlants
) {}
