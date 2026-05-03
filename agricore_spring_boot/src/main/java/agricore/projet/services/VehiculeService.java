package agricore.projet.services;

import java.util.List;

import org.springframework.stereotype.Service;

import agricore.projet.dto.vehicule.request.VehiculeRequestDTO;
import agricore.projet.dto.vehicule.response.VehiculeResponseDTO;
import agricore.projet.exception.VehiculeNotFound;
import agricore.projet.exception.ZoneNotFoundException;
import agricore.projet.model.Animal;
import agricore.projet.model.Plante;
import agricore.projet.model.Vehicule;
import agricore.projet.model.ressource.NomRessource;
import agricore.projet.model.ressource.Ressource;
import agricore.projet.model.zone.Zone;
import agricore.projet.repository.IDAOZone;
import agricore.projet.repository.IDAOVehicule;

@Service
public class VehiculeService {

    private final IDAOVehicule daovehicule;
    private final IDAOZone daoZone;

    public VehiculeService( IDAOVehicule daovehicule, IDAOZone daoZone) {
        this.daovehicule = daovehicule;
        this.daoZone = daoZone;
        
    }

    //Recolter plante et animal 

    public String recolterPLante(Plante plante, Vehicule vehicule, int distanceKm) {

        if (plante.getVehiculeRequis() != vehicule.getTypeVehicule() ) {
            throw new RuntimeException("Pas le bon véhicule");
        }

        vehicule.consommerCarburant(distanceKm);

        return "Le véhicule est aller chercher la récolte ! ";

    }

    public String acheterAnimal(Animal animal, Vehicule vehicule, int distanceKm) {

        if (animal.getVehiculeAchatRequis() != vehicule.getTypeVehicule()) {
             throw new RuntimeException("Pas le bon véhicule");
        }

        vehicule.consommerCarburant(distanceKm);

        return "Le véhicule est aller chercher l'animal ! ";
    }

    public void fairePlein(Vehicule vehicule) {

        //Essence stocké dans la zone 
        Ressource carburant = vehicule.getZone().getRessources(NomRessource.ESSENCE);

        // qtt carburant manquant dans le véhicule 
        int manque = vehicule.getCapaciteReservoir() - vehicule.getCarburantActuel();

        // set qtt stocker carburant zone 
        carburant.setQuantite(carburant.getQuantite() - manque);

        // set gtt carburant du vehicule => faire plein 
        vehicule.setCarburantActuel(vehicule.getCapaciteReservoir());


    }



    //Controller

    public VehiculeResponseDTO findByIdDTO(Integer id) {
        return VehiculeResponseDTO.convert(daovehicule.findById(id).orElseThrow(() -> new VehiculeNotFound(id)));  //Throw(() -> new VehiculeN);
    }

    public List<VehiculeResponseDTO> findAllDTO() {
        List<Vehicule> vehicules = daovehicule.findAll();
        
        if (vehicules.isEmpty()) {
            throw new VehiculeNotFound();
        }

    return daovehicule.findAll()
            .stream()
            .map(VehiculeResponseDTO::convert)
            .toList();
}

    public VehiculeResponseDTO create(VehiculeRequestDTO vehiculeRequestDTO) {
        
        Vehicule v = new Vehicule();
        v.setTypeVehicule(vehiculeRequestDTO.getTypeVehicule());
        v.setDateControleTech(vehiculeRequestDTO.getDateControleTech());

        Zone z = daoZone.findById(vehiculeRequestDTO.getZoneid()).orElseThrow(() -> new ZoneNotFoundException(vehiculeRequestDTO.getZoneid()));

        v.setZone(z);

        Vehicule tosave = daovehicule.save(v);
        
        return VehiculeResponseDTO.convert(tosave);
    }


    public VehiculeResponseDTO update(Integer id, VehiculeRequestDTO vehiculeRequestDTO) {
        
        //find entity
        Vehicule v = daovehicule.findById(id).orElseThrow(() -> new VehiculeNotFound(id));

        //maj entity
        v.setTypeVehicule(vehiculeRequestDTO.getTypeVehicule());
        v.setDateControleTech(vehiculeRequestDTO.getDateControleTech());


        //set zone
        Zone z = daoZone.findById(vehiculeRequestDTO.getZoneid()).orElseThrow(() -> new ZoneNotFoundException(vehiculeRequestDTO.getZoneid()));
        v.setZone(z);

        //save entity
        Vehicule update = daovehicule.save(v);

        return VehiculeResponseDTO.convert(update);


    }

    public void delete(Integer id) {

        if(!daovehicule.existsById(id)) {
            throw new VehiculeNotFound(id);
        }

        daovehicule.deleteById(id);
        
    }

    


}
