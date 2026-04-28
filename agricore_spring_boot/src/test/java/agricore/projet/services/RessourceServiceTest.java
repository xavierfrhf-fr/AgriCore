package agricore.projet.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;

import agricore.projet.config.SecurityConfig;
import agricore.projet.dto.ressource.request.PrixRequestDTO;
import agricore.projet.dto.ressource.request.RessourceRequestDTO;
import agricore.projet.dto.ressource.response.RessourceResponseDTO;
import agricore.projet.exception.RessourceNotFoundException;
import agricore.projet.model.ressource.NomRessource;
import agricore.projet.model.zone.NomZone;
import agricore.projet.model.ressource.PrixLot;
import agricore.projet.model.ressource.Ressource;
import agricore.projet.model.ressource.Unite;
import agricore.projet.model.zone.Zone;
import agricore.projet.repository.IDAORessource;
import agricore.projet.repository.IDAOZone;

@ExtendWith(MockitoExtension.class)
@Import(SecurityConfig.class)
class RessourceServiceTest {

    @Mock
    private IDAORessource daoRessource;
    @Mock
    private IDAOZone daoZone;
    @Mock
    private ZoneService zoneService;
    @InjectMocks
    private RessourceService ressourceService;

    private static final int RESSOURCE_ID = 1;
    private static final int ZONE_ID = 10;
    private static final int QUANTITE = 100;
    private static final int STOCK_MIN = 20;
    private static final PrixLot PRIX_LOT = new PrixLot(new BigDecimal("2.00"), 100, Unite.GRAMME);
    private static final PrixRequestDTO PRIX_REQUEST_DTO = new PrixRequestDTO(new BigDecimal("2.00"), 100,
            Unite.GRAMME);
    private static final NomRessource NOM_RESSOURCE = NomRessource.Blé;
    private static final NomZone NOM_ZONE = NomZone.SILO;
    private static final int ID_INEXISTANT = 99;
    private static final int QUANTITE_MODIFIEE = 200;
    private static final NomRessource NOM_RESSOURCE_UPDATE = NomRessource.Fraise;
    private static final int QUANTITE_UPDATE = 300;
    private static final int STOCK_MIN_UPDATE = 50;

    private Zone zone;
    private Ressource ressource;
    private RessourceRequestDTO request;

    @BeforeEach
    void setUp() {
        zone = new Zone();
        zone.setId(ZONE_ID);
        zone.setNomZone(NOM_ZONE);
        ressource = new Ressource(RESSOURCE_ID, NOM_RESSOURCE, QUANTITE, PRIX_LOT, STOCK_MIN, zone);
        request = new RessourceRequestDTO(NOM_RESSOURCE, QUANTITE, PRIX_REQUEST_DTO, STOCK_MIN);
    }

    @Test
    void SetRequestDTOSetFields() {
        request = new RessourceRequestDTO();
        request.setNom(NOM_RESSOURCE);
        request.setQuantite(QUANTITE);
        request.setStockMin(STOCK_MIN);

        assertThat(request.getNom()).isEqualTo(NOM_RESSOURCE);
        assertThat(request.getQuantite()).isEqualTo(QUANTITE);
        assertThat(request.getStockMin()).isEqualTo(STOCK_MIN);
    }

    @Test
    void getAllReturnsAll() {
        when(daoRessource.findAll()).thenReturn(List.of(ressource));
        assertThat(ressourceService.getAll()).hasSize(1);
    }

    @Test
    void getAllReturnsEmpty() {
        when(daoRessource.findAll()).thenReturn(List.of());
        assertThat(ressourceService.getAll()).isEmpty();
    }

    @Test
    void getByIdReturnsCorrectDTO() {
        when(daoRessource.findById(RESSOURCE_ID)).thenReturn(Optional.of(ressource));
        RessourceResponseDTO result = ressourceService.getById(RESSOURCE_ID);

        assertThat(result.getId()).isEqualTo(RESSOURCE_ID);
        assertThat(result.getNom()).isEqualTo(NOM_RESSOURCE);
        assertThat(result.getQuantite()).isEqualTo(QUANTITE);
        assertThat(result.getStockMin()).isEqualTo(STOCK_MIN);
        assertThat(result.getZoneId()).isEqualTo(ZONE_ID);
        assertThat(result.getZoneNom()).isEqualTo(NOM_ZONE);
    }

    @Test
    void getByIdThrowsWhenNotFound() {
        when(daoRessource.findById(ID_INEXISTANT)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> ressourceService.getById(ID_INEXISTANT))
                .isInstanceOf(RessourceNotFoundException.class);
    }

    @Test
    void createSavesAndReturnsDTO() {
        when(daoRessource.findAll()).thenReturn(List.of());
        when(zoneService.findZoneThatStoreRessources(NOM_RESSOURCE)).thenReturn(Optional.of(zone));
        when(daoRessource.save(any())).thenReturn(ressource);

        RessourceResponseDTO result = ressourceService.create(request);

        assertThat(result.getNom()).isEqualTo(NOM_RESSOURCE);
        assertThat(result.getZoneId()).isEqualTo(ZONE_ID);
        assertThat(result.getZoneNom()).isEqualTo(NOM_ZONE);
        verify(daoRessource).save(any());
    }

