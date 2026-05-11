package agricore.projet.services;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import agricore.projet.dto.animal.request.CreateAnimalRequest;
import agricore.projet.dto.animal.request.UpdateAnimalRequest;
import agricore.projet.dto.animal.response.AnimalResponse;
import agricore.projet.exception.AnimalNotAllowedInZoneException;
import agricore.projet.exception.AnimalNotFoundException;
import agricore.projet.exception.ZoneNotFoundException;
import agricore.projet.model.animal.Animal;
import agricore.projet.model.animal.EspeceAnimal;
import agricore.projet.model.zone.NomZone;
import agricore.projet.model.zone.Zone;
import agricore.projet.repository.IDAOAnimal;
import agricore.projet.repository.IDAOZone;

@Service
public class AnimalService {

    private final IDAOAnimal daoAnimal;
    private final IDAOZone daoZone;
    private final TransformationService transformationService;
    private final RessourceService ressourceService;

    AnimalService(IDAOAnimal daoAnimal, IDAOZone daoZone, TransformationService transformationService,  RessourceService ressourceService) {
        this.daoAnimal = daoAnimal;
        this.daoZone = daoZone;
        this.transformationService = transformationService;
        this.ressourceService = ressourceService;
    }

    public List<AnimalResponse> findAll() {
        return daoAnimal.findAll()
                .stream()
                .peek(this::produceRessource)
                .map(AnimalResponse::convert)
                .toList();
    }

    public AnimalResponse findById(Integer id) {

        return daoAnimal.findById(id)
                .map(animal -> {
                    this.produceRessource(animal);
                    return AnimalResponse.convert(animal);
                })
                .orElseThrow(() -> new AnimalNotFoundException(id));
    }

    public AnimalResponse insert(CreateAnimalRequest animal) {

        Animal a = new Animal();

        a.setDateNaissance(animal.getDateNaissance());
        a.setDateVaccination(animal.getDateVaccination());
        a.setEspece(animal.getEspece());
        a.initializeProduction();

        Zone zone = daoZone
                .findByName(a.getEspece().getAllowedZone())
                .stream()
                .findFirst()
                .orElseThrow(() -> new ZoneNotFoundException(a.getEspece().getAllowedZone()));

        a.setZone(zone);
        a.setMale(animal.isMale());

        return AnimalResponse.convert(daoAnimal.save(a));
    }

    public AnimalResponse update(Integer id, UpdateAnimalRequest animal) {

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
                        () -> new ZoneNotFoundException(animal.getZoneId()));

        if (isAnimalAllowedInZone(animal.getEspece(), zone.getNomZone())) {
            a.setZone(zone);
        }

        a.setMale(animal.isMale());

        return AnimalResponse.convert(daoAnimal.save(a));
    }

    public void deleteById(Integer id) {

        daoAnimal.deleteById(id);
    }

    public boolean isAnimalAllowedInZone(EspeceAnimal espece, NomZone nomZone) {
        // Retourne true si l'animal à le droit d'être dans cette Zone
        if (!espece.getAllowedZone().equals(nomZone)) {
            throw new AnimalNotAllowedInZoneException(nomZone, espece);
        } else {
            return true;
        }
    }

    public boolean isAnimalAllowedInZone(UpdateAnimalRequest animal) {
        // Surcharge potentiellement plus simple à utiliser (mais fait une 2eme requete
        // SQL de la zone)
        return daoZone.findById(animal.getZoneId())
                .map(zone -> isAnimalAllowedInZone(animal.getEspece(), zone.getNomZone()))
                .orElseThrow(() -> new ZoneNotFoundException(animal.getZoneId()));
    }

    public void produceRessource(Animal animal){
        if (!animal.isProducer()){
            //System.out.println("Animal "+animal.getEspece().name()+" is not a producer");
            //System.out.println(animal.getEspece().getDimorphisme().getProductionTime(true));
            return;
        }

        //System.out.println("Animal "+animal.getEspece().name()+" is producer of "+animal.getNomRessource());


        if (!ressourceService.ressourceAlreadyExists(animal.getNomRessource())) {
            //System.out.println("Ressource "+animal.getNomRessource()+" doesn't exist, animal: "+animal.getEspece().name()+" cannot produce");
            try{
                transformationService.createRessourceIfNotExist(animal.getNomRessource());
            } catch (ZoneNotFoundException e){
                //System.out.println("Zone manquante pour production de "+animal.getEspece().name());
                return;
            }
        }
        //System.out.println("Ressource "+animal.getNomRessource()+" produced");

        int qty = animal.produceRessource();
        try{
            animal.setTotalProduit(animal.getTotalProduit()+qty);
        }catch (NullPointerException e){
            if (qty>0){
                animal.setTotalProduit(qty);
            }
        }

        transformationService.changeQuantity(animal.getNomRessource(), qty);
        daoAnimal.save(animal);
    }

}
