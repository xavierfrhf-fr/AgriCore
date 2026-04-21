package agricore.projet.services;

import java.util.List;

import org.springframework.stereotype.Service;

import agricore.projet.dto.animal.request.AnimalRequest;
import agricore.projet.dto.animal.response.AnimalResponse;
import agricore.projet.exception.AnimalNotFoundException;
import agricore.projet.model.Animal;
import agricore.projet.repository.IDAOAnimal;
import agricore.projet.repository.IDAOZone;

@Service
public class AnimalService {

    private final IDAOAnimal daoAnimal;
    private final IDAOZone daoZone;

    AnimalService(IDAOAnimal daoAnimal, IDAOZone daoZone) {
        this.daoAnimal = daoAnimal;
        this.daoZone = daoZone;
    }

    public List<AnimalResponse> findAll() {

        return daoAnimal.findAll()
                .stream()
                .map(AnimalResponse::convert)
                .toList();
    }

    public AnimalResponse findById(Integer id) {

        return AnimalResponse.convert(daoAnimal.findById(id).orElse(null));
    }

    public AnimalResponse insert(AnimalRequest animal) {

        Animal a = new Animal();

        a.setDateNaissance(animal.getDateNaissance());
        a.setDateVaccination(animal.getDateVaccination());
        a.setEspece(animal.getEspece());
        a.setZone(daoZone.findById(animal.getZoneId()).orElse(null));
        a.setMale(animal.isMale());

        return AnimalResponse.convert(daoAnimal.save(a));
    }

    public AnimalResponse update(Integer id, AnimalRequest animal) {

        Animal a = daoAnimal.findById(id).orElse(null);

        if (a == null) {
            throw new AnimalNotFoundException(id);
        }

        a.setDateNaissance(animal.getDateNaissance());
        a.setDateVaccination(animal.getDateVaccination());
        a.setEspece(animal.getEspece());
        a.setZone(daoZone.findById(animal.getZoneId()).orElse(null));
        a.setMale(animal.isMale());

        return AnimalResponse.convert(daoAnimal.save(a));
    }


    public void deleteById(Integer id) {

        daoAnimal.deleteById(id);
    }

}
