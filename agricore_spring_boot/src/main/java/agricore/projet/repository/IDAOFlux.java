package agricore.projet.repository;

import agricore.projet.model.flux.Flux;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IDAOFlux extends JpaRepository<Flux, Integer> {

    @Query("""
        SELECT f FROM Flux f
        WHERE f.active = true
        ORDER BY f.priority ASC
    """)
    List<Flux> findAllActiveOrderedByPriority();
}