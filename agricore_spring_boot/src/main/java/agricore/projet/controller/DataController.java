package agricore.projet.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import agricore.projet.dto.data.*;
import agricore.projet.exception.RessourceNotFoundException;
import agricore.projet.model.EspecePlante;
import agricore.projet.model.ressource.Transformation;
import agricore.projet.repository.IDAOVehicule;
import agricore.projet.repository.IDAOZone;
import agricore.projet.services.TransformationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import agricore.projet.model.animal.EspeceAnimal;
import agricore.projet.model.ressource.NomRessource;
import agricore.projet.model.ressource.Unite;
import agricore.projet.model.zone.NomZone;
import agricore.projet.model.zone.position.MapSize;
import agricore.projet.model.zone.position.Position;

@RestController
@RequestMapping("/api/data")
public class DataController {

    private final IDAOZone daoZone;
    private final IDAOVehicule daoVehicule;
    private final TransformationService transformationService;

    public DataController(IDAOZone daoZone, IDAOVehicule daoVehicule, TransformationService transformationService) {
        this.daoZone = daoZone;
        this.transformationService = transformationService;
        this.daoVehicule = daoVehicule;
    }

    @GetMapping("/zone")
    public List<ZoneDataDTO> getZoneData() {
        List<ZoneDataDTO> dtos = new ArrayList<>();
        for (NomZone nomZone : NomZone.values()) {
            ZoneDataDTO dto = new ZoneDataDTO();
            dto.setNomZone(nomZone.name());
            dto.setTypeZone(nomZone.getTypeZone());
            dto.setPathSprite(nomZone.getPathSprite());
            dto.setZoneUnique(nomZone.isZoneUnique());
            dto.setDescription(nomZone.getDescription());
            dto.setNomAffichage(nomZone.getNomAffichage());
            dto.setShape(nomZone.getZoneShape()
                    .getShape()
                    .stream()
                    .toList());
            if (nomZone.isZoneUnique()) {
                dto.setZoneCreatable(!this.daoZone.existsByNomZone(nomZone));
            } else {
                dto.setZoneCreatable(true);
            }
            dto.setTransformations(Transformation
                    .transformationFromZone(nomZone)
                    .stream()
                    .map(this::convertTransfo)
                    .toList());
            dtos.add(dto);
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
        return Arrays
                .stream(Transformation.values())
                .map(this::convertTransfo)
                .toList();
    }

    public TransformationDataDTO convertTransfo(Transformation transformation) {
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
                    RessourceDataDTO.convert(output.getKey()),
                    output.getValue(),
                    output.getKey().getUniteStockage(),
                    true,
                    output.getValue() * maxTransfo));
        }

        List<TransformationPartDTO> ingredients = new ArrayList<>();
        for (Map.Entry<NomRessource, Integer> input : transformation.getInput().entrySet()) {
            ingredients.add(new TransformationPartDTO(
                    input.getKey(),
                    RessourceDataDTO.convert(input.getKey()),
                    input.getValue(),
                    input.getKey().getUniteStockage(),
                    false,
                    input.getValue() * maxTransfo));
        }
        return new TransformationDataDTO(
                transformation,
                transformation.getRequiredZone(),
                daoZone.existsByNomZone(transformation.getRequiredZone()),
                maxTransfo,
                ingredients,
                produits
        );
    }

    private int getMaxTransfo(Transformation transformation) {
        for (Map.Entry<NomRessource, Integer> output : transformation.getOutput().entrySet()) {
            if (Transformation.isProductUnique(output.getKey())) {
                return transformationService.getMaxTransformation(output.getKey(), false, null, true);
            }
        }
        throw new RuntimeException("Error during max transformation computation (DataController -> getMaxTransfo)");
    }

    @GetMapping("/animal")
    public List<AnimalDataDTO> getAnimalData() {
        return Stream.of(EspeceAnimal.values())
                .map(AnimalDataDTO::convert)
                .toList();
    }

    @GetMapping("/plante")
    public List<PlanteDataDTO> getPlanteData() {
        List<PlanteDataDTO> dtos = new ArrayList<>();
        for (EspecePlante especePlante : EspecePlante.values()) {
            int numberCreatable = Math.toIntExact(daoZone
                    .findByName(especePlante.getAllowedZone())
                    .stream()
                    .filter(z -> z.getPlante() == null)
                    .count());
            boolean isProductStorable = daoZone.existsByNomZone(especePlante.getAllowedZone());
            boolean isVehiculeAvailable = daoVehicule.existsByTypeVehicule(especePlante.getVehiculeRequis());
            dtos.add(PlanteDataDTO.convert(especePlante, numberCreatable, isProductStorable, isVehiculeAvailable));
        }
        return dtos;
    }

}
