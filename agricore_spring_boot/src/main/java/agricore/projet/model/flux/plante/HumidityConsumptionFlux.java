package agricore.projet.model.flux.plante;

import agricore.projet.contexts.DataContext;
import agricore.projet.contexts.UpdateContext;
import agricore.projet.model.Plante;
import agricore.projet.model.flux.SimulationStrategy;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.Duration;

@Entity
@DiscriminatorValue("PLANT_HUMIDITY_CONSUMPTION")
public class HumidityConsumptionFlux extends PlanteFlux {

    public HumidityConsumptionFlux() {
        super(
                SimulationStrategy.TIME_STEP,
                1,
                true);
    }

    @Override
    public boolean condition(UpdateContext updateContext, DataContext dataContext) {
        return getPlante().getHumidite() > 0;
    }

    @Override
    public void execute(UpdateContext updateContext, DataContext dataContext) {
        Plante plante = getPlante();

        if (plante.getHumidite() <= 0){
            return;
        }

        double minutes = updateContext.stepDuration().toSeconds() / 60.0;

        double consumption = plante.getEspece().getConsommationEauParMin() * minutes;

        double newHumidite = plante.getHumidite() - consumption;

        plante.setHumidite(clamp(newHumidite, 0, 100));
    }


}
