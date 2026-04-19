package agricore.projet.services;

import java.util.List;

import org.springframework.stereotype.Service;
import agricore.projet.dto.utilisateur.request.ClientRequestDTO;
import agricore.projet.dto.utilisateur.response.ClientResponseDTO;
import agricore.projet.model.Client;
import agricore.projet.repository.IDAOUtilisateur;

@Service
public class ClientService {
	private final IDAOUtilisateur daoUtilisateur;

	public ClientService(IDAOUtilisateur daoutilisateur) {
		this.daoUtilisateur = daoutilisateur;
	}

	public List<ClientResponseDTO> getAll() {
		return daoUtilisateur.findAll()
                .stream()
                .map(ClientResponseDTO::convert)
                .toList();
	}

	public ClientResponseDTO getById(Integer id) {
		return daoUtilisateur.findById(id)
                .map(ClientResponseDTO::convert)  //je sais pas si on peut faire un cast
                .orElse(null);
	}

	public ClientResponseDTO create(ClientRequestDTO request) {
		Client c = new Client();
		c.setLogin(request.getLogin());
		c.setPassword(request.getPassword());
		return ClientResponseDTO.convert(daoUtilisateur.save(c));
	}

	public ClientResponseDTO patch(Integer id, ClientRequestDTO request) {
		// j'ai pas compris cette methode ?
		return null;
	}

	public ClientResponseDTO update(Integer id, ClientRequestDTO request) {
		Client c = (Client) daoUtilisateur.findById(id)
				.orElseThrow(() -> new RuntimeException("Client introuvable pour l'id " + id));
		c.setLogin(request.getLogin());
		c.setPassword(request.getPassword());
		return ClientResponseDTO.convert(daoUtilisateur.save(c));
	}

	public void deleteById(Integer id) {
		daoUtilisateur.findById(id)
        .orElseThrow(() -> new RuntimeException("Client introuvable pour l'id " + id));
daoUtilisateur.deleteById(id);
	}
}
