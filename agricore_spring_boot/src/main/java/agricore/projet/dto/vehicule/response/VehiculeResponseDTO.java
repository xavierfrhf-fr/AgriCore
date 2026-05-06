package agricore.projet.dto.vehicule.response;

import java.time.LocalDate;

import agricore.projet.model.TypeVehicule;
import agricore.projet.model.Vehicule;

public class VehiculeResponseDTO {

    private Integer id;
    private TypeVehicule typeVehicule;
    private LocalDate dateControleTech;
    private int delaiAvantControle;
    private int carburantActuel;
    private Integer zoneId;
    private String zoneNom;

    public VehiculeResponseDTO(Integer id, TypeVehicule typeVehicule, LocalDate dateControleTech, int delaiAvantControle, int carburantActuel, Integer zoneId, String zoneNom) {
        this.id = id;
        this.typeVehicule = typeVehicule;
        this.dateControleTech = dateControleTech;
        this.delaiAvantControle = delaiAvantControle;
        this.carburantActuel = carburantActuel;
        this.zoneId = zoneId;
        this.zoneNom = zoneNom;
    }

    public VehiculeResponseDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TypeVehicule getTypeVehicule() {
        return typeVehicule;
    }

    public int getCarburantActuel() {
        return carburantActuel;
    }

    public void setCarburantActuel(int carburantActuel) {
        this.carburantActuel = carburantActuel;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneNom() {
        return zoneNom;
    }

    public void setZoneNom(String zoneNom) {
        this.zoneNom = zoneNom;
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
        responseDTO.setCarburantActuel(vehicule.getCarburantActuel());
        responseDTO.setZoneId(vehicule.getZone().getId());
        responseDTO.setZoneNom(vehicule.getZone().getNomZone().getNomAffichage());
        return responseDTO;

    }
    
}