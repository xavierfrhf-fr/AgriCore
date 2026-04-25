package agricore.projet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UniqueZoneAlreadyExistException extends RuntimeException {
    public UniqueZoneAlreadyExistException(String message) {
        super(message);
    }
}
