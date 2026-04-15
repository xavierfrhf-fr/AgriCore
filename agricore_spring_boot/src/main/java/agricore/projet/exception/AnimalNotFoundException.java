package agricore.projet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AnimalNotFoundException extends RuntimeException {

     public AnimalNotFoundException(Integer id) {
        super("L'animal numéro " + id + " est introuvable");
    }
}
