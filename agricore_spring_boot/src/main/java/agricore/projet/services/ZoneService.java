package agricore.projet.services;

import agricore.projet.dto.response.ZoneResponseDTO;
import agricore.projet.exception.ZoneNotFoundException;
import agricore.projet.model.Zone;
import agricore.projet.repository.IDAOZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZoneService {

    private final IDAOZone daoZone;

    public ZoneService(IDAOZone daoZone) {
        this.daoZone = daoZone;
    }

    public ZoneResponseDTO getZoneById(Integer id){
        return ZoneResponseDTO.convert(daoZone.findById(id)
                .orElseThrow(() -> new ZoneNotFoundException(id)));
    }
}
