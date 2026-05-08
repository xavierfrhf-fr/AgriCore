package agricore.projet.dto.data;

import agricore.projet.model.EspecePlante;

import java.util.List;

public record PlanteDataDTO(
        String nomEnum,
        String nomAffichage,
        int tempsPousseMinute,//tempsPousseMinute
        double consommationEauParMin,//Conso eau par sec
        String vehiculeRequis,
        boolean isCreatable,
        int numberCreatable,
        boolean isProductStorable,
        boolean isVehiculeAvailable,
        String pathSprite,
        int production,
        ZoneDataDTO zone,
        RessourceDataDTO ressource
        ) {
    public static PlanteDataDTO convert(EspecePlante especePlante, int numberCreatable, boolean isProductStorable, boolean isVehiculeAvailable) {
        return new PlanteDataDTO(
                especePlante.name(),
                especePlante.getNomAffichage(),
                especePlante.getTempsPousseMinute(),
                especePlante.getConsommationEauParMin(),
                especePlante.getVehiculeRequis().name(),
                (numberCreatable>0),
                numberCreatable,
                isProductStorable,
                isVehiculeAvailable,
                especePlante.getPathSprite(),
                especePlante.getQuantite(),
                ZoneDataDTO.from(especePlante.getAllowedZone()),
                RessourceDataDTO.convert(especePlante.getRessourceProduite())
        );
    }
}
