package agricore.projet.services;

import agricore.projet.dto.vehicule.request.VehiculeRequestDTO;
import agricore.projet.dto.vehicule.response.VehiculeResponseDTO;
import agricore.projet.model.Vehicule;
import agricore.projet.model.Zone;
import agricore.projet.repository.VehiculeRepository;
import agricore.projet.repository.IDAOZone;


public class VehiculeService {

    private final VehiculeRepository daovehicule;
    private final IDAOZone daoZone;

    public VehiculeService( VehiculeRepository daovehicule, IDAOZone daoZone) {
        this.daovehicule = daovehicule;
        this.daoZone = daoZone;
    }

    public VehiculeResponseDTO findByIdDTO(Integer id) {
        return VehiculeResponseDTO.convert(daovehicule.findById(id).orElse(null));  //Throw(() -> new VehiculeN);
    }

    public VehiculeResponseDTO create(VehiculeRequestDTO vehiculeRequestDTO) {
        
        Vehicule v = new Vehicule();
        v.setTypeVehicule(vehiculeRequestDTO.getTypeVehicule());
        v.setDateControleTech(vehiculeRequestDTO.getDateControleTech());

        Zone z = daoZone.findById(vehiculeRequestDTO.getZoneid()).orElse(null);

        v.setZone(z);

        Vehicule tosave = daovehicule.save(v);
        
        return VehiculeResponseDTO.convert(tosave);
    }


    public VehiculeResponseDTO update(Integer id, VehiculeRequestDTO vehiculeRequestDTO) {
        
        //find entity
        Vehicule v = daovehicule.findById(id).orElse(null);

        //maj entity
        v.setTypeVehicule(vehiculeRequestDTO.getTypeVehicule());
        v.setDateControleTech(vehiculeRequestDTO.getDateControleTech());


        //set zone
        Zone z = daoZone.findById(vehiculeRequestDTO.getZoneid()).orElse(null);
        v.setZone(z);

        //save entity
        Vehicule update = daovehicule.save(v);

        return VehiculeResponseDTO.convert(update);


    }


}
