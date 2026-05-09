package agricore.projet.dto.vehicule.response;

public class TypeVehiculeDTO {
    private String name;
    private int capaciteReservoir;
    private int consoParKm;
    private String pathSprite;

    public TypeVehiculeDTO() {
    }

    public TypeVehiculeDTO(String name, int capaciteReservoir, int consoParKm) {
        this.name = name;
        this.capaciteReservoir = capaciteReservoir;
        this.consoParKm = consoParKm;
    }

    public TypeVehiculeDTO(String name, int capaciteReservoir, int consoParKm, String pathSprite) {
        this.name = name;
        this.capaciteReservoir = capaciteReservoir;
        this.consoParKm = consoParKm;
        this.pathSprite = pathSprite;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getCapaciteReservoir() {
        return capaciteReservoir;
    }

    public int getConsoParKm() {
        return consoParKm;
    }

    public void setPathSprite(String pathSprite) {
        this.pathSprite = pathSprite;
    }

    public void setConsoParKm(int consoParKm) {
        this.consoParKm = consoParKm;
    }

    public void setCapaciteReservoir(int capaciteReservoir) {
        this.capaciteReservoir = capaciteReservoir;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPathSprite() {
        return pathSprite;
    }
}