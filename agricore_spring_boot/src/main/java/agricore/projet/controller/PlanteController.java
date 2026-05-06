package agricore.projet.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import agricore.projet.dto.plante.request.PlanteRequestDTO;
import agricore.projet.dto.plante.response.PlanteResponseDTO;
import agricore.projet.services.PlanteService;
//recoit la requete http et appelle le service
@RestController
@RequestMapping("/api/plante")
public class PlanteController {
	private final PlanteService planteService;
	
	public PlanteController(PlanteService planteService) {
		this.planteService = planteService;
	}
	//on recoit un url api/plante en mode get, on veut la liste des toutes les plantes
	//on appelle la methode findAll qui est dans le service

	@GetMapping
	public List<PlanteResponseDTO> getAll(){
		return planteService.findAll();
	}
	
	@GetMapping("/{id}")
    public PlanteResponseDTO getPlanteById(@RequestParam int id) {
        return planteService.findById(id);
    }

    @PostMapping
    public PlanteResponseDTO insert(@RequestBody PlanteRequestDTO planteRequest) {
        return planteService.insert(planteRequest);
    }

    @PutMapping("/{id}")
    public PlanteResponseDTO update(@PathVariable Integer id, @RequestBody PlanteRequestDTO planteRequest) {
        return planteService.update(id, planteRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        planteService.deleteById(id);

    }
	
}
