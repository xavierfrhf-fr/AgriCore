package agricore.projet.exception;

import agricore.projet.model.NomZone;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidZoneException extends RuntimeException {
    public InvalidZoneException(String invalidZone) {
        super("Zone "+invalidZone+" n'existe pas. Valeurs attendues sont : "+ Arrays.toString(NomZone.values()));
    }
}
