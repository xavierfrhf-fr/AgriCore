package agricore.projet.services;

import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtils {
    
    @Value("${JWT_KEY}")
    private String JWT_KEY;

    @Value("${JWT_DURATION}")
    private String JWT_DURATION;

    private JwtUtils() { }

    public String generate(Authentication auth) {
        
        Date now = new Date();
        SecretKey secretKey = Keys.hmacShaKeyFor(JWT_KEY.getBytes());

        // Si la connexion est OK, on génère un jeton JWT
        return Jwts.builder()
            .subject(auth.getName()) // Souvent, c'est le username ici
            .issuedAt(now)
            .expiration(new Date(now.getTime() + Long.parseLong(JWT_DURATION) )) // Durée de validité = 1 heure
            .signWith(secretKey)
            .compact() // Le jeton JWT sous forme de String
        ;
    }

    public Optional<String> validate(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(JWT_KEY.getBytes());

        try {
            return Optional.of(Jwts.parser()
                    .verifyWith(secretKey) // On donne la clé pour valider le jeton
                    .build()
                    .parseSignedClaims(token) // On donne le jeton à valider
                    .getPayload() // Le contenu du jeton
                    .getSubject() // Le nom d'utilisateur
            );
        }

        catch (Exception ex) {
            return Optional.empty();
        }
    }

   


}