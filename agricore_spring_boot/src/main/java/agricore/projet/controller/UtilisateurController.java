package agricore.projet.controller;


import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import agricore.projet.dto.utilisateur.AuthRequest;
import agricore.projet.dto.utilisateur.TokenResponse;
import agricore.projet.dto.utilisateur.UtilisateurResponseDTO;
import agricore.projet.model.Client;
import agricore.projet.repository.IDAOUtilisateur;
import agricore.projet.services.JwtUtils;
import agricore.projet.services.UtilisateurService;

@RestController
@RequestMapping("/api") //peut etre appeler ce controlleur ConnexionController pour avoir que des controller de fermier/client/employe
public class UtilisateurController {
    

    private AuthenticationManager authenticationManager;
    private JwtUtils JwtUtils;
    private PasswordEncoder passwordEncoder;
    private IDAOUtilisateur daoUtilisateur;
    
    private final UtilisateurService utilisateurService;
    
    
    public UtilisateurController(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}

	UtilisateurController(AuthenticationManager authenticationManager, JwtUtils JwtUtils, PasswordEncoder passwordEncoder, IDAOUtilisateur daoUtilisateur) {
        this.authenticationManager = authenticationManager;
        this.JwtUtils = JwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.daoUtilisateur = daoUtilisateur;
    }

    @PostMapping("/auth")
    public TokenResponse auth(@RequestBody AuthRequest request) throws AuthenticationException {
        System.out.println("ok");
        // On authentifie l'utilisateur ...

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword());

       

        return new TokenResponse(JwtUtils.generate(authenticationManager.authenticate(authentication)));
    }

    @PostMapping("/auth/register")
    public String register(@RequestBody AuthRequest entity) {
        
        Client user = new Client(); // ou Fermier selon logique

        user.setLogin(entity.getUsername());
        user.setPassword(passwordEncoder.encode(entity.getPassword()));

        daoUtilisateur.save(user);

       return "Client enregistré avec succès !";
    }
    
}