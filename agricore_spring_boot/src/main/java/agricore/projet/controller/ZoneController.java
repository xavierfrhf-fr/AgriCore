package agricore.projet.controller;


import agricore.projet.dto.zone.request.ZoneRequestDTO;
import agricore.projet.dto.zone.response.ZoneResponseDTO;
import agricore.projet.dto.zone.response.ZoneWithAnimalsResponseDTO;
import agricore.projet.dto.zone.response.ZoneWithRessourcesResponseDTO;
import agricore.projet.dto.zone.response.ZoneWithVehiculesResponseDTO;
import agricore.projet.services.ZoneService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/zone")
public class ZoneController {

    private final ZoneService zoneService;

    public ZoneController(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    @GetMapping("/{id}")
    public ZoneResponseDTO getZoneById(@PathVariable Integer id){
        return zoneService.getZoneById(id);
    }

    @GetMapping
    public List<ZoneResponseDTO> getAllZone() {
        Comparator<ZoneResponseDTO> comparator =
                Comparator
                        .comparing((ZoneResponseDTO zone) -> !zone.getNomZone().isZoneUnique())
                        .thenComparing(zone -> zone.getNomZone().name());
        return zoneService.getAllZone()
                .stream()
                .sorted(comparator)
                .toList();
    }

    @PostMapping()
    public int createZone(@Valid @RequestBody ZoneRequestDTO request){
        return zoneService.create(request);
    }

    @PostMapping("/randomPos")
    public boolean createZoneWithRandomPosition(@RequestBody ZoneRequestDTO request){
        return zoneService.createWithRandomPos(request.getNomZone());
    }

    @PatchMapping("/{id}")
    public int patchZone(@RequestBody ZoneRequestDTO request, @PathVariable Integer id){
        return zoneService.patch(request, id);
    }

    @PutMapping("/{id}")
    public int putZone(@Valid @RequestBody ZoneRequestDTO request, @PathVariable Integer id){
        return zoneService.put(request, id);
    }

    @DeleteMapping("/{id}")
    public void deleteZone(@PathVariable Integer id){
        zoneService.delete(id);
    }

    @GetMapping("/vehicule/{id}")
    public ZoneWithVehiculesResponseDTO getZoneWithVehicule(@PathVariable Integer id){
        return zoneService.getZoneWithVehicules(id);
    }

    @GetMapping("/ressource/{id}")
    public ZoneWithRessourcesResponseDTO getZoneWithRessources(@PathVariable Integer id){
        return zoneService.getZoneWithRessources(id);
    }

    @GetMapping("/animals/{id}")
    public ZoneWithAnimalsResponseDTO getZoneWithAnimals(@PathVariable Integer id){
        return zoneService.getZoneWithAnimals(id);
    }

    @GetMapping("/complete")
    public List<ZoneResponseDTO> getAllZonesComplete(){
        return zoneService.getAllZoneWithRelation();
    }

    @GetMapping("/by-name/{name}")
    public List<ZoneResponseDTO> getZoneByName(@PathVariable String name) {
        return zoneService.getZoneByName(name);
    }
    

}