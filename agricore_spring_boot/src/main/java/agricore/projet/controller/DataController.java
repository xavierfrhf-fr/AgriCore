package agricore.projet.controller;

import agricore.projet.dto.data.ZoneDataDTO;
import agricore.projet.model.zone.NomZone;
import agricore.projet.model.zone.position.MapSize;
import agricore.projet.model.zone.position.Position;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/data")
public class DataController {

    @GetMapping("/zone")
    public List<ZoneDataDTO> getZoneData(){
        List<ZoneDataDTO> dtos = new ArrayList<>();
        for (NomZone zone : NomZone.values()){
            dtos.add(ZoneDataDTO.from(zone));
        }
        return dtos;
    }

    @GetMapping("/zone/mapSize")
    public MapSize getMapSize(){
        return Position.mapSize;
    }
}
