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
        ) {}
