package agricore.projet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import agricore.projet.model.zone.NomZone;
import agricore.projet.model.zone.Zone;

public interface IDAOZone extends JpaRepository<Zone, Integer> {
    @Query("SELECT z FROM Zone z LEFT JOIN FETCH z.animals WHERE z.id = :id") // EUUUH FAUT VERIFIER QUE CA MARCHE
    Optional<Zone> findByIdWithAnimal(@Param("id") Integer id);

    @Query("SELECT z FROM Zone z LEFT JOIN FETCH z.vehicules WHERE z.id = :id")
    Optional<Zone> findByIdWithVehicule(@Param("id") Integer id);

    @Query("SELECT z FROM Zone z LEFT JOIN FETCH z.ressources WHERE z.id = :id")
    Optional<Zone> findByIdWithRessource(@Param("id") Integer id);

    @Query("SELECT z FROM Zone z LEFT JOIN FETCH z.ressources WHERE z.nomZone = :nomZone")
    Optional<Zone> findZoneByNomZone(@Param("nomZone") NomZone nomZone);

    @Query("SELECT z FROM Zone z WHERE z.nomZone = :name")
    List<Zone> findByName(@Param("name") NomZone name);

    boolean existsByNomZone(NomZone nomZone);

}
