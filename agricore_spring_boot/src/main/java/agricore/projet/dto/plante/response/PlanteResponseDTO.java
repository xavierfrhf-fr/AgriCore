package agricore.projet.dto.plante.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import agricore.projet.dto.data.RessourceDataDTO;
import agricore.projet.model.EspecePlante;
import agricore.projet.model.Plante;
import agricore.projet.model.ressource.NomRessource;

//apres creation de la plante, cette classe permet de renvoyer proprement les donnees a l'utilisateur quand celui-ci cherche la plante
public record PlanteResponseDTO(Integer id,
								LocalDateTime datePlantation,
								EspecePlante espece,
								String nomAffichage,
								int zoneId,
								LocalDateTime dernierUpdate,
								double humidite,
								double croissance,
								boolean mature,
								String pathSprite,
								int production,
								double croissanceParSec,
								double consoEauParMin,
								RessourceDataDTO ressource) {

	public static PlanteResponseDTO convert (Plante plante) {
		return new PlanteResponseDTO(
				plante.getId(),
				plante.getDatePlantation(),
				plante.getEspece(),
				plante.getEspece().getNomAffichage(),
				plante.getZone().getId(),
				plante.getDernierUpdate(),
				plante.getHumidite(),
				plante.getCroissance(),
				plante.isMature(),
				plante.getEspece().getPathSprite(),
				plante.getEspece().getQuantite(),
				plante.getCroissanceParSecond(),
				plante.getEspece().getConsommationEauParMin(),
				RessourceDataDTO.convert(plante.getEspece().getRessourceProduite())
		);
	}
}
