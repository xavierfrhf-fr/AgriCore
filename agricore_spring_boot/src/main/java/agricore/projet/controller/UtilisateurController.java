package agricore.projet.controller;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import agricore.projet.dto.utilisateur.AuthRequest;
import agricore.projet.dto.utilisateur.AuthResponse;
import agricore.projet.dto.utilisateur.TokenResponse;
import agricore.projet.dto.utilisateur.request.ClientRequestDTO;
import agricore.projet.model.Client;
import agricore.projet.repository.IDAOUtilisateur;
import agricore.projet.services.JwtUtils;

@RestController
@RequestMapping("/api") //peut etre appeler ce controlleur ConnexionController pour avoir que des controller de fermier/client/employe
public class UtilisateurController {
    

    private AuthenticationManager authenticationManager;
    private JwtUtils JwtUtils;
    private PasswordEncoder passwordEncoder;
    private IDAOUtilisateur daoUtilisateur;
    


	UtilisateurController(AuthenticationManager authenticationManager, JwtUtils JwtUtils, PasswordEncoder passwordEncoder, IDAOUtilisateur daoUtilisateur) {
        this.authenticationManager = authenticationManager;
        this.JwtUtils = JwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.daoUtilisateur = daoUtilisateur;
    }

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest request) throws AuthenticationException {
        

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword());

        var authenticated = authenticationManager.authenticate(authentication);
        String token = JwtUtils.generate(authenticated);

        // Récupérer le rôle depuis l'authentification
        //String role = authenticated.getAuthorities().stream()
        //    .findFirst()
        //    .map(auth -> auth.getAuthority())
        //    .orElse("CLIENT");

        return new AuthResponse(token,  request.getUsername());
    }

    @PostMapping("/auth/register")
    public String register(@RequestBody ClientRequestDTO entity) {
        
        Client user = new Client(); // ou Fermier selon logique

        user.setLogin(entity.getLogin());
        user.setPassword(passwordEncoder.encode(entity.getPassword()));
        user.setNom(entity.getNom());
        user.setPrenom(entity.getPrenom());
        user.setMail(entity.getEmail());


        daoUtilisateur.save(user);

       return "Client enregistré avec succès !";
    }
    
}