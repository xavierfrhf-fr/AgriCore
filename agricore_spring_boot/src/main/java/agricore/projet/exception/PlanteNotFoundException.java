package agricore.projet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PlanteNotFoundException extends RuntimeException{
    public PlanteNotFoundException(Integer id) {
        super("La plante " + id + " est introuvable");
    }
}
