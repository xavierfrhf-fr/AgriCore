package agricore.projet.model.ressource;

import agricore.projet.model.zone.NomZone;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public enum Transformation {
    FROMAGE(
            Map.of(NomRessource.LAIT_VACHE, 2),
            Map.of(NomRessource.FROMAGE, 1),
            NomZone.FROMAGERIE),
    FROMAGE_CHEVRE(
            Map.of(NomRessource.LAIT_CHEVRE, 10),
            Map.of(NomRessource.FROMAGE_CHEVRE, 1),
            NomZone.FROMAGERIE),
    JUS_POMME(
            Map.of(NomRessource.POMME, 5),
            Map.of(NomRessource.JUS_POMME, 1),
            NomZone.PRESSOIR),
    FARINE_BLE(
            Map.of(NomRessource.BLE, 10),
            Map.of(NomRessource.FARINE_BLE, 1),
            NomZone.MOULIN),
    PATE(
            Map.of(NomRessource.LAIT_VACHE, 1,
                    NomRessource.FARINE_BLE, 2),
            Map.of(NomRessource.PATE, 5),
            NomZone.CUISINE),
    PAIN(
            Map.of(NomRessource.PATE, 1),
            Map.of(NomRessource.PAIN, 1),
            NomZone.FOUR),
    PIZZA(
            Map.of(NomRessource.PATE,1,
                    NomRessource.JUS_TOMATE, 1,
                    NomRessource.FROMAGE,2),
            Map.of(NomRessource.PIZZA,1),
            NomZone.FOUR),
    JUS_RAISIN(Map.of(NomRessource.RAISIN, 100),
            Map.of(NomRessource.JUS_RAISIN,1),
            NomZone.PRESSOIR
    ),
    JUS_TOMATE(
            Map.of(NomRessource.TOMATE,5),
            Map.of(NomRessource.JUS_TOMATE,1),
            NomZone.PRESSOIR
    ),
    VIN(Map.of(NomRessource.JUS_RAISIN, 2),
            Map.of(NomRessource.VIN,1),
            NomZone.CUVE_FERMENTATION
    );

    private final Map<NomRessource, Integer> input;
    private final Map<NomRessource, Integer> output;
    private final NomZone requiredZone;

    Transformation(Map<NomRessource, Integer> input, Map<NomRessource, Integer> output, NomZone requiredZone) {
        this.input = input;
        this.output = output;
        this.requiredZone = requiredZone;
    }

    public Map<NomRessource, Integer> getOutput() {
        return output;
    }

    public Map<NomRessource, Integer> getInput() {
        return input;
    }

    public NomZone getRequiredZone() {
        return requiredZone;
    }

    public static Transformation getTransformationByOutput(NomRessource nomRessource) {
        return Arrays.stream(values())
                .filter(transformation -> transformation.output.containsKey(nomRessource))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No transformation allows to produce:" + nomRessource));
    }

    public static boolean isProductUnique(NomRessource nomRessource) {
        return Arrays.stream(values())
                .filter(transformation -> transformation.output.containsKey(nomRessource))
                .count() == 1;
    }

    public static List<Transformation> transformationFromZone(NomZone nomZone) {
        return Arrays.stream(values())
                .filter(transformation -> transformation.requiredZone == nomZone)
                .toList();
    }
}
