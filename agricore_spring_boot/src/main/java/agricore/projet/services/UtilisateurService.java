package agricore.projet.services;


import java.util.List;

import org.springframework.stereotype.Service;

import agricore.projet.dto.utilisateur.UtilisateurResponseDTO;
import agricore.projet.repository.IDAOUtilisateur;

@Service //à completer les methodes
public class UtilisateurService {
	
	private final IDAOUtilisateur daoUtilisateur;

	public List<UtilisateurResponseDTO> getAll() {
		return null;
	}

	public UtilisateurResponseDTO getById(Integer id) {
		return null;
	}

	public Object deleteById(Integer id) {
		return null;
	}

}
