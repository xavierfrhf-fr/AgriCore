package agricore.projet.exception;

import agricore.projet.model.ressource.NomRessource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RessourceNotFoundException extends RuntimeException {

    public RessourceNotFoundException(Integer id) {
        super("La ressource avec l'id : " + id + " est introuvable");
    }

    public RessourceNotFoundException(NomRessource resource) {
        super("La ressource : " + resource + " est introuvable");
    }
}
