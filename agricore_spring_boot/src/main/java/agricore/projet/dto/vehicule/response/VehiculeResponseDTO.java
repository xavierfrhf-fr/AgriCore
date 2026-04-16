package agricore.projet.dto.vehicule.response;

import java.time.LocalDate;

import agricore.projet.model.TypeVehicule;
import agricore.projet.model.Vehicule;

public class VehiculeResponseDTO {

    private Integer id;
    private TypeVehicule typeVehicule;
    private LocalDate dateControleTech;
    private int delaiAvantControle;
    private Integer Zoneid;

    public VehiculeResponseDTO(Integer id, TypeVehicule typeVehicule, LocalDate dateControleTech, int delaiAvantControle, Integer zoneid) {
        this.id = id;
        this.typeVehicule = typeVehicule;
        this.dateControleTech = dateControleTech;
        this.delaiAvantControle = delaiAvantControle;
        Zoneid = zoneid;
    }

    public VehiculeResponseDTO() {}

    public Integer getZoneid() {
        return Zoneid;
    }

    public void setZoneid(Integer zoneid) {
        Zoneid = zoneid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public int getDelaiAvantControle() {
        return delaiAvantControle;
    }

    public void setDelaiAvantControle(int delaiAvantControle) {
        this.delaiAvantControle = delaiAvantControle;
    }

    
    public static VehiculeResponseDTO convert (Vehicule vehicule) {
        VehiculeResponseDTO responseDTO = new VehiculeResponseDTO();
        responseDTO.setId(vehicule.getId());
        responseDTO.setTypeVehicule(vehicule.getTypeVehicule());
        responseDTO.setDateControleTech(vehicule.getDateControleTech());
        responseDTO.setDelaiAvantControle(vehicule.delaiAvantControle());
        return responseDTO;

    }
    
}