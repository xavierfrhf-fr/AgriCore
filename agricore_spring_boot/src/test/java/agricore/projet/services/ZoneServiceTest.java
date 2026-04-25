package agricore.projet.services;

import agricore.projet.dto.zone.request.PositionRequestDTO;
import agricore.projet.dto.zone.request.ZoneRequestDTO;
import agricore.projet.dto.zone.response.*;
import agricore.projet.exception.ZoneNotFoundException;
import agricore.projet.model.*;
import agricore.projet.model.zone.NomZone;
import agricore.projet.model.zone.position.Position;
import agricore.projet.model.zone.Zone;
import agricore.projet.model.zone.position.Rotation;
import agricore.projet.repository.IDAOUtilisateur;
import agricore.projet.repository.IDAOZone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
public class ZoneServiceTest {
    @InjectMocks
    private ZoneService zoneService;

    @Mock
    private PositionService positionService;

    @Mock
    private IDAOZone daoZone;

    @Mock
    private IDAOUtilisateur daoUtilisateur;

    private static final Position POSITION = new Position(1,1, Rotation.DEG_O);
    private static final PositionRequestDTO POSITION_REQ_DTO = new PositionRequestDTO(1,1,Rotation.DEG_O);
    private static final int FERMIER_ID = 5;
    private static final Fermier FERMIER = new Fermier(FERMIER_ID,"testLogin","testPassword");

    private static final int ZONE_ID_NOT_EXIST = 100;
    private static final int ZONE1_ID = 0;
    private static final int ZONE2_ID = 1;
    private static final NomZone ZONE1_NOMZONE = NomZone.CHAMPS;
    private static final NomZone ZONE2_NOMZONE = NomZone.CUVE;
    private static final Zone ZONE1 = new Zone(ZONE1_ID,POSITION, ZONE1_NOMZONE, FERMIER);
    private static final ZoneRequestDTO ZONE1_REQ_DTO = new ZoneRequestDTO(POSITION_REQ_DTO,ZONE1_NOMZONE,FERMIER_ID);
    private static final Zone ZONE2 = new Zone(ZONE2_ID,POSITION, ZONE2_NOMZONE, FERMIER);
    private static final List<Zone> ZONE_LIST = new ArrayList<>(List.of(ZONE1, ZONE2));

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
    @WithMockUser//TODO ajouter autorities
    public void createZoneShouldReturnId(){
        //given
        Mockito.when(daoUtilisateur.findById(FERMIER_ID)).thenReturn(Optional.of(FERMIER));
        Mockito.when(daoZone.save(any(Zone.class))).thenReturn(ZONE1);
        Mockito.when(positionService.zoneCanBeAdded(any(Zone.class))).thenReturn(true);

        //WHEN
        int id = zoneService.create(ZONE1_REQ_DTO);

        //Then
        assertThat(id).isEqualTo(ZONE1_ID);

        ArgumentCaptor<Zone> zoneCaptor = ArgumentCaptor.forClass(Zone.class);
        Mockito.verify(daoZone).save(zoneCaptor.capture()); //Zone captor recup l'objet zone qui a été passé dans le save
        Zone zoneCapture = zoneCaptor.getValue();
        assertThat(zoneCapture.getNomZone()).isEqualTo(ZONE1_NOMZONE);
        assertThat(zoneCapture.getPosition())
                .extracting(
                        Position::getAnchorX,
                        Position::getAnchorY,
                        Position::getRotation
                )
                .containsExactly(
                        POSITION.getAnchorX(),
                        POSITION.getAnchorY(),
                        POSITION.getRotation()
                );
        assertThat(zoneCapture.getFermier()).isNotNull();
        assertThat(zoneCapture.getFermier().getId()).isEqualTo(FERMIER_ID);
        assertThat(zoneCapture.getFermier()).isInstanceOf(Fermier.class);
        Mockito.verify(daoUtilisateur).findById(FERMIER_ID);
    }

