package agricore.projet.services;

import agricore.projet.contexts.DataContext;
import agricore.projet.contexts.UpdateContext;
import agricore.projet.model.SimulationState;
import agricore.projet.model.flux.Flux;
import agricore.projet.repository.IDAOFlux;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UpdateManager {

    private static final Duration STEP_DURATION = Duration.ofSeconds(1);
    private static final int MAX_STEPS_PER_UPDATE = 500;

    private final SimulationStateService simulationStateService;
    private final IDAOFlux daoFlux;
    private final DataContext dataContext;
    private final FluxExecutorService fluxExecutorService;

    public UpdateManager(
            SimulationStateService simulationStateService,
            IDAOFlux daoFlux,
            DataContext dataContext,
            FluxExecutorService fluxExecutorService
    ) {
        this.simulationStateService = simulationStateService;
        this.daoFlux = daoFlux;
        this.dataContext = dataContext;
        this.fluxExecutorService = fluxExecutorService;
    }

    @Transactional
    public void updateIfNeeded() {
        SimulationState simulationState = simulationStateService.getOrCreate();

        LocalDateTime lastUpdate = simulationState.getLastUpdate();
        LocalDateTime now = LocalDateTime.now();

        Duration elapsed = Duration.between(lastUpdate, now);

        if (elapsed.compareTo(STEP_DURATION) < 0) {
            return;
        }

        UpdateContext updateContext = new UpdateContext(
                lastUpdate,
                now,
                STEP_DURATION
        );

        List<Flux> activeFlux = daoFlux.findAllActiveOrderedByPriority();

        int executedSteps = 0;

        while (updateContext.canRunNextStep()
                && executedSteps < MAX_STEPS_PER_UPDATE) {

            executeOneStep(activeFlux, updateContext);

            updateContext.nextStep();
            executedSteps++;
        }

        if (executedSteps > 0) {
            simulationState.setLastUpdate(
                    lastUpdate.plus(STEP_DURATION.multipliedBy(executedSteps))
            );
        }
    }

    private void executeOneStep(
            List<Flux> activeFlux,
            UpdateContext updateContext
    ) {
        for (Flux flux : activeFlux) {
            if (updateContext.isFluxDisabledForCurrentUpdate(flux.getId())) {
                continue;
            }

            if (!flux.canExecute(updateContext, dataContext)) {
                continue;
            }

            try {
                fluxExecutorService.execute(flux, updateContext, dataContext);
            } catch (Exception e) {
                updateContext.disableFluxForCurrentUpdate(
                        flux.getId(),
                        "Erreur sur le flux " + flux.getFluxType() + " : " + e.getMessage()
                );
            }
        }
    }
}
