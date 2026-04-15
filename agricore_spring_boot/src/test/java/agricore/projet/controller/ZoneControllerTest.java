package agricore.projet.controller;

import agricore.projet.config.JwtHeaderFilter;
import agricore.projet.config.SecurityConfig;
import agricore.projet.dto.ressource.response.RessourceResponseDTO;
import agricore.projet.dto.vehicule.response.VehiculeResponseDTO;
import agricore.projet.dto.zone.request.PositionRequestDTO;
import agricore.projet.dto.zone.request.ZoneRequestDTO;
import agricore.projet.dto.zone.response.PositionResponseDTO;
import agricore.projet.dto.zone.response.ZoneResponseDTO;
import agricore.projet.dto.zone.response.ZoneWithRessourcesResponseDTO;
import agricore.projet.dto.zone.response.ZoneWithVehiculesResponseDTO;
import agricore.projet.exception.ZoneNotFoundException;
import agricore.projet.model.Fermier;
import agricore.projet.model.NomZone;
import agricore.projet.repository.IDAOUtilisateur;
import agricore.projet.services.JpaUserDetailsService;
import agricore.projet.services.JwtUtils;
import agricore.projet.services.ZoneService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

@WebMvcTest(controllers = ZoneController.class)
@Import({SecurityConfig.class, JwtHeaderFilter.class})
public class ZoneControllerTest {
    @Autowired
    private ZoneController zoneController;

    @MockitoBean
    private ZoneService zoneService;

    @MockitoBean
    private JpaUserDetailsService jpaUserDetailsService;

    @MockitoBean
    private JwtUtils jwtUtils;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;


    private final PositionResponseDTO POSITION_RESP_DTO = new PositionResponseDTO(1, 1, 5, 5);
    private final PositionRequestDTO POSITION_REQ_DTO = new PositionRequestDTO(1, 1, 5, 5);

    private final int ZONE_ID_NOT_EXIST = 42;
    private final int ZONE_ID = 1;
    private final NomZone NOM_ZONE = NomZone.POULAILLER;
    private final int FERMIER_ID = 5;
    private final ZoneResponseDTO ZONE_RESP_DTO = new ZoneResponseDTO(ZONE_ID,POSITION_RESP_DTO, NOM_ZONE, FERMIER_ID);
    private final ZoneRequestDTO ZONE_REQ_DTO = new ZoneRequestDTO(POSITION_REQ_DTO,NOM_ZONE,FERMIER_ID);
    private final String URL = "/api/zone";
    private final String URL_ID = URL+"/"+ZONE_ID;


    @Test
    public void shouldFindAllZonesReturnUnauthorized() throws Exception {
        //GIVEN

        //When
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(URL));

        //Then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void shouldFindAllZonesReturnOk() throws Exception {
        //GIVEN
        Mockito.when(zoneService.getAllZone()).thenReturn(List.of(ZONE_RESP_DTO));
        //WHEN
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(URL));

        //THEN
        result.andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(zoneService).getAllZone();

        ZoneResponseDTO[] responseDTOS = objectMapper.readValue(result.andReturn().getResponse().getContentAsString(), ZoneResponseDTO[].class);
        assertThat(responseDTOS).hasSize(1);
        assertThat(responseDTOS[0]).hasOnlyFields("id","position","nomZone","fermierId");
    }

    @Test
    public void shouldFindZoneByIdReturnUnauthorized() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(URL_ID));
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void shouldFindZoneByIdReturnNotFound() throws Exception {
        //GIVEN
        Mockito.when(zoneService.getZoneById(ZONE_ID_NOT_EXIST)).thenThrow(ZoneNotFoundException.class);
        //WHEN
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(URL + "/" + ZONE_ID_NOT_EXIST));
        //THEN
        result.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser
    public void shouldFindZoneByIdReturnOk() throws Exception {
        //GIVEN
        Mockito.when(zoneService.getZoneById(ZONE_ID)).thenReturn(ZONE_RESP_DTO);
        //WHEN
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(URL_ID));
        //THEN
        result.andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(zoneService).getZoneById(ZONE_ID);
    }

    @Test
    @WithMockUser
    public void shouldCreateZoneReturnOk() throws Exception {
        //GIVEN
        Mockito.when(zoneService.create(any(ZoneRequestDTO.class))).thenReturn(ZONE_ID);
        String json = objectMapper.writeValueAsString(ZONE_REQ_DTO);
        //When
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$").value(ZONE_ID));
    }

    @Test
    @WithMockUser
    public void shouldCreateZoneThrowExceptions() throws Exception {
        //GIVEN
        ZoneRequestDTO FalseZoneReqDTO = new ZoneRequestDTO();

    }
}