    @Test
    @WithMockUser
    public void updateZoneShouldReturnId(){

        Mockito.when(daoZone.save(any(Zone.class))).thenReturn(ZONE1);
        Mockito.when(daoUtilisateur.findById(FERMIER_ID)).thenReturn(Optional.of(FERMIER));
        Mockito.when(positionService.zoneCanBeMoved(any(Zone.class))).thenReturn(true);
        //When
        int id = zoneService.put(ZONE1_REQ_DTO, ZONE1_ID);

        //Then
        assertThat(id).isEqualTo(ZONE1_ID);

        ArgumentCaptor<Zone> zoneCaptor = ArgumentCaptor.forClass(Zone.class);
        Mockito.verify(daoZone).save(zoneCaptor.capture());
        Zone zoneCapture = zoneCaptor.getValue();
        assertThat(zoneCapture.getFermier()).isNotNull();
        assertThat(zoneCapture.getFermier().getId()).isEqualTo(FERMIER_ID);
        assertThat(zoneCapture.getFermier()).isInstanceOf(Fermier.class);
        Mockito.verify(daoUtilisateur).findById(FERMIER_ID);
    }

    @Test
    @WithMockUser
    public void partialUpdateZoneShouldReturnId(){
        Mockito.when(daoZone.save(any(Zone.class))).thenReturn(ZONE1);
        Mockito.when(daoUtilisateur.findById(FERMIER_ID)).thenReturn(Optional.of(FERMIER));
        Mockito.when(daoZone.findById(ZONE1_ID)).thenReturn(Optional.of(ZONE1));
        Mockito.when(positionService.zoneCanBeMoved(any(Zone.class))).thenReturn(true);

        //when
        int id = zoneService.patch(ZONE1_REQ_DTO, ZONE1_ID);

        //Then
        assertThat(id).isEqualTo(ZONE1_ID);
        ArgumentCaptor<Zone> zoneCaptor = ArgumentCaptor.forClass(Zone.class);
        Mockito.verify(daoZone).save(zoneCaptor.capture());
        Zone zoneCapture = zoneCaptor.getValue();
        assertThat(zoneCapture.getFermier()).isNotNull();
        assertThat(zoneCapture.getFermier().getId()).isEqualTo(FERMIER_ID);
        assertThat(zoneCapture.getNomZone()).isEqualTo(ZONE1_NOMZONE);
        assertThat(zoneCapture.getPosition())
                .extracting(
                        Position::getAnchorX,
                        Position::getAnchorY,
                        Position::getRotation
                )
                .containsExactly(
                        POSITION.getAnchorX(),
                        POSITION.getAnchorY(),
                        POSITION.getRotation()
                );

        Mockito.verify(daoUtilisateur).findById(FERMIER_ID);
    }

    private static Stream<Arguments> partielUpdateStream() {
        Position posInitiale = POSITION;
        Position posModifiee = new Position(2, 2, Rotation.DEG_O);

        return Stream.of(

                // Cas 1 : On ne change que le nom (la position doit rester l'initiale)
                Arguments.of(new ZoneRequestDTO( null,NomZone.CUVE, null), NomZone.CUVE, posInitiale),

                // Cas 2 : On ne change que la position (le nom doit rester l'initial)
                Arguments.of(new ZoneRequestDTO( new PositionRequestDTO(2, 2, Rotation.DEG_O),null, null), NomZone.CHAMPS, posModifiee),

                // Cas 3 : On change tout
                Arguments.of(new ZoneRequestDTO( new PositionRequestDTO(2, 2, Rotation.DEG_O),NomZone.CUVE, 5), NomZone.CUVE, posModifiee),
                Arguments.of(new ZoneRequestDTO( new PositionRequestDTO(2, 2, Rotation.DEG_O),NomZone.CUVE, 6), NomZone.CUVE, posModifiee),
                // Cas 4 : On envoie tout à null (rien ne doit changer)
                Arguments.of(new ZoneRequestDTO(null, null, null), NomZone.CHAMPS, posInitiale)
        );
    }

