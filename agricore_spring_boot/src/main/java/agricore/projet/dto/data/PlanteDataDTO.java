package agricore.projet.dto.data;

import agricore.projet.model.EspecePlante;

import java.util.List;

public record PlanteDataDTO(
        String nomEnum,
        String nomAffichage,
        int tempsPousseMois,
        double consommationEauParMin,
        String allowedZone,
        String vehiculeRequis,
        boolean isCreatable
        ) {
    public static PlanteDataDTO convert(EspecePlante especePlante, boolean isCreatable) {
        return new PlanteDataDTO(
                especePlante.name(),
                especePlante.getNomAffichage(),
                especePlante.getTempsPousseMois(),
                especePlante.getConsommationEauParMin(),
                especePlante.getAllowedZone().name(),
                especePlante.getVehiculeRequis().name(),
                isCreatable
        );
    }
}
