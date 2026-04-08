package agricore.projet.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import agricore.projet.model.Vehicule;
import agricore.projet.repository.VehiculeRepository;
import org.springframework.web.bind.annotation.RequestParam;





@RestController
@RequestMapping("/api/vehicule")
public class VehiculeController {

    VehiculeRepository vehiculeRepository;

    VehiculeController(VehiculeRepository vehiculeRepository) {
        this.vehiculeRepository = vehiculeRepository;
    }   

    @GetMapping
    public List<Vehicule> ChercherVehicule() {
        return vehiculeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Vehicule getVehiculeById(@RequestParam int id) {
        return vehiculeRepository.findById(id).orElse(null);
    }
    

    @PostMapping
    public String postMethodName(@RequestBody String entity) {
        //TODO: process POST request
        
        return entity;
    }

    @PutMapping("/{id}")
    public String putMethodName(@PathVariable String id, @RequestBody String entity) {
        //TODO: process PUT request
        
        return entity;
    }

    @DeleteMapping("/{id}")
    public void deleteMethodName(@PathVariable String id) { 
    }
    
    

}
