package agricore.projet.model.flux;

import agricore.projet.contexts.DataContext;
import agricore.projet.contexts.UpdateContext;
import jakarta.persistence.*;

import java.time.Duration;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "flux_type")
public abstract class Flux {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SimulationStrategy simulationStrategy;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private int priority = 0;


    public Flux(SimulationStrategy simulationStrategy, int priority, boolean active) {
        this.simulationStrategy = simulationStrategy;
        this.priority = priority;
        this.active = active;
    }

    public Flux() {

    }

    public abstract boolean condition(UpdateContext updateContext, DataContext dataContext);

    public abstract void execute(UpdateContext updateContext, DataContext dataContext);

    public boolean canExecute(UpdateContext updateContext, DataContext dataContext) {
        return active && condition(updateContext, dataContext);
    }

    public Integer getId() {
        return id;
    }

    public SimulationStrategy getSimulationStrategy() {
        return simulationStrategy;
    }

    public void setSimulationStrategy(SimulationStrategy simulationStrategy) {
        this.simulationStrategy = simulationStrategy;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getFluxType(){
        return this.getClass().getSimpleName();
    }

    protected double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