    @Test
    void createThrowsWhenAlreadyExists() {
        when(daoRessource.findAll()).thenReturn(List.of(ressource));
        assertThatThrownBy(() -> ressourceService.create(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("existe déjà");
        verify(daoRessource, never()).save(any());
    }

    @Test
    void createThrowsWhenNoZone() {
        when(daoRessource.findAll()).thenReturn(List.of());
        when(zoneService.findZoneThatStoreRessources(NOM_RESSOURCE)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> ressourceService.create(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Aucune zone");
        verify(daoRessource, never()).save(any());
    }

    @Test
    void patchUpdatesOnlyProvidedFields() {
        when(daoRessource.findById(RESSOURCE_ID)).thenReturn(Optional.of(ressource));
        when(daoRessource.save(any()))
                .thenReturn(new Ressource(RESSOURCE_ID, NOM_RESSOURCE, QUANTITE_MODIFIEE, PRIX_LOT, STOCK_MIN, zone));

        RessourceResponseDTO result = ressourceService.patch(RESSOURCE_ID,
                new RessourceRequestDTO(null, QUANTITE_MODIFIEE, null, null));

        assertThat(result.getQuantite()).isEqualTo(QUANTITE_MODIFIEE);
        assertThat(result.getStockMin()).isEqualTo(STOCK_MIN);
    }

    @Test
    void patchThrowsWhenNotFound() {
        when(daoRessource.findById(ID_INEXISTANT)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> ressourceService.patch(ID_INEXISTANT, request))
                .isInstanceOf(RessourceNotFoundException.class);
        verify(daoRessource, never()).save(any());
    }

    @Test
    void updateReplacesAllFields() {
        when(daoRessource.findById(RESSOURCE_ID)).thenReturn(Optional.of(ressource));
        when(daoRessource.save(any())).thenReturn(
                new Ressource(RESSOURCE_ID, NOM_RESSOURCE, QUANTITE_UPDATE, PRIX_LOT, STOCK_MIN_UPDATE, zone));

        RessourceResponseDTO result = ressourceService.update(RESSOURCE_ID,
                new RessourceRequestDTO(NOM_RESSOURCE, QUANTITE_UPDATE, PRIX_REQUEST_DTO, STOCK_MIN_UPDATE));

        assertThat(result.getQuantite()).isEqualTo(QUANTITE_UPDATE);
        assertThat(result.getStockMin()).isEqualTo(STOCK_MIN_UPDATE);
    }

    @Test
    void updateThrowsWhenNotFound() {
        when(daoRessource.findById(ID_INEXISTANT)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> ressourceService.update(ID_INEXISTANT, request))
                .isInstanceOf(RessourceNotFoundException.class);
    }

    @Test
    void updateIgnoresNameModification() {
        when(daoRessource.findById(RESSOURCE_ID)).thenReturn(Optional.of(ressource));
        when(daoRessource.save(any())).thenReturn(
                new Ressource(RESSOURCE_ID,
                        NOM_RESSOURCE, QUANTITE_UPDATE, PRIX_LOT, STOCK_MIN_UPDATE, zone));

        RessourceResponseDTO result = ressourceService.update(RESSOURCE_ID,
                new RessourceRequestDTO(NOM_RESSOURCE_UPDATE, QUANTITE_UPDATE, PRIX_REQUEST_DTO, STOCK_MIN_UPDATE));

        assertThat(result.getNom()).isEqualTo(NOM_RESSOURCE);
    }

    @Test
    void deleteByIdDeletesWhenExists() {
        when(daoRessource.findById(RESSOURCE_ID)).thenReturn(Optional.of(ressource));
        ressourceService.deleteById(RESSOURCE_ID);
        verify(daoRessource).deleteById(RESSOURCE_ID);
    }

    @Test
    void deleteByIdThrowsWhenNotFound() {
        when(daoRessource.findById(ID_INEXISTANT)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> ressourceService.deleteById(ID_INEXISTANT))
                .isInstanceOf(RessourceNotFoundException.class);
        verify(daoRessource, never()).deleteById(any());
    }

    @Test
    void ressourceAlreadyExistsReturnsTrueWhenExists() {
        when(daoRessource.findAll()).thenReturn(List.of(ressource));
        assertThat(ressourceService.ressourceAlreadyExists(NOM_RESSOURCE)).isTrue();
    }

    @Test
    void ressourceAlreadyExistsReturnsFalseWhenNotExists() {
        when(daoRessource.findAll()).thenReturn(List.of());
        assertThat(ressourceService.ressourceAlreadyExists(NOM_RESSOURCE)).isFalse();
    }
}