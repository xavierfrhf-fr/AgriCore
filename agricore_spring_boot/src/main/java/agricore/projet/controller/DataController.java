package agricore.projet.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import agricore.projet.dto.data.*;
import agricore.projet.exception.RessourceNotFoundException;
import agricore.projet.model.ressource.Transformation;
import agricore.projet.repository.IDAOZone;
import agricore.projet.services.TransformationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import agricore.projet.model.ressource.NomRessource;
import agricore.projet.model.ressource.Unite;
import agricore.projet.model.zone.NomZone;
import agricore.projet.model.zone.position.MapSize;
import agricore.projet.model.zone.position.Position;

@RestController
@RequestMapping("/api/data")
public class DataController {

    private final IDAOZone daoZone;
    private final TransformationService transformationService;

    public DataController(IDAOZone daoZone, TransformationService transformationService) {
        this.daoZone = daoZone;
        this.transformationService = transformationService;
    }

    @GetMapping("/zone")
    public List<ZoneDataDTO> getZoneData() {
        List<ZoneDataDTO> dtos = new ArrayList<>();
        for (NomZone zone : NomZone.values()) {
            ZoneDataDTO zoneData = ZoneDataDTO.from(zone);
            if (zone.isZoneUnique()) {
                zoneData.setZoneCreatable(!this.daoZone.existsByNomZone(zone));
            } else {
                zoneData.setZoneCreatable(true);
            }
            dtos.add(zoneData);
        }
        return dtos;
    }

    @GetMapping("/zone/mapSize")
    public MapSize getMapSize() {
        return Position.mapSize;
    }

    @GetMapping("/ressource")
    public List<RessourceDataDTO> getRessourceData() {
        return Stream.of(NomRessource.values())
                .map(RessourceDataDTO::convert)
                .toList();
    }

    @GetMapping("/unite")
    public List<UniteDataDTO> getUniteData() {
        return Stream.of(Unite.values())
                .map(UniteDataDTO::convert)
                .toList();
    }

    @GetMapping("/transfo")
    public List<TransformationDataDTO> getTransformationData() {
        List<TransformationDataDTO> dtos = new ArrayList<>();

        for (Transformation transformation : Transformation.values()) {
            Integer maxTransfo;
            try {
                maxTransfo = getMaxTransfo(transformation);
            } catch (RessourceNotFoundException e) {
                System.out.println(e.getMessage());
                maxTransfo = 0;
            }

            List<TransformationPartDTO> produits = new ArrayList<>();
            for (Map.Entry<NomRessource, Integer> output : transformation.getOutput().entrySet()) {
                produits.add(new TransformationPartDTO(
                        output.getKey(),
                        output.getValue(),
                        output.getKey().getUniteStockage(),
                        true,
                        output.getValue() * maxTransfo));
            }

            List<TransformationPartDTO> ingredients = new ArrayList<>();
            for (Map.Entry<NomRessource, Integer> input : transformation.getInput().entrySet()) {
                ingredients.add(new TransformationPartDTO(
                        input.getKey(),
                        input.getValue(),
                        input.getKey().getUniteStockage(),
                        false,
                        input.getValue() * maxTransfo));
            }

            dtos.add(new TransformationDataDTO(
                    transformation,
                    transformation.getRequiredZone(),
                    daoZone.existsByNomZone(transformation.getRequiredZone()),
                    maxTransfo,
                    ingredients,
                    produits));

        }
        return dtos;
    }

    private int getMaxTransfo(Transformation transformation) {
        for (Map.Entry<NomRessource, Integer> output : transformation.getOutput().entrySet()) {
            if (Transformation.isProductUnique(output.getKey())) {
                return transformationService.getMaxTransformation(output.getKey(), false, null, true);
            }
        }
        throw new RuntimeException("Error during max transformation computation (DataController -> getMaxTransfo)");
    }
}
