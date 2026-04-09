package agricore.projet.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
    void shouldFindById() {
        
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

    
    

}
