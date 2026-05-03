package agricore.projet.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import agricore.projet.dto.data.RessourceDataDTO;
import agricore.projet.dto.data.UniteDataDTO;
import agricore.projet.dto.data.ZoneDataDTO;
import agricore.projet.model.ressource.NomRessource;
import agricore.projet.model.ressource.Unite;
import agricore.projet.model.zone.NomZone;
import agricore.projet.model.zone.position.MapSize;
import agricore.projet.model.zone.position.Position;

@RestController
@RequestMapping("/api/data")
public class DataController {

    @GetMapping("/zone")
    public List<ZoneDataDTO> getZoneData() {
        List<ZoneDataDTO> dtos = new ArrayList<>();
        for (NomZone zone : NomZone.values()) {
            dtos.add(ZoneDataDTO.from(zone));
        }
        return dtos;
    }

    @GetMapping("/zone/mapSize")
    public MapSize getMapSize() {
        return Position.mapSize;
    }

    @GetMapping("/ressource")
    public List<RessourceDataDTO> getRessourceData() {
        return Stream.of(NomRessource.values())
                .map(RessourceDataDTO::convert)
                .toList();
    }

    @GetMapping("/unite")
    public List<UniteDataDTO> getUniteData() {
        return Stream.of(Unite.values())
                .map(UniteDataDTO::convert)
                .toList();
    }
}
