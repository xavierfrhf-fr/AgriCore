package agricore.projet.dto.data;

import agricore.projet.model.ressource.Transformation;
import agricore.projet.model.zone.NomZone;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public record TransformationDataDTO(
        Transformation transformation,
        NomZone requiredZone,
        boolean zoneExist,
        int maxTransfoPossible,
        List<TransformationPartDTO> ingredients,
        List<TransformationPartDTO> produits
) {}
