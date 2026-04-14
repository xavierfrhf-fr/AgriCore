package agricore.projet.repository;

import agricore.projet.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IDAOUtilisateur extends JpaRepository<Utilisateur, Integer> {

    Optional<Utilisateur> findByLogin(String login);

}
