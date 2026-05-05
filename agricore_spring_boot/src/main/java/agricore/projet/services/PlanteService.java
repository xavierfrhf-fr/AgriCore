package agricore.projet.services;

//permet de decider comment on cree une plante
import java.util.List;

import org.springframework.stereotype.Service;

import agricore.projet.dto.plante.request.PlanteRequestDTO;
import agricore.projet.dto.plante.response.PlanteResponseDTO;
import agricore.projet.exception.ZoneNotFoundException;
import agricore.projet.model.Plante;
import agricore.projet.model.zone.Zone;
import agricore.projet.repository.IDAOPlante;
import agricore.projet.repository.IDAOZone;

@Service
public class PlanteService {

	private final IDAOPlante daoPlante;
	private final IDAOZone daoZone;

	PlanteService(IDAOPlante daoPlante, IDAOZone daoZone) {
		this.daoPlante = daoPlante;
		this.daoZone = daoZone;
	}

	// le controller a besoin de la methode findAll
	// on veut parcourir la liste
	public List<PlanteResponseDTO> findAll() {
		return daoPlante.findAll()// recupere ttes les plantes dans la base
				.stream()// permet de pouvoir par la suite tranformer les donnees
				.map(PlanteResponseDTO::convert) // transforme chaque plante en PlanteResponse - meme chose que
													// .map(plante ->PlanteResponseDTO.convert(plante))
				.toList();// permet de produire une liste
	}

	public PlanteResponseDTO findById(Integer id) {

		return PlanteResponseDTO.convert(daoPlante.findById(id).orElse(null));
	}

	public PlanteResponseDTO insert(PlanteRequestDTO plante) {

        Plante p = new Plante();

        p.setDateNaissance(plante.getDateNaissance());
        p.setDateVaccination(plante.getDateVaccination());
        p.setEspece(plante.getEspece());

        Zone zone = daoZone
                .findById(plante.getZoneId())
                .orElseThrow(
                        ()-> new ZoneNotFoundException(plante.getZoneId())
                );

        if (isPlanteAllowedInZone(plante.getEspece(), zone.getNomZone())){
            p.setZone(zone);
        }

        a.setMale(plante.isMale());

        return PlanteResponseDTO.convert(daoPlante.save(a));
    }


}
