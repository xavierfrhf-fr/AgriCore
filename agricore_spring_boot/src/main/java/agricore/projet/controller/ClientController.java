package agricore.projet.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import agricore.projet.dto.utilisateur.request.ClientRequestDTO;
import agricore.projet.dto.utilisateur.response.ClientResponseDTO;
import agricore.projet.services.ClientService;

@RestController
@RequestMapping("/api/client")
public class ClientController {
	
	private final ClientService clientService;
	
	public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<ClientResponseDTO> findAll() {
        return clientService.getAll();
    }

    @GetMapping("/{id}")
    public ClientResponseDTO findById(@PathVariable Integer id) {
        return clientService.getById(id);
    }

    @PostMapping
    public ClientResponseDTO create(@RequestBody ClientRequestDTO request) {
        return clientService.create(request);
    }

    @PatchMapping("/{id}")
    public ClientResponseDTO patch(@PathVariable Integer id, @RequestBody ClientRequestDTO request) {
        return clientService.patch(id, request);
    }

    @PutMapping("/{id}")
    public ClientResponseDTO update(@PathVariable Integer id, @RequestBody ClientRequestDTO request) {
        return clientService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        clientService.deleteById(id);
    }
	

}
