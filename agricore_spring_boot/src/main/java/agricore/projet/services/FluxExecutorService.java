package agricore.projet.services;

import agricore.projet.contexts.DataContext;
import agricore.projet.contexts.UpdateContext;
import agricore.projet.dto.serviceDTO.RessourceOperationResult;
import agricore.projet.model.flux.Flux;
import agricore.projet.model.flux.RessourceConsumingFlux;
import agricore.projet.model.flux.RessourceProducingFlux;
import agricore.projet.model.flux.RessourceTransformingFlux;
import agricore.projet.model.flux.machine.MachineTransformationFlux;
import org.springframework.stereotype.Service;

@Service
public class FluxExecutorService {

    private final RessourceFluxService ressourceFluxService;

    public FluxExecutorService(RessourceFluxService ressourceFluxService) {
        this.ressourceFluxService = ressourceFluxService;
    }

    public void execute(
            Flux flux,
            UpdateContext updateContext,
            DataContext dataContext
    ) {
        if (flux instanceof MachineTransformationFlux machineTransformationFlux) {
            machineTransformationFlux.executeTransformationStep(
                    updateContext,
                    dataContext,
                    ressourceFluxService
            );
            return;
        }

        if (flux instanceof RessourceProducingFlux producingFlux) {
            executeProducingFlux(flux, producingFlux, updateContext, dataContext);
            return;
        }

        if (flux instanceof RessourceConsumingFlux consumingFlux) {
            executeConsumingFlux(flux, consumingFlux, updateContext, dataContext);
            return;
        }

        flux.execute(updateContext, dataContext);
    }

    private void executeProducingFlux(
            Flux flux,
            RessourceProducingFlux producingFlux,
            UpdateContext updateContext,
            DataContext dataContext
    ) {
        RessourceOperationResult result = ressourceFluxService.applyAtomicDeltas(
                producingFlux.productionDeltas(updateContext),
                dataContext
        );

        if (!result.success()) {
            updateContext.disableFluxForCurrentUpdate(
                    flux.getId(),
                    result.errorMessage()
            );
        }

        flux.execute(updateContext, dataContext);
    }

    private void executeConsumingFlux(
            Flux flux,
            RessourceConsumingFlux consumingFlux,
            UpdateContext updateContext,
            DataContext dataContext
    ) {
        RessourceOperationResult result = ressourceFluxService.applyAtomicDeltas(
                consumingFlux.consumptionDeltas(updateContext),
                dataContext
        );

        if (!result.success()) {
            updateContext.disableFluxForCurrentUpdate(
                    flux.getId(),
                    result.errorMessage()
            );
        }

        flux.execute(updateContext, dataContext);
    }
}