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

import agricore.projet.dto.animal.request.AnimalRequest;
import agricore.projet.dto.animal.response.AnimalResponse;
import agricore.projet.repository.IDAOAnimal;
import agricore.projet.services.AnimalService;

@RestController
@RequestMapping("/api/animal")
public class AnimalController {

    AnimalService animalService;

    @GetMapping
    public List<AnimalResponse> getAll() {
        return animalService.findAll();
    }

    @GetMapping("/{id}")
    public AnimalResponse getAnimalById(@RequestParam int id) {
        return animalService.findById(id);
    }

    @PostMapping
    public AnimalResponse insert(@RequestBody AnimalRequest animalRequest) {
        return animalService.insert(animalRequest);
    }

    @PutMapping("/{id}")
    public AnimalResponse update(@PathVariable Integer id, @RequestBody AnimalRequest animalRequest) {
        return animalService.update(id, animalRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        animalService.deleteById(id);

    }

}
