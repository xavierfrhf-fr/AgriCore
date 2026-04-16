package agricore.projet.services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import agricore.projet.model.Client;
import agricore.projet.model.Employe;
import agricore.projet.model.Utilisateur;
import agricore.projet.model.Fermier;
import agricore.projet.repository.IDAOUtilisateur;

@Service // Scanner par spring boot grace @SpringBootApplication qui contient @ComponentScan dans le FormationSpringBootSecurityApplication 
public class JpaUserDetailsService implements UserDetailsService { //Spring créer un objet JpaUserDetailsService et ne charge pas celui par defaut dans security
    
    //@Autowired // Spring scann Autowired  fait par le constructeur de JpaUserDetailsService et injecte un objet IDAOUtilisateur (qui est un proxy de l'interface IDAOUtilisateur)
    IDAOUtilisateur daoUtilisateur; 

    public JpaUserDetailsService(IDAOUtilisateur daoUtilisateur) {
        this.daoUtilisateur = daoUtilisateur;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        
        Utilisateur user = daoUtilisateur.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("L'utilisateur n'existe pas"));


        
        String role;

        if (user instanceof Fermier) {
            role = "FERMIER";
        } else if (user instanceof Employe) {
            role = "EMPLOYE";
        } else if (user instanceof Client) {
            role = "CLIENT";
        } else {
            throw new RuntimeException("Unknown user type");
        }

        UserDetails us = User.builder()
            .username(user.getLogin())
            .password(user.getPassword())
            .roles(role)
            .build();

        return us;

    }

  

}
