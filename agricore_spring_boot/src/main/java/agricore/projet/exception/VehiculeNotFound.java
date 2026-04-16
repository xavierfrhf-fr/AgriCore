package agricore.projet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class VehiculeNotFound extends RuntimeException {
    
    public VehiculeNotFound(Integer id) {
        super("Le Vehicule avec l'id: " + id+ " est introuvable" );
    }
}