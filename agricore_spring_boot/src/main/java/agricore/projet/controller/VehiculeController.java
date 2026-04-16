package agricore.projet.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import agricore.projet.dto.vehicule.request.VehiculeRequestDTO;
import agricore.projet.dto.vehicule.response.VehiculeResponseDTO;
import agricore.projet.repository.IDAOVehicule;
import agricore.projet.services.VehiculeService;



@RestController
@RequestMapping("/api/vehicule")
public class VehiculeController {

  
    private final VehiculeService vehiculeService;

    VehiculeController(IDAOVehicule vehiculeRepository, VehiculeService vehiculeService) {
        this.vehiculeService = vehiculeService;
    }



    @GetMapping
    public List<VehiculeResponseDTO> getAll() {
        return vehiculeService.findAllDTO();
    }


    @GetMapping("/{id}")
    public VehiculeResponseDTO getVehiculeById(@PathVariable int id) {
        return vehiculeService.findByIdDTO(id);
    }

      @PutMapping("/{id}")
    public VehiculeResponseDTO modifier(@PathVariable Integer id, @RequestBody VehiculeRequestDTO vehiculeRequestDTO) {
        return vehiculeService.update(id, vehiculeRequestDTO);
    }
    

    @PostMapping
    public VehiculeResponseDTO ajouter(@RequestBody VehiculeRequestDTO vehiculeRequestDTO) {
        return vehiculeService.create(vehiculeRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteVehicule(@PathVariable Integer id) { 
        vehiculeService.delete(id);

    }
    
    

}
