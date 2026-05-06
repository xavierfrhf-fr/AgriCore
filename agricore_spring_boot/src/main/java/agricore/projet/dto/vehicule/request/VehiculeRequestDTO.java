package agricore.projet.dto.vehicule.request;

import java.time.LocalDate;

import agricore.projet.model.TypeVehicule;
import jakarta.validation.constraints.NotNull;

public class VehiculeRequestDTO {

    
    private TypeVehicule typeVehicule;
    @NotNull
    private LocalDate dateControleTech;
    @NotNull
    private Integer zoneId;
    @NotNull
    private Integer carburantActuel;

    public VehiculeRequestDTO(TypeVehicule typeVehicule, LocalDate dateControleTech, Integer zoneId, Integer carburantActuel) {
        this.typeVehicule = typeVehicule;
        this.dateControleTech = dateControleTech;
        this.zoneId = zoneId;
        this.carburantActuel = carburantActuel;
    }

    public VehiculeRequestDTO() {}

    public TypeVehicule getTypeVehicule() {
        return typeVehicule;
    }

    public void setTypeVehicule(TypeVehicule typeVehicule) {
        this.typeVehicule = typeVehicule;
    }

    public LocalDate getDateControleTech() {
        return dateControleTech;
    }

    public void setDateControleTech(LocalDate dateControleTech) {
        this.dateControleTech = dateControleTech;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public Integer getCarburantActuel() {
        return carburantActuel;
    }

    public void setCarburantActuel(Integer carburantActuel) {
        this.carburantActuel = carburantActuel;
    }
   

}