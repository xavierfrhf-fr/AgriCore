package agricore.projet.controller;

import java.util.List;

import agricore.projet.dto.ressource.request.TransformationRequestDTO;
import agricore.projet.dto.ressource.response.TransformationResponseDTO;
import agricore.projet.services.TransformationService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import agricore.projet.dto.ressource.request.RessourceRequestDTO;
import agricore.projet.dto.ressource.response.RessourceResponseDTO;
import agricore.projet.services.RessourceService;

@RestController
@RequestMapping("/api/ressource")
public class RessourceController {
    private final RessourceService ressourceService;
    private final TransformationService transformationService;

    public RessourceController(RessourceService ressourceService,  TransformationService transformationService) {
        this.ressourceService = ressourceService;
        this.transformationService = transformationService;
    }

    @GetMapping
    public List<RessourceResponseDTO> findAll() {
        return ressourceService.getAll();
    }

    @GetMapping("/{id}")
    public RessourceResponseDTO findById(@PathVariable Integer id) {
        return ressourceService.getById(id);
    }

    @PostMapping
    public RessourceResponseDTO create(@RequestBody RessourceRequestDTO request) {
        return ressourceService.create(request);
    }

    @PatchMapping("/{id}")
    public RessourceResponseDTO patch(@PathVariable Integer id, @RequestBody RessourceRequestDTO request) {
        return ressourceService.patch(id, request);
    }

    @PutMapping("/{id}")
    public RessourceResponseDTO update(@PathVariable Integer id, @RequestBody RessourceRequestDTO request) {
        return ressourceService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        ressourceService.deleteById(id);
    }

    @PostMapping("/transformation")
    public TransformationResponseDTO transformation(@RequestBody TransformationRequestDTO request) {
        return transformationService.performTransformation(request);
    }
}
