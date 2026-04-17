package agricore.projet.dto.vehicule.request;

import java.time.LocalDate;

import agricore.projet.model.TypeVehicule;
import jakarta.validation.constraints.NotNull;

public class VehiculeRequestDTO {

    @NotNull
    private TypeVehicule typeVehicule;
    @NotNull
    private LocalDate dateControleTech;
    @NotNull
    private Integer Zoneid;

    public VehiculeRequestDTO(TypeVehicule typeVehicule, LocalDate dateControleTech, Integer zoneid) {
        this.typeVehicule = typeVehicule;
        this.dateControleTech = dateControleTech;
        Zoneid = zoneid;
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

    public Integer getZoneid() {
        return Zoneid;
    }

    public void setZoneid(Integer zoneid) {
        Zoneid = zoneid;
    }
   

}