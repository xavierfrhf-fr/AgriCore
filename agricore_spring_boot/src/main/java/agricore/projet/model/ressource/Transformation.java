package agricore.projet.model.ressource;

import agricore.projet.model.zone.NomZone;

import java.util.Arrays;
import java.util.Map;

public enum Transformation {
    FROMAGE(
            Map.of(NomRessource.Lait,2),
            Map.of(NomRessource.Fromage, 1),
            NomZone.FROMAGERIE),
    JUS_DE_POMME(
            Map.of(NomRessource.Pomme, 5),
            Map.of(NomRessource.JusDePomme, 1),
            NomZone.PRESSOIR),
    FARINE_BLE(
            Map.of(NomRessource.Blé, 10),
            Map.of(NomRessource.FarineBlé, 1),
            NomZone.MOULIN),
    PATE(
            Map.of(NomRessource.Lait, 1,
                   NomRessource.FarineBlé, 2),
            Map.of(NomRessource.Pate,5),
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
