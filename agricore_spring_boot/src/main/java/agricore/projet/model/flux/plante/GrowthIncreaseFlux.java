package agricore.projet.model.flux.plante;

import agricore.projet.contexts.DataContext;
import agricore.projet.contexts.UpdateContext;
import agricore.projet.model.Plante;
import agricore.projet.model.flux.SimulationStrategy;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("PLANT_GROWTH")
public class GrowthIncreaseFlux extends PlanteFlux{

    public GrowthIncreaseFlux() {
        super(
                SimulationStrategy.TIME_STEP,
                2,
                true
        );
    }

    @Override
    public boolean condition(UpdateContext updateContext, DataContext dataContext) {
        return getPlante().getHumidite() > 0;
    }

    @Override
    public void execute(UpdateContext updateContext, DataContext dataContext) {
        Plante plante = getPlante();

        if (plante.isMature() || plante.getHumidite() <= 0){
            return;
        }

        double minutes = updateContext.stepDuration().toSeconds() /60. ;

        double growth = plante.getCroissanceParMin() * minutes;

        double newGrowth = plante.getCroissance() + growth;

        plante.setCroissance(clamp(newGrowth, 0, 100));

        if (newGrowth >= 100){
            plante.setMature(true);
        }
    }
}
