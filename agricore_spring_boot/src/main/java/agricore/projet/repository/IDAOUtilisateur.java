package agricore.projet.repository;

import agricore.projet.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDAOUtilisateur extends JpaRepository<Utilisateur, Integer> {

    Utilisateur findByLogin(String login);

}
