package agricore.projet.model.flux.plante;

import agricore.projet.model.Plante;
import agricore.projet.model.flux.Flux;
import agricore.projet.model.flux.SimulationStrategy;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public abstract class PlanteFlux extends Flux {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plante_id", nullable = true)
    private Plante plante;

    public PlanteFlux(SimulationStrategy simulationStrategy, int priority, boolean active) {
        super(simulationStrategy, priority, active);
    }

    public PlanteFlux() {

    }

    public void setPlante(Plante plante) {
        this.plante = plante;
    }

    public Plante getPlante() {
        return plante;
    }
}
