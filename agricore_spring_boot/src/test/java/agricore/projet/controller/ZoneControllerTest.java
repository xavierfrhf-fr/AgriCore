package agricore.projet.controller;

import agricore.projet.dto.ressource.response.RessourceResponseDTO;
import agricore.projet.dto.vehicule.response.VehiculeResponseDTO;
import agricore.projet.dto.zone.request.ZoneRequestDTO;
import agricore.projet.dto.zone.response.PositionResponseDTO;
import agricore.projet.dto.zone.response.ZoneResponseDTO;
import agricore.projet.dto.zone.response.ZoneWithRessourcesResponseDTO;
import agricore.projet.dto.zone.response.ZoneWithVehiculesResponseDTO;
import agricore.projet.model.NomZone;
import agricore.projet.services.ZoneService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest
public class ZoneControllerTest {
    @Autowired
    private ZoneController zoneController;

    @MockitoBean
    private ZoneService zoneService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private final PositionResponseDTO POSITION_DTO = new PositionResponseDTO(1, 1, 5, 5);








    public void getAllZonesShouldReturnAllZones(){

    }

}
