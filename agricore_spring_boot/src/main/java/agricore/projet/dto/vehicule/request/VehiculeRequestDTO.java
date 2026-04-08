package agricore.projet.dto.vehicule.request;

import java.time.LocalDate;

import agricore.projet.model.TypeVehicule;

public class VehiculeRequestDTO {

    private TypeVehicule typeVehicule;
    private LocalDate dateControleTech;
    private Integer Zoneid;
    

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