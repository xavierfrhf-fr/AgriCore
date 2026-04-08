package agricore.projet.restController;

import agricore.projet.dto.response.ZoneResponseDTO;
import agricore.projet.services.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
