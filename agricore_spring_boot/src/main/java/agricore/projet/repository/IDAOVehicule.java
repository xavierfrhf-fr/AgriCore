package agricore.projet.repository;

import agricore.projet.model.TypeVehicule;
import org.springframework.data.jpa.repository.JpaRepository;

import agricore.projet.model.Vehicule;

public interface IDAOVehicule extends JpaRepository<Vehicule, Integer> {
    boolean existsByTypeVehicule(TypeVehicule typeVehicule);

}
