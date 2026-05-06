package agricore.projet.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import agricore.projet.model.EspecePlante;
import agricore.projet.model.Plante;
import agricore.projet.model.TypeVehicule;
import agricore.projet.model.Vehicule;
import agricore.projet.model.animal.Animal;
import agricore.projet.model.animal.EspeceAnimal;
import agricore.projet.model.ressource.NomRessource;
import agricore.projet.model.ressource.Ressource;
import agricore.projet.model.zone.NomZone;
import agricore.projet.model.zone.Zone;
import agricore.projet.model.zone.position.Position;
import agricore.projet.repository.IDAORessource;

@ExtendWith(MockitoExtension.class)
public class VehiculeServiceCarburantTest {

    @InjectMocks
    private VehiculeService vehiculeService;
 
    @Mock
    private TransformationService transformationService;
 
    @Mock
    private IDAORessource daoRessource;
 
    private Vehicule vehicule;
    private Zone zone;
    private Ressource carburant;
    private Plante plante;
    private Animal animal;
    private EspecePlante especePlante = EspecePlante.Blé;
    private EspeceAnimal especeAnimal = EspeceAnimal.VACHE;
 
    @BeforeEach
    void setUp() {
        // Zone
        Position pos = new Position();
        zone = new Zone(pos, NomZone.CHAMPS, null);
 
        // Setup Vehicule
        LocalDate date = LocalDate.parse("2026-05-05");
        vehicule = new Vehicule(TypeVehicule.UTILITAIRE, date, zone, 50);
        vehicule.setCarburantActuel(50); // 50L actuellement
        
        // Setup Ressource (carburant)
        carburant = new Ressource();
        carburant.setNom(NomRessource.ESSENCE);
        carburant.setQuantite(50);
 
        // Setup Plante
        plante = new Plante();
        plante.setEspece(especePlante);
        plante.setZone(zone);
 
        // Setup Animal
        animal = new Animal();
        animal.setEspece(especeAnimal);
    }

    // ===================================
    // Tests : besoinCarburant()
    // ===================================
 
    @Test
    @DisplayName("besoinCarburant - Retourne true quand carburant suffisant")
    void testBesoinCarburantSuffisant() {
        
        int distanceKm = 3; // 3 * 14 = 42
 
        assertTrue(vehiculeService.besoinCarburant(vehicule, distanceKm));
    }
 
    @Test
    @DisplayName("besoinCarburant - Retourne false quand carburant insuffisant")
    void testBesoinCarburantInsuffisant() {
        
        vehicule.setCarburantActuel(40);
        int distanceKm = 3; // 3 * 14 = 42
 
        assertFalse(vehiculeService.besoinCarburant(vehicule, distanceKm));
    }
 
    @Test
    @DisplayName("besoinCarburant - Retourne true avec carburant exact")
    void testBesoinCarburantExact() {
        vehicule.setTypeVehicule(TypeVehicule.UTILITAIRE);
        vehicule.setCarburantActuel(28);
        int distanceKm = 2; // 2 * 14 = 28
 
        assertTrue(vehiculeService.besoinCarburant(vehicule, distanceKm));
    }
 
    @Test
    @DisplayName("besoinCarburant - Retourne true avec distance 0")
    void testBesoinCarburantDistance0() {
        vehicule.setCarburantActuel(0);
        int distanceKm = 0;
 
        assertTrue(vehiculeService.besoinCarburant(vehicule, distanceKm));
    }
 
    @ParameterizedTest
    @CsvSource({
        "50, 1, true",   // 50 >= 14
        "14, 1, true",   // 14 >= 14
        "13, 1, false",  // 13 < 14
        "0, 0, true",    // 0 >= 0
        "27, 2, false"   // 27 < 28
    })
    @DisplayName("besoinCarburant - Tests paramétrés")
    void testBesoinCarburantParametres(int carburantActuel, int distanceKm, boolean expected) {
        vehicule.setCarburantActuel(carburantActuel);
 
        assertEquals(expected, vehiculeService.besoinCarburant(vehicule, distanceKm));
    }

    
    // ===================================
    // Tests : consommerCarburant()
    // ===================================
 

