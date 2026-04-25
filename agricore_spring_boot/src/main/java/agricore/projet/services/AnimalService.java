package agricore.projet.services;

import java.util.List;

import agricore.projet.dto.animal.request.AnimalRequest;
import agricore.projet.dto.animal.response.AnimalResponse;
import agricore.projet.exception.AnimalNotAllowedInZoneException;
import agricore.projet.exception.AnimalNotFoundException;
import agricore.projet.exception.ZoneNotFoundException;
import agricore.projet.model.Animal;
import agricore.projet.model.EspeceAnimal;
import agricore.projet.model.zone.NomZone;
import agricore.projet.model.zone.Zone;
import agricore.projet.repository.IDAOAnimal;
import agricore.projet.repository.IDAOZone;

public class AnimalService {

    IDAOAnimal daoAnimal;
    IDAOZone daoZone;

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

        Zone zone = daoZone
                .findById(animal.getZoneId())
                .orElseThrow(
                        ()-> new ZoneNotFoundException(animal.getZoneId())
                );

        if (isAnimalAllowedInZone(animal.getEspece(), zone.getNomZone())){
            a.setZone(zone);
        }

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

        Zone zone = daoZone
                .findById(animal.getZoneId())
                .orElseThrow(
                        ()-> new ZoneNotFoundException(animal.getZoneId())
                );

        if (isAnimalAllowedInZone(animal.getEspece(), zone.getNomZone())){
            a.setZone(zone);
        }

        a.setMale(animal.isMale());

        return AnimalResponse.convert(daoAnimal.save(a));
    }


    public void deleteById(Integer id) {

        daoAnimal.deleteById(id);
    }

    public boolean isAnimalAllowedInZone(EspeceAnimal espece, NomZone nomZone) {
        //Retourne true si l'animal à le droit d'être dans cette Zone
        if (!espece.getAllowedZone().equals(nomZone)){
            throw new AnimalNotAllowedInZoneException(nomZone, espece);
        }else{
            return true;
        }
    }

    public boolean isAnimalAllowedInZone(AnimalRequest animal) {
        //Surcharge potentiellement plus simple à utiliser (mais fait une 2eme requete SQL de la zone)
        return daoZone.findById(animal.getZoneId())
                .map(zone -> isAnimalAllowedInZone(animal.getEspece(), zone.getNomZone()))
                .orElseThrow(() -> new ZoneNotFoundException(animal.getZoneId()));
    }

}
