package agricore.projet.services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import agricore.projet.dto.utilisateur.request.EmployeRequestDTO;
import agricore.projet.dto.utilisateur.response.EmployeResponseDTO;
import agricore.projet.exception.UtilisateurNotFoundException;
import agricore.projet.model.Employe;
import agricore.projet.model.Fermier;
import agricore.projet.repository.IDAOUtilisateur;

@Service
public class EmployeService {
	
	private final IDAOUtilisateur daoUtilisateur;
	private final PasswordEncoder passwordEncoder;
	
	public EmployeService(IDAOUtilisateur daoUtilisateur, PasswordEncoder passwordEncoder) {
		this.daoUtilisateur = daoUtilisateur;
		this.passwordEncoder = passwordEncoder;
	}

	public List<EmployeResponseDTO> getAll() {
		return daoUtilisateur.findAll()
                .stream()
                .filter(user->user instanceof Employe)
                .map(employe->(Employe) employe)
                .map(EmployeResponseDTO::convert)
                .toList();
	}

	public EmployeResponseDTO getById(Integer id) {
		return daoUtilisateur.findById(id)
				.filter(user->user instanceof Employe)
                .map(employe->(Employe) employe)
                .map(EmployeResponseDTO::convert)
                .orElseThrow(() -> new UtilisateurNotFoundException(id));
	}

	public EmployeResponseDTO create(EmployeRequestDTO request) {
		Employe e = new Employe();
		e.setLogin(request.getLogin());
		e.setPassword(passwordEncoder.encode(request.getPassword()));  //on ne stock pas en clair les mdp dans la bbd
		return EmployeResponseDTO.convert(daoUtilisateur.save(e));
	}


	public EmployeResponseDTO update(Integer id, EmployeRequestDTO request) {
		Employe e = (Employe) daoUtilisateur.findById(id)
				.orElseThrow(() -> new RuntimeException("Employe introuvable pour l'id " + id));
		e.setLogin(request.getLogin());
		e.setPassword(passwordEncoder.encode(request.getPassword()));  //on ne stock pas en clair les mdp dans la bbd
		return EmployeResponseDTO.convert(daoUtilisateur.save(e));
	}

	public void deleteById(Integer id) {
		daoUtilisateur.findById(id)
        .orElseThrow(() -> new RuntimeException("Employe introuvable pour l'id " + id));
daoUtilisateur.deleteById(id);
	}

}
