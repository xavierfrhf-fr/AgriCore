package agricore.projet.repository;

import agricore.projet.model.Employe;
import agricore.projet.model.Utilisateur;
import agricore.projet.model.Zone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IDAOUtilisateur extends JpaRepository<Utilisateur, Integer> {

    Optional<Utilisateur> findByLogin(String login); //je sais pas ce que j'en fais de ça
    
    @Query("SELECT f FROM Fermier f LEFT JOIN FETCH f.employe where f.id=id ")
    public List<Employe>FermierWithEmploye();
    
    @Query("SELECT f FROM Fermier f LEFT JOIN FETCH f.zone where f.id=id")
    public List<Zone>FermierWithZone();

}
