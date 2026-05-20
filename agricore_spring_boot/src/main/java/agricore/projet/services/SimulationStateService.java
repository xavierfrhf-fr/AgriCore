package agricore.projet.services;

import agricore.projet.model.SimulationState;
import agricore.projet.repository.IDAOSimulationState;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SimulationStateService {

    private final IDAOSimulationState daoSimulationState;

    public SimulationStateService(IDAOSimulationState daoSimulationState) {
        this.daoSimulationState = daoSimulationState;
    }

    public SimulationState getOrCreate() {
        return daoSimulationState.findById(1)
                .orElseGet(() -> daoSimulationState.save(
                        new SimulationState(LocalDateTime.now())
                ));
    }
}
