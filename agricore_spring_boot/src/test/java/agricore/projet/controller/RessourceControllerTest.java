package agricore.projet.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import agricore.projet.dto.ressource.response.RessourceResponseDTO;
import agricore.projet.model.NomRessource;
import agricore.projet.model.NomZone;
import agricore.projet.services.JpaUserDetailsService;
import agricore.projet.services.JwtUtils;
import agricore.projet.services.RessourceService;

@WebMvcTest(controllers = RessourceController.class)
// @Import(SecurityConfig.class)
public class RessourceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RessourceService ressourceService;

    @MockitoBean
    private JpaUserDetailsService jpaUserDetailsService;

    @MockitoBean
    private JwtUtils jwtUtils;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Integer RESSOURCE_ID = 1;
    private static final NomRessource RESSOURCE_NOM = NomRessource.Fraise;
    private static final int RESSOURCE_QUANTITE = 1;
    private static final double RESSOURCE_PRIX = 1.10;
    private static final int RESSOURCE_STOCK_MIN = 10;
    private static final Integer ZONE_ZONE_ID = 1;
    private static final NomZone ZONE_ZONE_NOM = NomZone.POULAILLER;

    private static final String API_URL = "/api/ressource";
    private static final String API_URL_BY_ID = API_URL + "/" + RESSOURCE_ID;

    @Test
    void shouldFindAllStatusUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void shouldFindByIdStatusUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void shouldCreateStatusUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(API_URL).with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void shouldPatchStatusUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch(API_URL_BY_ID).with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void shouldUpdateStatusUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(API_URL_BY_ID).with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void shouldDeleteByIdStatusUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(API_URL_BY_ID).with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void shouldFindAllStatusOkAndAttributesOk() throws Exception {
        // given
        RessourceResponseDTO ressource = new RessourceResponseDTO(RESSOURCE_ID, RESSOURCE_NOM, RESSOURCE_QUANTITE,
                RESSOURCE_PRIX,
                RESSOURCE_STOCK_MIN, ZONE_ZONE_ID, ZONE_ZONE_NOM);

        Mockito.when(this.ressourceService.getAll()).thenReturn(List.of(ressource));

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL));

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());

        result.andExpect(MockMvcResultMatchers.jsonPath("$[*].id").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$[*].nom").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$[*].quantite").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$[*].prix").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$[*].stockMin").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$[*].zoneId").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$[*].zoneNom").exists());

        result.andExpect(MockMvcResultMatchers.jsonPath("$[*].fermierId").doesNotExist());

        Mockito.verify(this.ressourceService).getAll();
    }

    @Test
    @WithMockUser
    void shouldFindByIdStatusOkAndAttributesOk() throws Exception {
        // given
        RessourceResponseDTO ressource = new RessourceResponseDTO(RESSOURCE_ID, RESSOURCE_NOM, RESSOURCE_QUANTITE,
                RESSOURCE_PRIX,
                RESSOURCE_STOCK_MIN, ZONE_ZONE_ID, ZONE_ZONE_NOM);

        Mockito.when(this.ressourceService.getById(RESSOURCE_ID)).thenReturn(ressource);

        // when
        ResultActions result = this.mockMvc.perform(MockMvcRequestBuilders.get(API_URL_BY_ID));

        // then
        result.andExpect(MockMvcResultMatchers.status().isOk());

        result.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.nom").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.quantite").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.prix").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.stockMin").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.zoneId").exists());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.zoneNom").exists());

        result.andExpect(MockMvcResultMatchers.jsonPath("$.fermierId").doesNotExist());

        Mockito.verify(this.ressourceService).getById(RESSOURCE_ID);
    }

    @Test
    @WithMockUser
    void shouldCreateStatusOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(API_URL)
                .with(csrf())
                .contentType("application/json")
                .content("{}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser
    void shouldUpdateStatusOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(API_URL_BY_ID)
                .with(csrf())
                .contentType("application/json")
                .content("{}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser
    void shouldPatchStatusOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch(API_URL_BY_ID)
                .with(csrf())
                .contentType("application/json")
                .content("{}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser
    void shouldDeleteStatusOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(API_URL_BY_ID)
                .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
