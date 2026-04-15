package agricore.projet.services;

import agricore.projet.dto.zone.response.PositionResponseDTO;
import agricore.projet.dto.zone.response.ZoneResponseDTO;
import agricore.projet.exception.ZoneNotFoundException;
import agricore.projet.model.Fermier;
import agricore.projet.model.NomZone;
import agricore.projet.model.Position;
import agricore.projet.model.Zone;
import agricore.projet.repository.IDAOZone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class ZoneServiceTest {
    @Autowired
    private ZoneService zoneService;

    @MockitoBean
    private IDAOZone daoZone;

    private final Position POSITION = new Position(1,1,5,5);
    private final int FERMIER_ID = 5;
    private final Fermier FERMIER = new Fermier(FERMIER_ID,"testLogin","testPassword");

    private final int ZONE_ID_NOT_EXIST = 100;
    private final int ZONE1_ID = 0;
    private final int ZONE2_ID = 1;
    private final NomZone ZONE1_NOMZONE = NomZone.CHAMPS;
    private final NomZone ZONE2_NOMZONE = NomZone.CUVE;
    private final Zone ZONE1 = new Zone(ZONE1_ID,POSITION, ZONE1_NOMZONE, FERMIER);
    private final Zone ZONE2 = new Zone(ZONE2_ID,POSITION, ZONE2_NOMZONE, FERMIER);
    private final List<Zone> ZONE_LIST = new ArrayList<>(List.of(ZONE1, ZONE2));

    @Test
    public void getAllZonesShouldReturnAllZones(){
        //Given
        Mockito.when(daoZone.findAll()).thenReturn(ZONE_LIST);

        //When
        List<ZoneResponseDTO> result = zoneService.getAllZone();

        //Then
        assertThat(result)
                .extracting(ZoneResponseDTO::getNomZone)
                .containsExactly(ZONE1_NOMZONE,ZONE2_NOMZONE);

        assertThat(result)
                .extracting(ZoneResponseDTO::getFermierId)
                .containsOnly(FERMIER_ID);

        assertThat(result)
                .extracting(ZoneResponseDTO::getPosition)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactly(PositionResponseDTO.convert(POSITION), PositionResponseDTO.convert(POSITION));

        Assertions.assertEquals(ZONE_LIST.size(), result.size());
        Mockito.verify(daoZone).findAll();
    }

    @Test
    public void getZoneByIdShouldReturnZone(){
        //Given
        Mockito.when(daoZone.findById(ZONE1_ID)).thenReturn(Optional.of(ZONE1));

        //When
        ZoneResponseDTO result = zoneService.getZoneById(ZONE1_ID);

        //Then
        assertThat(result)
                .isNotNull();
        assertThat(result)
                .isInstanceOf(ZoneResponseDTO.class);
        assertThat(result)
                .extracting(ZoneResponseDTO::getNomZone,
                        ZoneResponseDTO::getFermierId,
                        ZoneResponseDTO::getId)
                .containsExactly(ZONE1_NOMZONE,FERMIER_ID,ZONE1_ID);

    }

    @Test
    public void getZoneByIdShouldThrow(){
        //GIVEN
        Mockito.when(daoZone.findById(ZONE_ID_NOT_EXIST)).thenReturn(Optional.empty());
        //WHEN + THEN
        assertThatThrownBy(() -> zoneService.getZoneById(ZONE_ID_NOT_EXIST))
                .hasMessageContaining(String.valueOf(ZONE_ID_NOT_EXIST))
                .isInstanceOf(ZoneNotFoundException.class);
    }

    @Test
    public void createZoneShouldReturnId(){
        //Given

        //When

        //Then

    }



}
