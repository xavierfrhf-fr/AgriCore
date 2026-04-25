package agricore.projet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import agricore.projet.model.ressource.Ressource;

public interface IDAORessource extends JpaRepository<Ressource, Integer> {}
