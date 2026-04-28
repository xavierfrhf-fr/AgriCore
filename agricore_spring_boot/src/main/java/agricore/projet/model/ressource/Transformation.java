package agricore.projet.model.ressource;

import agricore.projet.model.zone.NomZone;

import java.util.Arrays;
import java.util.Map;

public enum Transformation {
    FROMAGE(
            Map.of(NomRessource.LAIT,2),
            Map.of(NomRessource.FROMAGE, 1),
            NomZone.FROMAGERIE),
    JUS_DE_POMME(
            Map.of(NomRessource.POMME, 5),
            Map.of(NomRessource.JUS_POMME, 1),
            NomZone.PRESSOIR),
    FARINE_BLE(
            Map.of(NomRessource.BLE, 10),
            Map.of(NomRessource.FARINE_BLE, 1),
            NomZone.MOULIN),
    PATE(
            Map.of(NomRessource.LAIT, 1,
                   NomRessource.FARINE_BLE, 2),
            Map.of(NomRessource.PATE,5),
            NomZone.CUISINE);


    private final Map<NomRessource, Integer> input;
    private final Map<NomRessource, Integer> output;
    private final NomZone requiredZone;

    Transformation(Map<NomRessource, Integer> input, Map<NomRessource, Integer> output, NomZone requiredZone){
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

    public static Transformation getTransformationByOutput(NomRessource nomRessource){
        return Arrays.stream(values())
                .filter(transformation -> transformation.output.containsKey(nomRessource))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("No transformation allows to produce:"+nomRessource));
    }
}
