package agricore.projet.repository;

import agricore.projet.model.ressource.NomRessource;
import org.springframework.data.jpa.repository.JpaRepository;
import agricore.projet.model.ressource.Ressource;

import java.util.Optional;

public interface IDAORessource extends JpaRepository<Ressource, Integer> {
    Optional<Ressource> findByNom(NomRessource nomRessource);
}