    @ParameterizedTest
    @MethodSource("partielUpdateStream")
    public void parameterizedPartialUpdateZone(ZoneRequestDTO request, NomZone expectedNom, Position expectedPos) {
        // GIVEN
        Zone zoneInitial = new Zone(ZONE1_ID, POSITION, NomZone.CHAMPS, FERMIER);

        Mockito.when(daoZone.findById(ZONE1_ID)).thenReturn(Optional.of(zoneInitial));
        Mockito.when(daoZone.save(any(Zone.class))).thenAnswer(i -> i.getArguments()[0]);
        Mockito.lenient().when(positionService.zoneCanBeMoved(any(Zone.class))).thenReturn(true);
        Mockito.lenient().when(daoUtilisateur.findById(anyInt())).thenReturn(Optional.of(FERMIER));//lenient permet que mockito ne râle pas si daoUtilisateur n'est pas utilisé

        // WHEN
        zoneService.patch(request, ZONE1_ID);

        // THEN
        ArgumentCaptor<Zone> zoneCaptor = ArgumentCaptor.forClass(Zone.class);
        Mockito.verify(daoZone).save(zoneCaptor.capture());
        Zone zoneCapture = zoneCaptor.getValue();

        assertThat(zoneCapture.getNomZone()).isEqualTo(expectedNom);
        assertThat(zoneCapture.getPosition())
                .extracting(
                        Position::getAnchorX,
                        Position::getAnchorY,
                        Position::getRotation
                )
                .containsExactly(
                        expectedPos.getAnchorX(),
                        expectedPos.getAnchorY(),
                        expectedPos.getRotation()
                );
        //Fermier ne doit pas changer
        assertThat(zoneCapture.getFermier()).isNotNull();
        assertThat(zoneCapture.getFermier().getId()).isEqualTo(FERMIER_ID);
    }

    @Test
    public void patchShouldThrow(){
        //GIVEN
        Mockito.when(daoZone.findById(ZONE_ID_NOT_EXIST)).thenReturn(Optional.empty());
        //WHEN + THEN
        assertThatThrownBy(() -> zoneService.patch(ZONE1_REQ_DTO,ZONE_ID_NOT_EXIST))
                .hasMessageContaining(String.valueOf(ZONE_ID_NOT_EXIST))
                .isInstanceOf(ZoneNotFoundException.class);
    }


    @Test
    public void deleteZone(){
        //when
        zoneService.delete(ZONE1_ID);
        //then
        Mockito.verify(daoZone).deleteById(ZONE1_ID);
    }



    @Test
    @WithMockUser
    public void getZoneWithVehiculesShouldContainVehicules(){
        //GIVEN
        LocalDate date = LocalDate.now();
        Vehicule vehicule = new Vehicule(1, TypeVehicule.Utilitaire, date, ZONE1);

        Zone zoneWithVehicule = ZONE1;
        zoneWithVehicule.setVehicules(List.of(vehicule));

        Mockito.when(daoZone.findByIdWithVehicule(ZONE1_ID)).thenReturn(Optional.of(zoneWithVehicule));
        //When
        ZoneWithVehiculesResponseDTO response = zoneService.getZoneWithVehicules(ZONE1_ID);

        assertThat(response.getVehicules().size()).isEqualTo(1);
        assertThat(response.getVehicules())
                .extracting("id","typeVehicule","dateControleTech","delaiAvantControle","zoneId")
                .containsExactly(tuple(1,TypeVehicule.Utilitaire,date,0,ZONE1_ID));

        assertThat(response)
                .extracting(ZoneWithVehiculesResponseDTO::getNomZone,
                        ZoneWithVehiculesResponseDTO::getFermierId,
                        ZoneWithVehiculesResponseDTO::getId)
                .containsExactly(ZONE1_NOMZONE,FERMIER_ID,ZONE1_ID);
    }

    @Test
    public void getZoneByIdWithVehiculeShouldThrow(){
        //GIVEN
        Mockito.when(daoZone.findByIdWithVehicule(ZONE_ID_NOT_EXIST)).thenReturn(Optional.empty());
        //WHEN + THEN
        assertThatThrownBy(() -> zoneService.getZoneWithVehicules(ZONE_ID_NOT_EXIST))
                .hasMessageContaining(String.valueOf(ZONE_ID_NOT_EXIST))
                .isInstanceOf(ZoneNotFoundException.class);
    }

