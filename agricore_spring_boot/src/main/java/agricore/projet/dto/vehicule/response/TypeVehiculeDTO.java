package agricore.projet.dto.vehicule.response;

public class TypeVehiculeDTO {
    private String name;
    private int capaciteReservoir;
    private int consoParKm;

    public TypeVehiculeDTO(String name, int capaciteReservoir, int consoParKm) {
        this.name = name;
        this.capaciteReservoir = capaciteReservoir;
        this.consoParKm = consoParKm;
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
}