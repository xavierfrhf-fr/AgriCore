package agricore.projet.controller;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import agricore.projet.config.JwtHeaderFilter;
import agricore.projet.config.SecurityConfig;
import agricore.projet.dto.vehicule.request.VehiculeRequestDTO;
import agricore.projet.dto.vehicule.response.VehiculeResponseDTO;
import agricore.projet.model.TypeVehicule;
import agricore.projet.repository.IDAOVehicule;
import agricore.projet.repository.IDAOZone;
import agricore.projet.services.JpaUserDetailsService;
import agricore.projet.services.JwtUtils;
import agricore.projet.services.VehiculeService;

@WebMvcTest(controllers = VehiculeController.class)
@Import({SecurityConfig.class, JwtHeaderFilter.class})
public class VehiculeControllerTest {

    private final LocalDate DATE_CONTROLE_TECH = LocalDate.now().plusDays(10);
    private final int DELAI_AVANT_CONTROLE = (int) LocalDate.now().until(DATE_CONTROLE_TECH, java.time.temporal.ChronoUnit.DAYS);
    private final int ZONE_ID = 1;
    private final int VEHICULE_ID = 1;
    private final TypeVehicule TYPE_VEHICULE = TypeVehicule.Utilitaire;

    private final VehiculeResponseDTO VEHICULE_RESPONSE_DTO = new VehiculeResponseDTO(VEHICULE_ID, TYPE_VEHICULE, DATE_CONTROLE_TECH, DELAI_AVANT_CONTROLE, ZONE_ID);
    private final VehiculeRequestDTO VEHICULE_REQUEST_DTO = new VehiculeRequestDTO(TYPE_VEHICULE, DATE_CONTROLE_TECH, ZONE_ID);

    private final String URL = "/api/vehicule";
    private final String URL_ID = URL + "/" + VEHICULE_ID;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JpaUserDetailsService jpaUserDetailsService;

    @MockitoBean
    private JwtUtils jwtUtils;


    @MockitoBean
    IDAOVehicule vehiculeRepository;

    @MockitoBean
    IDAOZone daoZone;

    @MockitoBean
    private VehiculeService vehiculeService;

   

    @Test
    public void shouldgetAllVehiculeReturnUnauthorized() throws Exception {
        //given 

        //when
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(URL));
        //then 

        result.andExpect(MockMvcResultMatchers.status().isUnauthorized());

    }




}
