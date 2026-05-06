package agricore.projet.dto.zone.response;

import java.util.List;

import agricore.projet.dto.animal.response.AnimalResponse;
import agricore.projet.model.zone.NomZone;
import agricore.projet.model.zone.Zone;

public class ZoneWithAnimalsResponseDTO extends ZoneResponseDTO {

    private List<AnimalResponse> animals;

    public ZoneWithAnimalsResponseDTO() {
    }

    public ZoneWithAnimalsResponseDTO(Integer id, PositionResponseDTO position, NomZone nomZone, Integer fermierId, List<AnimalResponse> animals) {
        super(id, position, nomZone, fermierId);
        this.animals = animals;
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
        response.setPosition(PositionResponseDTO.convert(zone));
        response.setNomZone(zone.getNomZone());
        response.setAnimals(zone.getAnimals()
                .stream()
                .map(AnimalResponse::convert)
                .toList());
        return response;
    }

}
