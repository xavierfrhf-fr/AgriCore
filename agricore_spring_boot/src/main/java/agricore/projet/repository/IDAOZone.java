package agricore.projet.repository;

import agricore.projet.model.zone.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IDAOZone extends JpaRepository<Zone,Integer> {
    @Query("SELECT z FROM Zone z LEFT JOIN FETCH z.animals WHERE z.id = :id")// EUUUH FAUT VERIFIER QUE CA MARCHE
    Optional<Zone> findByIdWithAnimal(@Param("id") Integer id);

    @Query("SELECT z FROM Zone z LEFT JOIN FETCH z.vehicules WHERE z.id = :id")
    Optional<Zone> findByIdWithVehicule(@Param("id") Integer id);

    @Query("SELECT z FROM Zone z LEFT JOIN FETCH z.ressources WHERE z.id = :id")
    Optional<Zone> findByIdWithRessource(@Param("id") Integer id);

}
