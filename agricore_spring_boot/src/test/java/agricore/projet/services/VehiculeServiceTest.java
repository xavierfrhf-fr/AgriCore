package agricore.projet.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import agricore.projet.dto.vehicule.request.VehiculeRequestDTO;
import agricore.projet.dto.vehicule.response.VehiculeResponseDTO;
import agricore.projet.exception.VehiculeNotFound;
import agricore.projet.exception.ZoneNotFoundException;
import agricore.projet.model.TypeVehicule;
import agricore.projet.model.Vehicule;
import agricore.projet.model.zone.NomZone;
import agricore.projet.model.zone.Zone;
import agricore.projet.repository.IDAOZone;
import agricore.projet.repository.IDAOVehicule;

@ExtendWith(MockitoExtension.class)
public class VehiculeServiceTest {


    @Mock
    IDAOVehicule vehiculeRepository;

    @Mock
    IDAOZone daoZone;

    @InjectMocks
    VehiculeService vehiculeService;


    @Test
    void shouldFindByIdDTO() {
        
        //given 
        Zone zone = new Zone();
        zone.setId(1);
        zone.setNomZone(NomZone.CHAMPS);

        Vehicule v = new Vehicule();  // ← Crée un Vehicule, pas un DTO
        v.setId(1);
        v.setTypeVehicule(TypeVehicule.UTILITAIRE);
        v.setDateControleTech(LocalDate.now().plusDays(10));
        v.setCarburantActuel(50);  // ← Ajoute le carburant
        v.setZone(zone);

        //when 
        when(vehiculeRepository.findById(1)).thenReturn(Optional.of(v));
        VehiculeResponseDTO rez = vehiculeService.findByIdDTO(1);

        //then
        //then
        assertNotNull(rez);
        assertEquals(1, rez.getId());
        assertEquals(TypeVehicule.UTILITAIRE, rez.getTypeVehicule());
        assertEquals(LocalDate.now().plusDays(10), rez.getDateControleTech());
        assertEquals(50, rez.getCarburantActuel());  // ← Vérifie le carburant
        assertEquals(1, rez.getZoneId());  // ← Vérifie la zone ID
        assertEquals("champ", rez.getZoneNom());  // ← Vérifie que le nom est bien mappé
    }

