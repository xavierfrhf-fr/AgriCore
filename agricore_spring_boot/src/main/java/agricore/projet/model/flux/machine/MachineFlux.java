package agricore.projet.model.flux.machine;

import agricore.projet.model.flux.Flux;
import agricore.projet.model.flux.SimulationStrategy;
import agricore.projet.model.zone.Zone;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public abstract class MachineFlux extends Flux {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id", nullable = true)
    private Zone zone;

    protected MachineFlux() {
    }

    protected MachineFlux(
            SimulationStrategy simulationStrategy,
            int priority,
            boolean active
    ) {
        super(simulationStrategy, priority, active);
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }
}