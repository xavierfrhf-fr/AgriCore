package agricore.projet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import agricore.projet.model.Vehicule;

public interface VehiculeRepository extends JpaRepository<Vehicule, Integer> {

}
