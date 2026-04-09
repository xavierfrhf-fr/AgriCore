package agricore.projet.controller;


import agricore.projet.dto.zone.request.ZoneRequestDTO;
import agricore.projet.dto.zone.response.ZoneResponseDTO;
import agricore.projet.services.ZoneService;
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
    public int createZone(@RequestBody ZoneRequestDTO request){//TODO Ajouter validator dans ZoneRequestDTO
        return zoneService.create(request);
    }

    @PatchMapping("/{id}")
    public int patchZone(@RequestBody ZoneRequestDTO request, @PathVariable Integer id){//TODO Ajouter validator dans ZoneRequestDTO
        return zoneService.patch(request, id);
    }

    @PutMapping("/{id}")
    public int updateZone(@RequestBody ZoneRequestDTO request, @PathVariable Integer id){
        return zoneService.put(request, id);
    }

    @DeleteMapping("/{id}")
    public void deleteZone(@PathVariable Integer id){
        zoneService.delete(id);
    }


}