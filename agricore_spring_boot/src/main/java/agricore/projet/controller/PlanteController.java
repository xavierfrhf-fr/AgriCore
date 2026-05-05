package agricore.projet.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
/*	@GetMapping
	public List<PlanteResponseDTO> getAll(){
		return planteService.findAll();
	}*/
	
	
}
