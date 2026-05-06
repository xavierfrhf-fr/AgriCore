package agricore.projet.dto.animal.response;

import java.time.LocalDate;

import agricore.projet.model.animal.Animal;
import agricore.projet.model.animal.EspeceAnimal;
import agricore.projet.model.ressource.NomRessource;

public class AnimalResponse {

    private Integer id;
    private EspeceAnimal espece;
    private boolean male;
    private LocalDate dateNaissance;
    private LocalDate dateVaccination;
    private int delaisVaccination;
    private String pathSprite;
    private String nomAffichage;
    private String zone;
    private String zonePathSprite;
    private String produit;
    private String produitPathSprite;

    public AnimalResponse(Integer id, EspeceAnimal espece, boolean male, LocalDate dateNaissance, LocalDate dateVaccination,
            int delaisVaccination,  String pathSprite,
            String nomAffichage, String zone, String zonePathSprite, String produit, String produitPathSprite) {
        this.id = id;      
          this.espece = espece;
        this.male = male;
        this.dateNaissance = dateNaissance;
        this.dateVaccination = dateVaccination;
        this.delaisVaccination = delaisVaccination;
        this.pathSprite = pathSprite;
        this.nomAffichage = nomAffichage;
        this.zone = zone;
        this.zonePathSprite = zonePathSprite;
        this.produit = produit;
        this.produitPathSprite = produitPathSprite;
    }

    public AnimalResponse() {
    }

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

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getPathSprite() {
        return pathSprite;
    }

    public void setPathSprite(String pathSprite) {
        this.pathSprite = pathSprite;
    }

    public String getNomAffichage() {
        return nomAffichage;
    }

    public void setNomAffichage(String nomAffichage) {
        this.nomAffichage = nomAffichage;
    }

    public int getDelaisVaccination() {
        return delaisVaccination;
    }

    public void setDelaisVaccination(int delaisVaccination) {
        this.delaisVaccination = delaisVaccination;
    }

    public String getProduit() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }

    public String getProduitPathSprite() {
        return produitPathSprite;
    }

    public void setProduitPathSprite(String produitPathSprite) {
        this.produitPathSprite = produitPathSprite;
    }

    public String getZonePathSprite() {
        return zonePathSprite;
    }

    public void setZonePathSprite(String zonePathSprite) {
        this.zonePathSprite = zonePathSprite;
    }

    public static AnimalResponse convert(Animal animal) {
        AnimalResponse response = new AnimalResponse();
        response.setId(animal.getId());
        response.setMale(animal.isMale());
        response.setDateNaissance(animal.getDateNaissance());
        response.setDateVaccination(animal.getDateVaccination());
        response.setEspece(animal.getEspece());
        response.setZone(animal.getZone().getNomZone().getNomAffichage());
        response.setZonePathSprite(animal.getZone().getNomZone().getPathSprite());
        response.setPathSprite(animal.getEspece().getDimorphisme().getPathSprite(animal.isMale()));
        response.setNomAffichage(animal.getEspece().getDimorphisme().getNomAffichage(animal.isMale()));
        response.setDelaisVaccination(animal.delaiAvantVaccin());
        NomRessource ressource = animal.getEspece().getDimorphisme().getNomRessource(animal.isMale());
        if (ressource != null) {
            response.setProduit(ressource.getNomAffichage());
            response.setProduitPathSprite(ressource.getPathSprite());
        } else {
            response.setProduit("");
            response.setProduitPathSprite("");
        }
        return response;
    }

}
