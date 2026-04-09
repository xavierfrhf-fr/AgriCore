package agricore.projet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ZoneNotFoundException extends RuntimeException {

    public ZoneNotFoundException(Integer id) {
        super("La zone avec l'id : " + id + " est introuvable");
    }
}
