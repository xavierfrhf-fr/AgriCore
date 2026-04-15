package agricore.projet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import agricore.projet.model.Plante;

public interface IDAOPlante extends JpaRepository<Plante, Integer>{

}
