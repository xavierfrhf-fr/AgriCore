package agricore.projet.repository;

import java.util.List;
import java.util.Optional;

import agricore.projet.model.Fermier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import agricore.projet.model.Employe;
import agricore.projet.model.Utilisateur;


public interface IDAOUtilisateur extends JpaRepository<Utilisateur, Integer> {

    Optional<Utilisateur> findByLogin(String login);
    
    @Query("SELECT f FROM Fermier f LEFT JOIN FETCH f.employe where f.id=:id")
    public Optional<Fermier> findFermierByIdWithEmploye(@Param ("id") Integer id);
    
    @Query("SELECT f FROM Fermier f LEFT JOIN FETCH f.zones where f.id=:id")
    public Optional<Fermier> findFermierByIdWithZone(@Param ("id") Integer id);

}
