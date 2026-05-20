package agricore.projet.model.flux.machine;

import agricore.projet.contexts.DataContext;
import agricore.projet.contexts.UpdateContext;
import agricore.projet.dto.serviceDTO.RessourceDelta;
import agricore.projet.dto.serviceDTO.RessourceOperationResult;
import agricore.projet.model.flux.RessourceTransformingFlux;
import agricore.projet.model.flux.SimulationStrategy;
import agricore.projet.model.ressource.NomRessource;
import agricore.projet.model.ressource.Transformation;
import agricore.projet.services.RessourceFluxService;
import jakarta.persistence.*;

import java.util.List;

@Entity
@DiscriminatorValue("MACHINE_TRANSFORMATION")
public class MachineTransformationFlux
        extends MachineProgressFlux
        implements RessourceTransformingFlux {

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Transformation transformation;

    protected MachineTransformationFlux() {
    }

    public MachineTransformationFlux(double transformationsPerMinute) {
        super(
                SimulationStrategy.COMPLETE,
                20,
                true,
                transformationsPerMinute
        );
    }

    @Override
    public boolean condition(UpdateContext updateContext, DataContext dataContext) {
        return isActive();
    }

    @Override
    public void execute(UpdateContext updateContext, DataContext dataContext) {
        // l'exécution réelle dépendra du ResourceFluxService.
        // Voir section UpdateManager avec dispatch.
    }

    @Override
    public Transformation getTransformation() {
        return this.transformation;
    }

    @Override
    public List<RessourceDelta> unitRessourceDeltas() {
        return List.of(
                new RessourceDelta(NomRessource.BLE, -5),
                new RessourceDelta(NomRessource.FARINE_BLE, 1)
        );
    }

    public void executeTransformationStep(
            UpdateContext updateContext,
            DataContext dataContext,
            RessourceFluxService ressourceFluxService//GRRRRRR TOUT CE QUE JE VOULAIS EVITER .........
    ) {
        int maxPossible = ressourceFluxService.computeMaxTransformations(
                unitRessourceDeltas(),
                dataContext
        );

        if (maxPossible <= 0) {
            updateContext.disableFluxForCurrentUpdate(
                    getId(),
                    "Ressources insuffisantes pour " + getFluxType()
            );
            return;
        }

        addProgress(updateContext.stepDuration());

        int completed = completedTransformations();

        if (completed <= 0) {
            return;
        }

        int transformationsToApply = Math.min(completed, maxPossible);

        RessourceOperationResult result = ressourceFluxService.applyAtomicDeltas(
                ressourceDeltas(transformationsToApply),
                dataContext
        );

        if (!result.success()) {
            updateContext.disableFluxForCurrentUpdate(
                    getId(),
                    result.errorMessage()
            );
            return;
        }

        consumeTransformations(transformationsToApply);
    }
}
