package agricore.projet.services;

import java.util.List;

import org.springframework.stereotype.Service;

import agricore.projet.dto.utilisateur.request.EmployeRequestDTO;
import agricore.projet.dto.utilisateur.response.EmployeResponseDTO;
import agricore.projet.model.Employe;
import agricore.projet.repository.IDAOUtilisateur;

@Service
public class EmployeService {
	
	private final IDAOUtilisateur daoUtilisateur;

	public EmployeService(IDAOUtilisateur daoutilisateur) {
		this.daoUtilisateur = daoutilisateur;
	}
	
	public List<EmployeResponseDTO> getAll() {
		return daoUtilisateur.findAll()
                .stream()
                .map(EmployeResponseDTO::convert)
                .toList();
	}

	public EmployeResponseDTO getById(Integer id) {
		return daoUtilisateur.findById(id)
                .map(EmployeResponseDTO::convert)  //je sais pas si on peut faire un cast
                .orElse(null);
	}

	public EmployeResponseDTO create(EmployeRequestDTO request) {
		Employe e = new Employe();
		e.setLogin(request.getLogin());
		e.setPassword(request.getPassword());
		return EmployeResponseDTO.convert(daoUtilisateur.save(e));
	}

	public EmployeResponseDTO patch(Integer id, EmployeRequestDTO request) {
		// j'ai pas compris cette methode ?
		return null;
	}

	public EmployeResponseDTO update(Integer id, EmployeRequestDTO request) {
		Employe e = (Employe) daoUtilisateur.findById(id)
				.orElseThrow(() -> new RuntimeException("Employe introuvable pour l'id " + id));
		e.setLogin(request.getLogin());
		e.setPassword(request.getPassword());
		return EmployeResponseDTO.convert(daoUtilisateur.save(e));
	}

	public void deleteById(Integer id) {
		daoUtilisateur.findById(id)
        .orElseThrow(() -> new RuntimeException("Employe introuvable pour l'id " + id));
daoUtilisateur.deleteById(id);
	}

}
