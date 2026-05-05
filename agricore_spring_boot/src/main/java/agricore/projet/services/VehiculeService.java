package agricore.projet.services;

import java.util.List;

import org.springframework.stereotype.Service;

import agricore.projet.dto.vehicule.request.VehiculeRequestDTO;
import agricore.projet.dto.vehicule.response.VehiculeResponseDTO;
import agricore.projet.exception.VehiculeNotFound;
import agricore.projet.exception.ZoneNotFoundException;
import agricore.projet.model.Plante;
import agricore.projet.model.Vehicule;
import agricore.projet.model.animal.Animal;
import agricore.projet.model.ressource.Ressource;
import agricore.projet.model.zone.Zone;
import agricore.projet.repository.IDAORessource;
import agricore.projet.repository.IDAOVehicule;
import agricore.projet.repository.IDAOZone;

@Service
public class VehiculeService {

    private final IDAOVehicule daovehicule;
    private final IDAOZone daoZone;
    private final IDAORessource daoRessource;
 
    private final TransformationService transformationService;

    public VehiculeService( IDAOVehicule daovehicule, 
        IDAOZone daoZone,
        IDAORessource daoRessource,
        TransformationService transformationService) {
        
            this.daovehicule = daovehicule;
        this.daoZone = daoZone;
        this.daoRessource= daoRessource;
        this.transformationService = transformationService;
        
    }

    public boolean besoinCarburant(Vehicule vehicule, int distanceKm) {
		return vehicule.getCarburantActuel() >= distanceKm * vehicule.getTypeVehicule().getConsoParKm();
	}

	public void consommerCarburant(Vehicule vehicule, int distanceKm) {

		int carburantNecessaire = distanceKm * vehicule.getTypeVehicule().getConsoParKm();

		if (carburantNecessaire > vehicule.getCarburantActuel()) {

			throw new RuntimeException("Pas assez de carburant");
			
		}

		vehicule.setCarburantActuel( vehicule.getCarburantActuel() - carburantNecessaire);

	}

    public void fairePlein(Vehicule vehicule, Ressource carburant) {
    //on modifie la logique du plein et on peut le faire qu'importe la position du véhicule. on récupère juste la ressource 
    

    // qtt carburant manquant dans le véhicule 
    int manque =  vehicule.getTypeVehicule().getCapaciteReservoir() - vehicule.getCarburantActuel();

    if (carburant.getQuantite() < manque) {
        throw new RuntimeException("Pas assez de carburant en stock pour faire le plein");
    }

    // set qtt stocker carburant
    daoRessource
            .findByNom(carburant.getNom())
            .map (ressource ->  {
                ressource.setQuantite(ressource.getQuantite()-manque);
                daoRessource.save(ressource);
                return null;
            });
    //transformationService.changeQuantity(carburant.getNom(), manque);
    //carburant.setQuantite(carburant.getQuantite() - manque);

    // set gtt carburant du vehicule => faire plein 
    vehicule.setCarburantActuel(vehicule.getTypeVehicule().getCapaciteReservoir());


    }


    //Recolter plante et animal 

    public String recolterPlante(Plante plante, Vehicule vehicule, int distanceKm) {

        if (plante.getEspece().getVehiculeRequis() != vehicule.getTypeVehicule() ) {
            throw new RuntimeException("Pas le bon véhicule");
        }

        consommerCarburant(vehicule, distanceKm);

        return "Le véhicule est aller chercher la récolte ! ";

    }

    public String acheterAnimal(Animal animal, Vehicule vehicule, int distanceKm) {

        if (animal.getEspece().getVehiculeAchatRequis() != vehicule.getTypeVehicule()) {
             throw new RuntimeException("Pas le bon véhicule");
        }

        consommerCarburant(vehicule,distanceKm);

        return "Le véhicule est aller chercher l'animal ! ";
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
