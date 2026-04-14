package agricore.projet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, JwtHeaderFilter jwtHeaderFilter) throws Exception {

        http.csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"));

        http.authorizeHttpRequests(auth -> {
            // L'ordre a une importance, on commence toujours par le plus spécifique vers le plus générale.

            //auth.requestMatchers("/matiere").permitAll(); //permet l'accès a /matiere pour tout le monde.

            //auth.requestMatchers("/matiere").hasRole("ADMIN"); 
            // auth.requestMatchers(HttpMethod.POST, "/matiere", "/utilisateur").hasRole("ADMIN"); ici on permet de faire de requete POST sur tel vu en fonction du ROLE

            // uniquement les utilisateur conencter mais n'amène pâs sur le formulaire de connexion désactivé par l'utilisation de filterChain : /** c'est pour partout sur l'application
            auth.requestMatchers("/api/auth").permitAll();
            auth.requestMatchers("/**").authenticated(); 

           

        });

        http.addFilterBefore(jwtHeaderFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean 
    PasswordEncoder passwordEncoder() {
       // return NoOpPasswordEncoder.getInstance(); Car retourne le mdp en clair donc pas bien !

       BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

       System.out.println(passwordEncoder.encode("test2"));
       return passwordEncoder;
    }
    
}

