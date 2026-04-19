package agricore.projet.services;

import java.util.List;

import org.springframework.stereotype.Service;

import agricore.projet.dto.utilisateur.request.FermierRequestDTO;
import agricore.projet.dto.utilisateur.response.FermierResponseDTO;
import agricore.projet.model.Fermier;
import agricore.projet.repository.IDAOUtilisateur;

@Service
public class FermierService {
	
	private final IDAOUtilisateur daoUtilisateur;

	public FermierService(IDAOUtilisateur daoutilisateur) {
		this.daoUtilisateur = daoutilisateur;
	}
	
	public List<FermierResponseDTO> getAll() {
		return daoUtilisateur.findAll()
                .stream()
                .map(FermierResponseDTO::convert)
                .toList();
	}

	public FermierResponseDTO getById(Integer id) {
		return daoUtilisateur.findById(id)
                .map(FermierResponseDTO::convert)  //je sais pas si on peut faire un cast
                .orElse(null);
	}

	public FermierResponseDTO create(FermierRequestDTO request) {
		Fermier c = new Fermier();
		c.setLogin(request.getLogin());
		c.setPassword(request.getPassword());
		return FermierResponseDTO.convert(daoUtilisateur.save(c));
	}

	public FermierResponseDTO patch(Integer id, FermierRequestDTO request) {
		// j'ai pas compris cette methode ?
		return null;
	}

	public FermierResponseDTO update(Integer id, FermierRequestDTO request) {
		Fermier c = (Fermier) daoUtilisateur.findById(id)
				.orElseThrow(() -> new RuntimeException("Fermier introuvable pour l'id " + id));
		c.setLogin(request.getLogin());
		c.setPassword(request.getPassword());
		return FermierResponseDTO.convert(daoUtilisateur.save(c));
	}

	public void deleteById(Integer id) {
		daoUtilisateur.findById(id)
        .orElseThrow(() -> new RuntimeException("Fermier introuvable pour l'id " + id));
daoUtilisateur.deleteById(id);
	}

}
