package agricore.projet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import agricore.projet.model.animal.Animal;

public interface IDAOAnimal extends JpaRepository<Animal, Integer> {

}
