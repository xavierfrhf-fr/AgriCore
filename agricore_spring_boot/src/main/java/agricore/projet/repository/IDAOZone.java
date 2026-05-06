package agricore.projet.repository;

import agricore.projet.model.zone.NomZone;
import agricore.projet.model.zone.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

public interface IDAOZone extends JpaRepository<Zone, Integer> {
    @Query("SELECT z FROM Zone z LEFT JOIN FETCH z.animals WHERE z.id = :id") // EUUUH FAUT VERIFIER QUE CA MARCHE
    Optional<Zone> findByIdWithAnimal(@Param("id") Integer id);

    @Query("SELECT z FROM Zone z LEFT JOIN FETCH z.vehicules WHERE z.id = :id")
    Optional<Zone> findByIdWithVehicule(@Param("id") Integer id);

    @Query("SELECT z FROM Zone z LEFT JOIN FETCH z.ressources WHERE z.id = :id")
    Optional<Zone> findByIdWithRessource(@Param("id") Integer id);

    @Query("SELECT z FROM Zone z LEFT JOIN FETCH z.ressources WHERE z.nomZone = :nomZone")
    Optional<Zone> findZoneByNomZone(@Param("nomZone") NomZone nomZone);

    boolean existsByNomZone(NomZone nomZone);

    @Query("SELECT DISTINCT z FROM Zone z LEFT JOIN FETCH z.ressources WHERE z.nomZone IN :nomZones")
    ArrayList<Zone> findByNomZonesWithRessource(@Param("nomZones") Set<NomZone> nomZones);

    @Query("SELECT DISTINCT z FROM Zone z LEFT JOIN FETCH z.vehicules WHERE z.nomZone IN :nomZones")
    ArrayList<Zone> findByNomZonesWithVehicule(@Param("nomZones") Set<NomZone> nomZones);

    @Query("SELECT DISTINCT z FROM Zone z LEFT JOIN FETCH z.animals WHERE z.nomZone IN :nomZones")
    ArrayList<Zone> findByNomZonesWithAnimal(@Param("nomZones") Set<NomZone> nomZones);

    @Query("SELECT DISTINCT z FROM Zone z LEFT JOIN FETCH z.plante WHERE z.nomZone IN :nomZones")
    ArrayList<Zone> findByNomZonesWithPlant(@Param("nomZones") Set<NomZone> nomZones);

}