    @Test
    void shouldThrowExceptionWhenVehiculeNotFound() {
        //given 

        //when
        when(vehiculeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(VehiculeNotFound.class, () ->  vehiculeService.findByIdDTO(1));
    }

    @Test
    void shouldFindAllDTO() {
        //given 
        Zone zone = new Zone();
        zone.setId(1);
        zone.setNomZone(NomZone.CHAMPS);

        Vehicule v1 = new Vehicule();
        v1.setId(1);
        v1.setTypeVehicule(TypeVehicule.UTILITAIRE);
        v1.setDateControleTech(LocalDate.now().plusDays(10));
        v1.setCarburantActuel(50);
        v1.setZone(zone);

        Vehicule v2 = new Vehicule();
        v2.setId(2);
        v2.setTypeVehicule(TypeVehicule.TRACTEUR);
        v2.setDateControleTech(LocalDate.now().plusDays(20));
        v2.setCarburantActuel(60);
        v2.setZone(zone);

        //when
        when(vehiculeRepository.findAll()).thenReturn(List.of(v1, v2));

        List<VehiculeResponseDTO> rez = vehiculeService.findAllDTO();

        //then
        assertNotNull(rez);
        assertEquals(2, rez.size());
        assertEquals(1, rez.get(0).getId());
        assertEquals(TypeVehicule.UTILITAIRE, rez.get(0).getTypeVehicule());
        assertEquals(50, rez.get(0).getCarburantActuel());
        assertEquals(2, rez.get(1).getId());
        assertEquals(TypeVehicule.TRACTEUR, rez.get(1).getTypeVehicule());
        assertEquals(60, rez.get(1).getCarburantActuel());

    }

    @Test
    void shouldFindAllDTOEmpty() {
        //given 

        //when
        when(vehiculeRepository.findAll()).thenReturn(List.of());

        //then
        assertThrows(VehiculeNotFound.class, () -> vehiculeService.findAllDTO());

    }   



    @Test 
    void shouldCreateVehicule() {
        //given 

        Zone zone = new Zone();
        zone.setId(1);
        zone.setNomZone(NomZone.CHAMPS);

        VehiculeRequestDTO request = new VehiculeRequestDTO();
        request.setTypeVehicule(TypeVehicule.TRACTEUR);
        request.setDateControleTech(LocalDate.of(2026,05,20));
        request.setZoneId(1);
        request.setCarburantActuel(60);

        Vehicule tosave = new Vehicule();
        tosave.setId(1);
        tosave.setTypeVehicule(request.getTypeVehicule());
        tosave.setDateControleTech(request.getDateControleTech());
        tosave.setCarburantActuel(request.getCarburantActuel());
        tosave.setZone(zone);

        //when
        when(daoZone.findById(1)).thenReturn(Optional.of(zone));
        when(vehiculeRepository.save(any(Vehicule.class))).thenReturn(tosave);

        VehiculeResponseDTO rez = vehiculeService.create(request);

        //then

        assertNotNull(rez);
        assertEquals(1,rez.getId());
        assertEquals(TypeVehicule.TRACTEUR, rez.getTypeVehicule());
        assertEquals(LocalDate.of(2026,05,20), rez.getDateControleTech());
        assertEquals(60, rez.getCarburantActuel());

    }

    @Test
    void shouldThrowExceptionWhenZoneNotFound() {
        //given 

        VehiculeRequestDTO request = new VehiculeRequestDTO();
        request.setTypeVehicule(TypeVehicule.UTILITAIRE);
        request.setDateControleTech(LocalDate.of(2026,04,15));
        request.setZoneId(1);
        request.setCarburantActuel(50);

        //when
        when(daoZone.findById(1)).thenReturn(Optional.empty());

        assertThrows(ZoneNotFoundException.class, () -> vehiculeService.create(request));   
    }


    @Test
    void shouldUpdateVehicule() {

        //given 

        Zone zone = new Zone();
        zone.setId(1);
        zone.setNomZone(NomZone.CHAMPS);
        Vehicule v = new Vehicule();
        v.setId(1);
        v.setTypeVehicule(TypeVehicule.UTILITAIRE); 
        v.setDateControleTech(LocalDate.of(2026,04,15));
        v.setCarburantActuel(50);
        v.setZone(zone);

        VehiculeRequestDTO request = new VehiculeRequestDTO();
        request.setTypeVehicule(TypeVehicule.TRACTEUR);
        request.setDateControleTech(LocalDate.of(2026,05,20));
        request.setZoneId(1);
        request.setCarburantActuel(60);
        
        
        Vehicule updated = new Vehicule();
        updated.setId(1);
        updated.setTypeVehicule(request.getTypeVehicule());
        updated.setDateControleTech(request.getDateControleTech());
        updated.setCarburantActuel(request.getCarburantActuel());
        updated.setZone(zone);



        //when 

        when(vehiculeRepository.findById(1)).thenReturn(Optional.of(v));
        when(daoZone.findById(1)).thenReturn(Optional.of(zone));
        when(vehiculeRepository.save(any(Vehicule.class))).thenReturn(updated);


        //then 
        VehiculeResponseDTO rez = vehiculeService.update(1, request);
        assertNotNull(rez);
        assertEquals(1, rez.getId());
        assertEquals(TypeVehicule.TRACTEUR, rez.getTypeVehicule());
        assertEquals(LocalDate.of(2026,05,20), rez.getDateControleTech());

    }

    @Test
    void shouldThrowExceptionWhenUpdatingVehiculeNotFound() {
        //given 

        VehiculeRequestDTO request = new VehiculeRequestDTO();
        request.setTypeVehicule(TypeVehicule.TRACTEUR);
        request.setDateControleTech(LocalDate.of(2026,05,20));
        request.setZoneId(1);

        //when
        when(vehiculeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(VehiculeNotFound.class, () -> vehiculeService.update(1, request));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingZoneNotFound() {
        //given 
        Zone zone = new Zone();
        zone.setId(1);

        Vehicule v = new Vehicule();
        v.setId(1);
        v.setTypeVehicule(TypeVehicule.UTILITAIRE); 
        v.setDateControleTech(LocalDate.of(2026,04,15));
        v.setCarburantActuel(50);
        v.setZone(zone);

        VehiculeRequestDTO request = new VehiculeRequestDTO();
        request.setTypeVehicule(TypeVehicule.TRACTEUR);
        request.setDateControleTech(LocalDate.of(2026,05,20));
        request.setZoneId(1);
        request.setCarburantActuel(60);
        //when
        when(vehiculeRepository.findById(1)).thenReturn(Optional.of(v));
        when(daoZone.findById(1)).thenReturn(Optional.empty());

        assertThrows(ZoneNotFoundException.class, () -> vehiculeService.update(1, request));
     }

    @Test
    void shouldDeleteVehicule() {
        Vehicule v = new Vehicule();
        v.setId(1);
        
        //given
        when(vehiculeRepository.existsById(1)).thenReturn(true);
        // when
        vehiculeService.delete(1);

        // then
        
        verify(vehiculeRepository).deleteById(1);  

    }

    @Test
    void shouldThrowExceptionWhenDeletingVehiculeNotFound() {
        //given 

        //when
        when(vehiculeRepository.existsById(1)).thenReturn(false);
        
        //then
        assertThrows(VehiculeNotFound.class, () -> vehiculeService.delete(1));
        //verify(vehiculeRepository).deleteById(1);  
    }

    
    

}
