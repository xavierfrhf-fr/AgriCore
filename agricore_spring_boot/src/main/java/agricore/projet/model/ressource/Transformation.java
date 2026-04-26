package agricore.projet.model.ressource;

import java.util.Arrays;
import java.util.Map;

public enum Transformation {
    FROMAGE(
            Map.of(NomRessource.Fromage, 1),
            Map.of(NomRessource.Lait,2)
    );

    private final Map<NomRessource, Integer> output;
    private final Map<NomRessource, Integer> input;

    Transformation(Map<NomRessource, Integer> output, Map<NomRessource, Integer> input){
        this.output = output;
        this.input = input;
    }

    public Map<NomRessource, Integer> getOutput() {
        return output;
    }

    public Map<NomRessource, Integer> getInput() {
        return input;
    }

    public static Transformation getTransformationByOutput(NomRessource nomRessource){
        return Arrays.stream(values())
                .filter(transformation -> transformation.output.containsKey(nomRessource))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("No transformation allows to produce:"+nomRessource));
    }
}
