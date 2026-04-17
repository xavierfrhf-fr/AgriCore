package agricore.projet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import agricore.projet.model.Vehicule;

public interface IDAOVehicule extends JpaRepository<Vehicule, Integer> {

}
