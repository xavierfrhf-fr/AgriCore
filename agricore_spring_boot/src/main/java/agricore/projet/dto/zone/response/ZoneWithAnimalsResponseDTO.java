package agricore.projet.dto.zone.response;

import agricore.projet.dto.animal.response.AnimalResponse;
import agricore.projet.dto.ressource.response.RessourceResponseDTO;
import agricore.projet.model.NomZone;
import agricore.projet.model.Zone;

import java.util.List;

public class ZoneWithAnimalsResponseDTO {
    private Integer id;
    private PositionResponseDTO position;
    private NomZone nomZone;
    private Integer fermierId;
    private List<AnimalResponse> animals;

    public ZoneWithAnimalsResponseDTO() {
    }

    public ZoneWithAnimalsResponseDTO(Integer id, PositionResponseDTO position, NomZone nomZone, Integer fermierId, List<AnimalResponse> animals) {
        this.id = id;
        this.position = position;
        this.nomZone = nomZone;
        this.fermierId = fermierId;
        this.animals = animals;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public NomZone getNomZone() {
        return nomZone;
    }

    public void setNomZone(NomZone nomZone) {
        this.nomZone = nomZone;
    }

    public PositionResponseDTO getPosition() {
        return position;
    }

    public Integer getFermierId() {
        return fermierId;
    }

    public void setFermierId(Integer fermierId) {
        this.fermierId = fermierId;
    }

    public void setPosition(PositionResponseDTO position) {
        this.position = position;
    }

    public List<AnimalResponse> getAnimals() {
        return animals;
    }

    public void setAnimals(List<AnimalResponse> animals) {
        this.animals = animals;
    }

    public static ZoneWithAnimalsResponseDTO convert (Zone zone){
        ZoneWithAnimalsResponseDTO response = new ZoneWithAnimalsResponseDTO();
        response.setId(zone.getId());
        response.setFermierId(zone.getFermier().getId());
        response.setPosition(PositionResponseDTO.convert(zone.getPosition()));
        response.setNomZone(zone.getNomZone());
        response.setAnimals(zone.getAnimals()
                .stream()
                .map(AnimalResponse::convert)
                .toList());
        return response;
    }

}
