package agricore.projet.dto.animal.response;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;

import agricore.projet.model.animal.Animal;
import agricore.projet.model.animal.EspeceAnimal;

public class AnimalResponse {

    private Integer id;
    private boolean male;
    private LocalDate dateNaissance;
    private LocalDate dateVaccination;
    private EspeceAnimal espece;
    private Integer zoneId;
    private String zoneName;
    private String pathSprite;

    public AnimalResponse(Integer id, boolean male, LocalDate dateNaissance, LocalDate dateVaccination, EspeceAnimal espece, Integer zoneId, String zoneName, String pathSprite) {
        this.id = id;
        this.male = male;
        this.dateNaissance = dateNaissance;
        this.dateVaccination = dateVaccination;
        this.espece = espece;
        this.zoneId = zoneId;
        this.zoneName = zoneName;
        this.pathSprite = pathSprite;
    }

    public AnimalResponse() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public LocalDate getDateVaccination() {
        return dateVaccination;
    }

    public void setDateVaccination(LocalDate dateVaccination) {
        this.dateVaccination = dateVaccination;
    }

    public EspeceAnimal getEspece() {
        return espece;
    }

    public void setEspece(EspeceAnimal espece) {
        this.espece = espece;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getPathSprite() {
        return pathSprite;
    }

    public void setPathSprite(String pathSprite) {
        this.pathSprite = pathSprite;
    }
   

    public static AnimalResponse convert(Animal animal) {
        AnimalResponse response = new AnimalResponse();
        BeanUtils.copyProperties(animal, response);
        response.setZoneId(animal.getZone().getId());
        response.setZoneName(animal.getZone().getNomZone().toString());
        response.setPathSprite(animal.getEspece().getPathSprite());
        return response;

    }
}
