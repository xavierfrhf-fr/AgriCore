package agricore.projet.controller;

import agricore.projet.config.JwtHeaderFilter;
import agricore.projet.config.SecurityConfig;
import agricore.projet.dto.ressource.response.PrixResponseDTO;
import agricore.projet.dto.ressource.response.RessourceResponseDTO;
import agricore.projet.dto.vehicule.response.VehiculeResponseDTO;
import agricore.projet.dto.zone.request.PositionRequestDTO;
import agricore.projet.dto.zone.request.ZoneRequestDTO;
import agricore.projet.dto.zone.response.PositionResponseDTO;
import agricore.projet.dto.zone.response.ZoneResponseDTO;
import agricore.projet.dto.zone.response.ZoneWithRessourcesResponseDTO;
import agricore.projet.dto.zone.response.ZoneWithVehiculesResponseDTO;
import agricore.projet.exception.ZoneNotFoundException;
import agricore.projet.model.NomRessource;
import agricore.projet.model.zone.NomZone;
import agricore.projet.model.PrixLot;
import agricore.projet.model.TypeVehicule;
import agricore.projet.model.Unite;
import agricore.projet.model.zone.position.Rotation;
import agricore.projet.services.JpaUserDetailsService;
import agricore.projet.services.JwtUtils;
import agricore.projet.services.ZoneService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@WebMvcTest(controllers = ZoneController.class)
@Import({ SecurityConfig.class, JwtHeaderFilter.class })
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

    private final PositionResponseDTO POSITION_RESP_DTO = new PositionResponseDTO(1,1, Rotation.DEG_O);
    private final PositionRequestDTO POSITION_REQ_DTO = new PositionRequestDTO(1,1, Rotation.DEG_O);

    private final int ZONE_ID_NOT_EXIST = 42;
    private final int ZONE_ID = 1;
    private final NomZone NOM_ZONE = NomZone.POULAILLER;
    private final int FERMIER_ID = 5;
    private final ZoneResponseDTO ZONE_RESP_DTO = new ZoneResponseDTO(ZONE_ID, POSITION_RESP_DTO, NOM_ZONE, FERMIER_ID);
    private final ZoneRequestDTO ZONE_REQ_DTO = new ZoneRequestDTO(POSITION_REQ_DTO, NOM_ZONE, FERMIER_ID);
    private final String URL = "/api/zone";
    private final String URL_ID = URL + "/" + ZONE_ID;

    @Test
    public void shouldFindAllZonesReturnUnauthorized() throws Exception {
        // GIVEN

        // When
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(URL));

        // Then
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void shouldFindAllZonesReturnOk() throws Exception {
        // GIVEN
        Mockito.when(zoneService.getAllZone()).thenReturn(List.of(ZONE_RESP_DTO));
        // WHEN
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(URL));

        // THEN
        result.andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(zoneService).getAllZone();

        ZoneResponseDTO[] responseDTOS = objectMapper.readValue(result.andReturn().getResponse().getContentAsString(),
                ZoneResponseDTO[].class);
        assertThat(responseDTOS).hasSize(1);
        assertThat(responseDTOS[0]).hasOnlyFields("id", "position", "nomZone", "fermierId");
    }

    @Test
    public void shouldFindZoneByIdReturnUnauthorized() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(URL_ID));
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void shouldFindZoneByIdReturnNotFound() throws Exception {
        // GIVEN
        Mockito.when(zoneService.getZoneById(ZONE_ID_NOT_EXIST)).thenThrow(ZoneNotFoundException.class);
        // WHEN
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(URL + "/" + ZONE_ID_NOT_EXIST));
        // THEN
        result.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser
    public void shouldFindZoneByIdReturnOk() throws Exception {
        // GIVEN
        Mockito.when(zoneService.getZoneById(ZONE_ID)).thenReturn(ZONE_RESP_DTO);
        // WHEN
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(URL_ID));
        // THEN
        result.andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(zoneService).getZoneById(ZONE_ID);
    }

    @Test
    @WithMockUser
    public void shouldCreateZoneReturnOk() throws Exception {
        // GIVEN
        Mockito.when(zoneService.create(any(ZoneRequestDTO.class))).thenReturn(ZONE_ID);
        String json = objectMapper.writeValueAsString(ZONE_REQ_DTO);
        // When
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON));

        result.andExpect(MockMvcResultMatchers.status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$").value(ZONE_ID));
    }

    private static Stream<Arguments> zoneWithNotValidAttributesStream() {
        // Position
        int anchorX = 1;
        int anchorY = 1;
        Rotation rotation = Rotation.DEG_O;

        // Zone
        NomZone nomZone = NomZone.POULAILLER;
        int fermierId = 1;
        return Stream.of(
                Arguments.of(new ZoneRequestDTO(null, nomZone, fermierId)),
                Arguments.of(new ZoneRequestDTO(new PositionRequestDTO(anchorX, anchorY, rotation), null, fermierId)),
                Arguments.of(new ZoneRequestDTO(new PositionRequestDTO(anchorX, anchorY, rotation), nomZone, null)),
                Arguments.of(new ZoneRequestDTO(new PositionRequestDTO(anchorX, anchorY, rotation), nomZone, -1)));
    }

    @ParameterizedTest
    @WithMockUser
    @MethodSource("zoneWithNotValidAttributesStream")
    public void shouldCreateZoneReturnBadRequest(ZoneRequestDTO request) throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .post(URL)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(zoneService, Mockito.never()).create(any(ZoneRequestDTO.class));
    }

    @ParameterizedTest
    @WithMockUser
    @MethodSource("zoneWithNotValidAttributesStream")
    public void shouldPutZoneReturnBadRequest(ZoneRequestDTO request) throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .put(URL_ID)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(zoneService, Mockito.never()).put(any(ZoneRequestDTO.class), anyInt());
    }

    @Test
    @WithMockUser
    public void shouldPutZoneReturnOk() throws Exception {
        Mockito.when(zoneService.put(ZONE_REQ_DTO, ZONE_ID)).thenReturn(ZONE_ID);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .put(URL_ID)
                .content(objectMapper.writeValueAsString(ZONE_REQ_DTO))
                .contentType(MediaType.APPLICATION_JSON));
        result.andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(zoneService).put(any(ZoneRequestDTO.class), eq(ZONE_ID));
    }

    @Test
    @WithMockUser
    public void shouldDeleteZoneReturnOk() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .delete(URL_ID));
        result.andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(zoneService).delete(eq(ZONE_ID));
    }

    private static Stream<Arguments> zoneWithPartialDataAttributesStream() {
        // Position
        int anchorX = 1;
        int anchorY = 1;
        Rotation rotation = Rotation.DEG_O;
        // Zone
        NomZone nomZone = NomZone.POULAILLER;
        int fermierId = 1;
        return Stream.of(
                Arguments.of(new ZoneRequestDTO(null, null, null)),
                Arguments.of(new ZoneRequestDTO(null, nomZone, fermierId)),
                Arguments.of(new ZoneRequestDTO(new PositionRequestDTO(anchorX, anchorY, rotation), null, fermierId)),
                Arguments.of(new ZoneRequestDTO(new PositionRequestDTO(anchorX, anchorY, rotation), nomZone, null)),
                Arguments.of(
                        new ZoneRequestDTO(new PositionRequestDTO(anchorX, anchorY, rotation), nomZone, fermierId)));
    }

    @ParameterizedTest
    @WithMockUser
    @MethodSource("zoneWithPartialDataAttributesStream")
    public void shouldPatchZoneReturnOk(ZoneRequestDTO request) throws Exception {
        Mockito.when(zoneService.patch(request, ZONE_ID)).thenReturn(ZONE_ID);
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .patch(URL_ID)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));
        result.andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(zoneService).patch(any(ZoneRequestDTO.class), eq(ZONE_ID));
    }

    @Test
    public void shouldFindZoneByIdWithRessourceReturnUnauthorized() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(URL + "/ressource/" + ZONE_ID));
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void shouldFindZoneByIdWithRessourceReturnNotFound() throws Exception {
        // GIVEN
        Mockito.when(zoneService.getZoneWithRessources(ZONE_ID_NOT_EXIST)).thenThrow(ZoneNotFoundException.class);
        // WHEN
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(URL + "/ressource/" + ZONE_ID_NOT_EXIST));
        // THEN
        result.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser
    public void shouldFindZoneByIdWithRessourceReturnOk() throws Exception {
        // GIVEN
        ZoneWithRessourcesResponseDTO zoneWithRessourcesResponseDTO = new ZoneWithRessourcesResponseDTO(
                ZONE_ID,
                POSITION_RESP_DTO,
                NOM_ZONE,
                FERMIER_ID,
                List.of(new RessourceResponseDTO(1, NomRessource.Fraise,
                        NomRessource.Fraise.getUniteStockage().getAffichage(),
                        1,
                        PrixResponseDTO.convert(new PrixLot(new BigDecimal("2.00"), 100, Unite.GRAMME)), 10, 1,
                        NomZone.POULAILLER)));
        Mockito.when(zoneService.getZoneWithRessources(ZONE_ID)).thenReturn(zoneWithRessourcesResponseDTO);
        // WHEN
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(URL + "/ressource/" + ZONE_ID));
        // THEN
        result.andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(zoneService).getZoneWithRessources(ZONE_ID);
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(ZONE_ID));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.position.anchorX").value(POSITION_RESP_DTO.getAnchorX()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.position.anchorY").value(POSITION_RESP_DTO.getAnchorY()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.position.rotation").value(POSITION_RESP_DTO.getRotation().name()));
        // tailles
        result.andExpect(MockMvcResultMatchers.jsonPath("$.nomZone").value(NOM_ZONE.name()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.fermierId").value(FERMIER_ID));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.ressources.[*].id").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.ressources.[*].nom").value(NomRessource.Fraise.name()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.ressources.[*].quantite").value(1));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.ressources.[*].stockMin").value(10));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.ressources.[*].zoneId").value(ZONE_ID));
    }

    @Test
    public void shouldFindZoneByIdWithVehiculeReturnUnauthorized() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(URL + "/vehicule/" + ZONE_ID));
        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void shouldFindZoneByIdWithVehiculeReturnNotFound() throws Exception {
        // GIVEN
        Mockito.when(zoneService.getZoneWithVehicules(ZONE_ID_NOT_EXIST)).thenThrow(ZoneNotFoundException.class);
        // WHEN
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(URL + "/vehicule/" + ZONE_ID_NOT_EXIST));
        // THEN
        result.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser
    public void shouldFindZoneByIdWithVehiculeReturnOk() throws Exception {
        // GIVEN
        LocalDate date = LocalDate.now();
        ZoneWithVehiculesResponseDTO zoneWithVehiculesResponseDTO = new ZoneWithVehiculesResponseDTO(
                ZONE_ID,
                POSITION_RESP_DTO,
                NOM_ZONE,
                FERMIER_ID,
                List.of(new VehiculeResponseDTO(1, TypeVehicule.Utilitaire, date, 0, ZONE_ID)));
        Mockito.when(zoneService.getZoneWithVehicules(ZONE_ID)).thenReturn(zoneWithVehiculesResponseDTO);
        // WHEN
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(URL + "/vehicule/" + ZONE_ID));
        // THEN
        result.andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(zoneService).getZoneWithVehicules(ZONE_ID);
        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(ZONE_ID));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.position.anchorX").value(POSITION_RESP_DTO.getAnchorX()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.position.anchorY").value(POSITION_RESP_DTO.getAnchorY()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.position.rotation").value(POSITION_RESP_DTO.getRotation().name()));
        // tailles
        result.andExpect(MockMvcResultMatchers.jsonPath("$.nomZone").value(NOM_ZONE.name()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.fermierId").value(FERMIER_ID));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.vehicules.[*].id").value(1));
        result.andExpect(
                MockMvcResultMatchers.jsonPath("$.vehicules.[*].typeVehicule").value(TypeVehicule.Utilitaire.name()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.vehicules.[*].dateControleTech").value(date.toString()));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.vehicules.[*].delaiAvantControle").value(0));
        result.andExpect(MockMvcResultMatchers.jsonPath("$.vehicules.[*].zoneId").value(ZONE_ID));
    }

}
