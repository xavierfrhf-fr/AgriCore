package agricore.projet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UtilisateurNotFoundException extends RuntimeException{

	public UtilisateurNotFoundException(Integer id) {
		super("L'utilisateur avec l'id : " + id + " est introuvable");
	}

	

}
