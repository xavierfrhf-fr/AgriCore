package agricore.projet.controller;


import agricore.projet.dto.zone.request.ZoneRequestDTO;
import agricore.projet.dto.zone.response.ZoneResponseDTO;
import agricore.projet.dto.zone.response.ZoneWithRessourcesResponseDTO;
import agricore.projet.dto.zone.response.ZoneWithVehiculesResponseDTO;
import agricore.projet.services.ZoneService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<ZoneResponseDTO> getAllZone(){
        return zoneService.getAllZone();
    }

    @PostMapping()
    public int createZone(@Valid @RequestBody ZoneRequestDTO request){
        return zoneService.create(request);
    }

    @PatchMapping("/{id}")
    public int patchZone(@RequestBody ZoneRequestDTO request, @PathVariable Integer id){
        return zoneService.patch(request, id);
    }

    @PutMapping("/{id}")
    public int updateZone(@Valid @RequestBody ZoneRequestDTO request, @PathVariable Integer id){
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

}