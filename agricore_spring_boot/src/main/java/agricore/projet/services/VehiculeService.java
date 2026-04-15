package agricore.projet.services;

import java.util.List;

import org.springframework.stereotype.Service;

import agricore.projet.dto.vehicule.request.VehiculeRequestDTO;
import agricore.projet.dto.vehicule.response.VehiculeResponseDTO;
import agricore.projet.exception.VehiculeNotFound;
import agricore.projet.exception.ZoneNotFoundException;
import agricore.projet.model.Vehicule;
import agricore.projet.model.Zone;
import agricore.projet.repository.IDAOZone;
import agricore.projet.repository.VehiculeRepository;

@Service
public class VehiculeService {

    private final VehiculeRepository daovehicule;
    private final IDAOZone daoZone;

    public VehiculeService( VehiculeRepository daovehicule, IDAOZone daoZone) {
        this.daovehicule = daovehicule;
        this.daoZone = daoZone;
    }

    public VehiculeResponseDTO findByIdDTO(Integer id) {
        return VehiculeResponseDTO.convert(daovehicule.findById(id).orElseThrow(() -> new VehiculeNotFound(id)));  //Throw(() -> new VehiculeN);
    }

    public List<VehiculeResponseDTO> findAllDTO() {
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
