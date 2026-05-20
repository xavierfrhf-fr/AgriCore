package agricore.projet.factory;

import agricore.projet.model.Plante;
import agricore.projet.model.flux.plante.GrowthIncreaseFlux;
import agricore.projet.model.flux.plante.HumidityConsumptionFlux;
import agricore.projet.model.flux.plante.PlanteFlux;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlanteFluxFactory {
    public static List<PlanteFlux> createDefaultFluxFor(Plante plante) {
        List<PlanteFlux> flux = new ArrayList<>();

        flux.add(new GrowthIncreaseFlux());
        flux.add(new HumidityConsumptionFlux());

        return flux;
    }
}
