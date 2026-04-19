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

import agricore.projet.dto.utilisateur.request.FermierRequestDTO;
import agricore.projet.dto.utilisateur.response.FermierResponseDTO;
import agricore.projet.model.Employe;
import agricore.projet.model.Zone;
import agricore.projet.services.EmployeService;
import agricore.projet.services.FermierService;
import agricore.projet.services.ZoneService;

@RestController
@RequestMapping("/api/fermier")
public class FermierController {


	private final FermierService fermierService;
	private final EmployeService employeService;
	private final ZoneService zoneService;

	public FermierController(FermierService fermierService, EmployeService employeService, ZoneService zoneService) {
		this.fermierService = fermierService;
		this.employeService = employeService;
		this.zoneService = zoneService;
	}

	@GetMapping
	public List<FermierResponseDTO> findAll() {
		return fermierService.getAll();
	}

	@GetMapping("/{id}")
	public FermierResponseDTO findById(@PathVariable Integer id) {
		return fermierService.getById(id);
	}
	
	// est ce qu'on mettrait pas 2 methodes FermierWithEmploye et FermierWithZone ?

	@GetMapping
	public List<Employe>findFermierWithEmploye(@PathVariable Integer id){
		return (List<Employe>) employeService.getById(id);
	}
	
	@GetMapping
	public List<Zone>findFermierWithZone(@PathVariable Integer id){
		return (List<Zone>) zoneService.getZoneById(id);
	}
	
	@PostMapping
	public FermierResponseDTO create(@RequestBody FermierRequestDTO request) {
		return fermierService.create(request);
	}

	@PatchMapping("/{id}")
	public FermierResponseDTO patch(@PathVariable Integer id, @RequestBody FermierRequestDTO request) {
		return fermierService.patch(id, request);
	}

	@PutMapping("/{id}")
	public FermierResponseDTO update(@PathVariable Integer id, @RequestBody FermierRequestDTO request) {
		return fermierService.update(id, request);
	}

	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Integer id) {
		fermierService.deleteById(id);
	}



}