    @Test
    @DisplayName("consommerCarburant - Réduit le carburant lorsque suffisant")
    void testConsommerCarburantSuffisant() {
        vehicule.setCarburantActuel(42);
        int distanceKm = 3; // 3 * 14 = 42

        vehiculeService.consommerCarburant(vehicule, distanceKm);

        assertEquals(0, vehicule.getCarburantActuel());
    }

    @Test
    @DisplayName("consommerCarburant - Lève une exception si carburant insuffisant")
    void testConsommerCarburantInsuffisant() {
        vehicule.setTypeVehicule(TypeVehicule.UTILITAIRE);
        vehicule.setCarburantActuel(40);
        int distanceKm = 3; // 3 * 14 = 42

        assertThrows(RuntimeException.class, () -> vehiculeService.consommerCarburant(vehicule, distanceKm));
    }


    // ===================================
    // Tests : fairePlein()
    // ===================================
 

    @Test
    @DisplayName("fairePlein - Remplit le réservoir si le stock est suffisant")
    void testFairePleinAvecStockSuffisant() {

        when(daoRessource.findByNom(NomRessource.ESSENCE)).thenReturn(java.util.Optional.of(carburant));

        vehiculeService.fairePlein(vehicule, carburant);

        assertEquals(vehicule.getTypeVehicule().getCapaciteReservoir(), vehicule.getCarburantActuel());
        assertEquals(0, carburant.getQuantite()); // 50 - 50 = 0
        verify(daoRessource).save(carburant);

    }

    @Test
    @DisplayName("fairePlein - Lève une exception si le stock est insuffisant")
    void testFairePleinAvecStockInsuffisant() {
        
        
        carburant.setQuantite(10); // Stock insuffisant, manque = 100 - 90 = 10

        assertThrows(RuntimeException.class, () -> vehiculeService.fairePlein(vehicule, carburant));
    }

    @Test
    @DisplayName("recolterPlante - Récolte avec le bon véhicule et réduit le carburant")
    void testRecolterPlanteAvecBonVehicule() {
        Zone petitZone = new Zone(new Position(), NomZone.POULAILLER, null); // 4x2 = 8 cellules
        plante.setZone(petitZone);
        plante.setEspece(EspecePlante.Fraisier); // nécessite PICKUP

        vehicule.setTypeVehicule(TypeVehicule.PICKUP);
        vehicule.setCarburantActuel(250); // 8*2=16 km * 13 (conso par km pickup) = 208

        String result = vehiculeService.recolterPlante(plante, vehicule);

        assertEquals("Le véhicule est aller chercher la récolte ! ", result);
        assertEquals(42, vehicule.getCarburantActuel());
    }

    @Test
    @DisplayName("recolterPlante - Lève une exception avec le mauvais véhicule")
    void testRecolterPlanteAvecMauvaisVehicule() {
        plante.setZone(zone);
        plante.setEspece(EspecePlante.Fraisier);
        vehicule.setTypeVehicule(TypeVehicule.TRACTEUR);

        assertThrows(RuntimeException.class, () -> vehiculeService.recolterPlante(plante, vehicule));
    }

    @Test
    @DisplayName("acheterAnimal - Achète l'animal avec le bon véhicule et réduit le carburant")
    void testAcheterAnimalAvecBonVehicule() {
        vehicule.setTypeVehicule(TypeVehicule.TRACTEUR);
        vehicule.setCarburantActuel(200);
        animal.setEspece(EspeceAnimal.VACHE);

        String result = vehiculeService.acheterAnimal(animal, vehicule);

        assertEquals("Le véhicule est aller chercher l'animal ! ", result);
        assertEquals(140, vehicule.getCarburantActuel());
    }

    @Test
    @DisplayName("acheterAnimal - Lève une exception avec le mauvais véhicule")
    void testAcheterAnimalAvecMauvaisVehicule() {
        vehicule.setTypeVehicule(TypeVehicule.PICKUP);
        animal.setEspece(EspeceAnimal.VACHE);

        assertThrows(RuntimeException.class, () -> vehiculeService.acheterAnimal(animal, vehicule));
    }
}
