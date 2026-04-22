package agricore.projet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import agricore.projet.model.Employe;
import agricore.projet.model.Utilisateur;
import agricore.projet.model.Zone;

public interface IDAOUtilisateur extends JpaRepository<Utilisateur, Integer> {

    Optional<Utilisateur> findByLogin(String login); //je sais pas ce que j'en fais de ça
    
    @Query("SELECT f FROM Fermier f LEFT JOIN FETCH f.employe where f.id=:id")
    public List<Employe>FermierWithEmploye(@Param ("id") Integer id);
    
    @Query("SELECT f FROM Fermier f LEFT JOIN FETCH f.zone where f.id=:id")
    public List<Zone>FermierWithZone(@Param ("id") Integer id);

}
