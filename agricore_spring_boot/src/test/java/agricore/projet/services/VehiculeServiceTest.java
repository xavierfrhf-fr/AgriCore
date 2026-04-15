package agricore.projet.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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
import agricore.projet.model.Zone;
import agricore.projet.repository.IDAOZone;
import agricore.projet.repository.VehiculeRepository;

@ExtendWith(MockitoExtension.class)
public class VehiculeServiceTest {


    @Mock
    VehiculeRepository vehiculeRepository;

    @Mock
    IDAOZone daoZone;

    @InjectMocks
    VehiculeService vehiculeService;


    @Test
    void shouldFindByIdDTO() {
        
        //given 
        Vehicule v = new Vehicule();
        v.setId(1);
        v.setTypeVehicule(TypeVehicule.Utilitaire);
        v.setDateControleTech(LocalDate.now().plusDays(10));

        //when 
        when(vehiculeRepository.findById(1)).thenReturn(Optional.of(v));
        VehiculeResponseDTO rez = vehiculeService.findByIdDTO(1);

        //then
        assertNotNull(rez);
        assertEquals(1, rez.getId());
        assertEquals(TypeVehicule.Utilitaire, rez.getTypeVehicule());
        assertEquals(LocalDate.now().plusDays(10), rez.getDateControleTech());
    }

    @Test
    void shouldThrowExceptionWhenVehiculeNotFound() {
        //given 

        //when
        when(vehiculeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(VehiculeNotFound.class, () ->  vehiculeService.findByIdDTO(1));
    }

    @Test 
    void shouldCreateVehicule() {
        //given 

        Zone zone = new Zone();
        zone.setId(1);

        VehiculeRequestDTO request = new VehiculeRequestDTO();
        request.setTypeVehicule(TypeVehicule.Utilitaire);
        request.setDateControleTech(LocalDate.of(2026,04,15));
        request.setZoneid(1);

        Vehicule tosave = new Vehicule();
        tosave.setId(1);
        tosave.setTypeVehicule(request.getTypeVehicule());
        tosave.setDateControleTech(request.getDateControleTech());
        tosave.setZone(zone);

        //when
        when(daoZone.findById(1)).thenReturn(Optional.of(zone));
        when(vehiculeRepository.save(any(Vehicule.class))).thenReturn(tosave);

        VehiculeResponseDTO rez = vehiculeService.create(request);

        //then

        assertNotNull(rez);
        assertEquals(1,rez.getId());
        assertEquals(TypeVehicule.Utilitaire, rez.getTypeVehicule());
        assertEquals(LocalDate.of(2026,04,15), rez.getDateControleTech());

    }

    @Test
    void shouldThrowExceptionWhenZoneNotFound() {
        //given 

        VehiculeRequestDTO request = new VehiculeRequestDTO();
        request.setTypeVehicule(TypeVehicule.Utilitaire);
        request.setDateControleTech(LocalDate.of(2026,04,15));
        request.setZoneid(1);

        //when
        when(daoZone.findById(1)).thenReturn(Optional.empty());

        assertThrows(ZoneNotFoundException.class, () -> vehiculeService.create(request));   
    }


    @Test
    void shouldUpdateVehicule() {

        //given 

        Zone zone = new Zone();
        zone.setId(1);
        Vehicule v = new Vehicule();
        v.setId(1);
        v.setTypeVehicule(TypeVehicule.Utilitaire); 
        v.setDateControleTech(LocalDate.of(2026,04,15));
        v.setZone(zone);

        VehiculeRequestDTO request = new VehiculeRequestDTO();
        request.setTypeVehicule(TypeVehicule.Tracteur);
        request.setDateControleTech(LocalDate.of(2026,05,20));
        request.setZoneid(1);
        
        
        Vehicule updated = new Vehicule();
        updated.setId(1);
        updated.setTypeVehicule(request.getTypeVehicule());
        updated.setDateControleTech(request.getDateControleTech());
        updated.setZone(zone);



        //when 

        when(vehiculeRepository.findById(1)).thenReturn(Optional.of(v));
        when(daoZone.findById(1)).thenReturn(Optional.of(zone));
        when(vehiculeRepository.save(any(Vehicule.class))).thenReturn(updated);


        //then 
        VehiculeResponseDTO rez = vehiculeService.update(1, request);
        assertNotNull(rez);
        assertEquals(1, rez.getId());
        assertEquals(TypeVehicule.Tracteur, rez.getTypeVehicule());
        assertEquals(LocalDate.of(2026,05,20), rez.getDateControleTech());

    }

    @Test
    void shouldThrowExceptionWhenUpdatingVehiculeNotFound() {
        //given 

        VehiculeRequestDTO request = new VehiculeRequestDTO();
        request.setTypeVehicule(TypeVehicule.Tracteur);
        request.setDateControleTech(LocalDate.of(2026,05,20));
        request.setZoneid(1);

        //when
        when(vehiculeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(VehiculeNotFound.class, () -> vehiculeService.update(1, request));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingZoneNotFound() {
        //given 

        Vehicule v = new Vehicule();
        v.setId(1);
        v.setTypeVehicule(TypeVehicule.Utilitaire); 
        v.setDateControleTech(LocalDate.of(2026,04,15));

        VehiculeRequestDTO request = new VehiculeRequestDTO();
        request.setTypeVehicule(TypeVehicule.Tracteur);
        request.setDateControleTech(LocalDate.of(2026,05,20));
        request.setZoneid(1);

        //when
        when(vehiculeRepository.findById(1)).thenReturn(Optional.of(v));
        when(daoZone.findById(1)).thenReturn(Optional.empty());

        assertThrows(ZoneNotFoundException.class, () -> vehiculeService.update(1, request));
     }

    @Test
    void shouldDeleteVehicule() {
      
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
