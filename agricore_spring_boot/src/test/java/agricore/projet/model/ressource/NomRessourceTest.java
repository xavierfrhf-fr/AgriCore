package agricore.projet.model.ressource;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import agricore.projet.model.ressource.NomRessource;
import agricore.projet.model.zone.NomZone;

public class NomRessourceTest {

    @ParameterizedTest
    @EnumSource(value = NomZone.class, names = { "SILO", "CUVE", "STOCK_DE_FRUIT", "CHAMBRE_FROIDE" })
    public void isZoneUniqueShouldReturnTrueForStorageZones(NomZone zone) {
        assertThat(zone.isZoneUnique()).isTrue();
    }

    @ParameterizedTest
    @EnumSource(value = NomZone.class, names = { "CHAMPS", "POULAILLER", "ETABLE" })
    public void isZoneUniqueShouldReturnFalseForNonStorageZones(NomZone zone) {
        assertThat(zone.isZoneUnique()).isFalse();
    }

    @ParameterizedTest
    @EnumSource(NomRessource.class)
    public void everyRessourceShouldHaveAZone(NomRessource ressource) {
        assertThat(ressource.getZoneStockage()).isNotNull();
    }

    @ParameterizedTest
    @EnumSource(NomRessource.class)
    public void everyRessourceZoneShouldBeUnique(NomRessource ressource) {
        assertThat(ressource.getZoneStockage().isZoneUnique()).isTrue();
    }

}