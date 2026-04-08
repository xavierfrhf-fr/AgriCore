package agricore.projet.dto.vehicule.response;

import agricore.projet.model.Vehicule;

public class VehiculeResponseDTO {

    private Integer id;
    private String typeVehicule;
    private String dateControleTech;
    private int delaiAvantControle;
    private Integer Zoneid;

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

    public String getTypeVehicule() {
        return typeVehicule;
    }

    public void setTypeVehicule(String typeVehicule) {
        this.typeVehicule = typeVehicule;
    }

    public String getDateControleTech() {
        return dateControleTech;
    }

    public void setDateControleTech(String dateControleTech) {
        this.dateControleTech = dateControleTech;
    }

    public int getDelaiAvantControle() {
        return delaiAvantControle;
    }

    public void setDelaiAvantControle(int delaiAvantControle) {
        this.delaiAvantControle = delaiAvantControle;
    }

    
    public static VehiculeResponseDTO convert (Vehicule vehciule) {
        VehiculeResponseDTO responseDTO = new VehiculeResponseDTO();
        responseDTO.setId(vehciule.getId());
        responseDTO.setTypeVehicule(vehciule.getTypeVehicule().toString());
        responseDTO.setDateControleTech(vehciule.getDateControleTech().toString());
        responseDTO.setDelaiAvantControle(vehciule.delaiAvantControle());
        return responseDTO;

    }
    
}