    @Test
    @WithMockUser
    public void getZoneWithRessourcesShouldContainRessources(){
        //GIVEN
        Ressource ressource =  new Ressource(
                1,
                NomRessource.Fraise,
                1,
                new PrixLot(BigDecimal.valueOf(1.00),100,Unite.GRAMME),
                1,
                ZONE1
        );
        Zone zoneWithRessource = ZONE1;
        zoneWithRessource.setRessources(List.of(ressource));

        Mockito.when(daoZone.findByIdWithRessource(ZONE1_ID)).thenReturn(Optional.of(zoneWithRessource));
        //When
        ZoneWithRessourcesResponseDTO response = zoneService.getZoneWithRessources(ZONE1_ID);

        assertThat(response.getRessources().size()).isEqualTo(1);
        assertThat(response.getRessources())
                .extracting("id","nom","quantite","stockMin","zoneId","zoneNom")
                .containsExactly(tuple(1,NomRessource.Fraise,1,1,ZONE1_ID,ZONE1_NOMZONE));

        assertThat(response.getRessources())
                .extracting("prixLot")
                .extracting("prixPar","quantiteLot","unite")
                .containsExactly(tuple(BigDecimal.valueOf(1.00).setScale(2, RoundingMode.HALF_UP),100,Unite.GRAMME));

        assertThat(response)
                .extracting(ZoneWithRessourcesResponseDTO::getNomZone,
                        ZoneWithRessourcesResponseDTO::getFermierId,
                        ZoneWithRessourcesResponseDTO::getId)
                .containsExactly(ZONE1_NOMZONE,FERMIER_ID,ZONE1_ID);
    }

    @Test
    public void getZoneByIdWithRessourceShouldThrow(){
        //GIVEN
        Mockito.when(daoZone.findByIdWithRessource(ZONE_ID_NOT_EXIST)).thenReturn(Optional.empty());
        //WHEN + THEN
        assertThatThrownBy(() -> zoneService.getZoneWithRessources(ZONE_ID_NOT_EXIST))
                .hasMessageContaining(String.valueOf(ZONE_ID_NOT_EXIST))
                .isInstanceOf(ZoneNotFoundException.class);
    }

    @Test
    @WithMockUser
    public void getZoneWithAnimalsShouldContainAnimals(){
        LocalDate now = LocalDate.now();
        //GIVEN
        Animal animal =  new Animal(1,true,now,now,EspeceAnimal.Vache);
        animal.setZone(ZONE1);
        Zone zoneWithAnimal = ZONE1;
        zoneWithAnimal.setAnimals(List.of(animal));

        Mockito.when(daoZone.findByIdWithAnimal(ZONE1_ID)).thenReturn(Optional.of(zoneWithAnimal));
        //When
        ZoneWithAnimalsResponseDTO response = zoneService.getZoneWithAnimals(ZONE1_ID);

        assertThat(response.getAnimals().size()).isEqualTo(1);
        assertThat(response.getAnimals())
                .extracting("id","male","dateNaissance","dateVaccination","espece","zoneId","zoneName")
                .containsExactly(tuple(1,true,now,now,EspeceAnimal.Vache,ZONE1_ID,ZONE1_NOMZONE.name()));


        assertThat(response)
                .extracting(ZoneWithAnimalsResponseDTO::getNomZone,
                        ZoneWithAnimalsResponseDTO::getFermierId,
                        ZoneWithAnimalsResponseDTO::getId)
                .containsExactly(ZONE1_NOMZONE,FERMIER_ID,ZONE1_ID);
    }

    @Test
    public void getZoneByIdWithAnimalsShouldThrow(){
        //GIVEN
        Mockito.when(daoZone.findByIdWithAnimal(ZONE_ID_NOT_EXIST)).thenReturn(Optional.empty());
        //WHEN + THEN
        assertThatThrownBy(() -> zoneService.getZoneWithAnimals(ZONE_ID_NOT_EXIST))
                .hasMessageContaining(String.valueOf(ZONE_ID_NOT_EXIST))
                .isInstanceOf(ZoneNotFoundException.class);
    }

    @Test
    public void findZoneThatStoreRessourcesShouldReturnOptZone(){
        Mockito.when(daoZone.findAll()).thenReturn(ZONE_LIST);
        NomRessource nomRessource = NomRessource.Lait;

        Optional<Zone> result = zoneService.findZoneThatStoreRessources(nomRessource);

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    public void findZoneThatStoreRessourcesShouldReturnEmptyOpt(){
        Mockito.when(daoZone.findAll()).thenReturn(ZONE_LIST);
        NomRessource nomRessource = NomRessource.Fraise;

        Optional<Zone> result = zoneService.findZoneThatStoreRessources(nomRessource);

        assertThat(result.isPresent()).isFalse();
    }



}
