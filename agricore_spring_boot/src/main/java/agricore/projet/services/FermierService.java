package agricore.projet.services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import agricore.projet.dto.utilisateur.request.FermierRequestDTO;
import agricore.projet.dto.utilisateur.response.FermierResponseDTO;
import agricore.projet.dto.utilisateur.response.FermierWithEmployeResponseDTO;
import agricore.projet.dto.utilisateur.response.FermierWithZoneResponseDTO;
import agricore.projet.exception.UtilisateurNotFoundException;
import agricore.projet.model.Fermier;
import agricore.projet.repository.IDAOUtilisateur;

@Service
public class FermierService {
	
	private final IDAOUtilisateur daoUtilisateur;
	private final PasswordEncoder passwordEncoder;

	
	public FermierService(IDAOUtilisateur daoUtilisateur, PasswordEncoder passwordEncoder) {
		this.daoUtilisateur = daoUtilisateur;
		this.passwordEncoder = passwordEncoder;
	}

	public List<FermierResponseDTO> getAll() {
		return daoUtilisateur.findAll()
                .stream()
                .filter(user->user instanceof Fermier)
                .map(fermier->(Fermier) fermier)
                .map(FermierResponseDTO::convert)
                .toList();
	}

	public FermierResponseDTO getById(Integer id) {
		return daoUtilisateur.findById(id)
				.filter(user->user instanceof Fermier)
                .map(fermier->(Fermier) fermier)
                .map(FermierResponseDTO::convert)
                .orElseThrow(() -> new UtilisateurNotFoundException(id));
	}

	public FermierResponseDTO create(FermierRequestDTO request) {
		Fermier c = new Fermier();
		c.setLogin(request.getLogin());
		c.setPassword(passwordEncoder.encode(request.getPassword()));  //on ne stock pas en clair les mdp dans la bbd
		c.setNom(request.getNom());
		c.setPrenom(request.getPrenom());
		return FermierResponseDTO.convert(daoUtilisateur.save(c));
	}

	public FermierResponseDTO update(Integer id, FermierRequestDTO request) {
		Fermier c = (Fermier) daoUtilisateur.findById(id)
				.orElseThrow(() -> new RuntimeException("Fermier introuvable pour l'id " + id));
		c.setLogin(request.getLogin());
		c.setPassword(passwordEncoder.encode(request.getPassword()));  //on ne stock pas en clair les mdp dans la bbd
		c.setNom(request.getNom());
		c.setPrenom(request.getPrenom());
		return FermierResponseDTO.convert(daoUtilisateur.save(c));
	}

	public void deleteById(Integer id) {
		daoUtilisateur.findById(id)
        .orElseThrow(() -> new RuntimeException("Fermier introuvable pour l'id " + id));
daoUtilisateur.deleteById(id);
	}
	
	public FermierWithEmployeResponseDTO getFermierWithEmploye(int id) {
		return daoUtilisateur
				.findFermierByIdWithEmploye(id)
				.map(FermierWithEmployeResponseDTO::convert)
				.orElseThrow(() -> new RuntimeException("Fermier introuvable pour l'id " + id));
		
	} 
	
	public FermierWithZoneResponseDTO getFermierWithZone(int id) {
		return daoUtilisateur
				.findFermierByIdWithZone(id)
				.map(FermierWithZoneResponseDTO::convert)
				.orElseThrow(() -> new RuntimeException("Zone introuvable pour l'id " + id));
		
	}
	

}
