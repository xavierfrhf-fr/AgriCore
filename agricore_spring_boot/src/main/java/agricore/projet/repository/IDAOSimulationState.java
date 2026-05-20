package agricore.projet.repository;

import agricore.projet.model.SimulationState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDAOSimulationState extends JpaRepository<SimulationState, Integer> {
}
