package agricore.projet.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
//permet de decider comment on cree une plante
import java.util.List;

import agricore.projet.dto.MessageDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import agricore.projet.dto.plante.request.PlanteRequestDTO;
import agricore.projet.dto.plante.response.PlanteResponseDTO;
import agricore.projet.exception.PlanteNonAutoriseDansZoneException;
import agricore.projet.exception.PlanteNotFoundException;
import agricore.projet.exception.ZoneNotFoundException;
import agricore.projet.model.EspecePlante;
import agricore.projet.model.Plante;
import agricore.projet.model.zone.NomZone;
import agricore.projet.model.zone.Zone;
import agricore.projet.repository.IDAOPlante;
import agricore.projet.repository.IDAOZone;

@Service
public class PlanteService {

	private final IDAOPlante daoPlante;
	private final IDAOZone daoZone;
	private final TransformationService transformationService;

	PlanteService(IDAOPlante daoPlante, IDAOZone daoZone, TransformationService transformationService) {
		this.daoPlante = daoPlante;
		this.daoZone = daoZone;
		this.transformationService = transformationService;
	}

	public boolean isPlanteAutoriseDansZone(EspecePlante espece, NomZone nomZone){
		if (!espece.getAllowedZone().equals(nomZone)){
            throw new PlanteNonAutoriseDansZoneException(nomZone, espece);
        }else{
            return true;
        }
	}

	// le controller a besoin de la methode findAll
	// on veut parcourir la liste
	public List<PlanteResponseDTO> findAll() {
		List<Plante> plantes = daoPlante.findAll();
		for (Plante plante : plantes){
			plante.updateHumidite();
			daoPlante.save(plante);
		}

		return plantes.stream()			// recupere ttes les plantes dans la base
							// permet de pouvoir par la suite tranformer les donnees
				.map(PlanteResponseDTO::convert) 		// transforme chaque plante en PlanteResponse - meme chose que
													// .map(plante ->PlanteResponseDTO.convert(plante))
				.toList();// permet de produire une liste
	}

	public PlanteResponseDTO findById(Integer id) {
		return PlanteResponseDTO.convert(daoPlante.findById(id).orElseThrow(() -> new PlanteNotFoundException(id)));
	}

	public PlanteResponseDTO insert(PlanteRequestDTO plante) {

        Plante p = new Plante();

        p.setDatePlantation(LocalDateTime.now());

        p.setEspece(plante.getEspece());
		p.setCroissance(0.);
		p.setHumidite(100);
		p.setMature(false);
		p.setDernierUpdate(LocalDateTime.now());

        Zone zone = daoZone
				.findByName(p.getEspece().getAllowedZone())
				.stream()
				.filter(z -> z.getPlante() == null)
				.findFirst()
				.orElseThrow(() -> new ZoneNotFoundException(p.getEspece().getAllowedZone()));

		p.setZone(zone);

        return PlanteResponseDTO.convert(daoPlante.save(p));
    }
/*
	public PlanteResponseDTO update(Integer id, PlanteRequestDTO plante) {
	//Ne pas utiliser
		Plante p =daoPlante.findById(id).orElse(null);

		if(p == null) {
			throw new PlanteNotFoundException(id);
		}

		p.setDatePlantation(LocalDate.now());
        p.setDateRecolte(p.getDatePlantation().plusMonths(plante.getEspece().getTempsPousseMois()));
        p.setEspece(plante.getEspece());
		p.setHumidite(100);
		p.setDernierUpdate(LocalDateTime.now());

		Zone zone = daoZone
                .findById(plante.getZoneId())
                .orElseThrow(
                        ()-> new ZoneNotFoundException(plante.getZoneId())
                );

        if (isPlanteAutoriseDansZone(plante.getEspece(), zone.getNomZone())){
            p.setZone(zone);
        }

        return PlanteResponseDTO.convert(daoPlante.save(p));
	}

 */

	public void deleteById(Integer id) {
		daoPlante.deleteById(id);
	}

	@Transactional
	public void arroser(Integer id) {
		Plante p =daoPlante.findById(id).orElseThrow(()->new PlanteNotFoundException(id));
		p.updateHumidite();
		p.arroser();
		//this.daoPlante.save(p);
	}

	@Transactional
    public MessageDTO recolter(Integer id) {
		Plante p = daoPlante.findById(id).orElseThrow(()->new PlanteNotFoundException(id));
		p.updateHumidite();

		if (!p.isMature()){
			System.out.println("Plante pas mature " + p.getCroissance());
			return new MessageDTO("Plante pas encore mature",false);
		}

		try {
			System.out.println(p.getCroissance());
			this.transformationService.createRessourceIfNotExist(p.getEspece().getRessourceProduite());
			System.out.println("OK");
		}catch (ZoneNotFoundException e) {
			return new MessageDTO("Zone "+p.getEspece().getRessourceProduite().getZoneStockage().getNomAffichage()+", manquante pour le stockage de "+p.getEspece().getRessourceProduite().getNomAffichage(), false);
		}

		//ICI VERIF VEHICULE (stp essaye de récupérer automatiquement le vehicule (et si plusieurs vehicules identiques prendre celui qui a le + de carburant))
		/*
		if (false){
			return new MessageDTO(p.getEspece().getVehiculeRequis() + " manquant pour la récolte", false);
			if (false){
				return new MessageDTO(p.getEspece().getVehiculeRequis() + " n'a pas assez de carburant pour la récolte", false);
			}
		}
		 */
		p.recolter();
		this.transformationService.changeQuantity(p.getEspece().getRessourceProduite(), p.getEspece().getQuantite());

		if (!p.getEspece().isTree()){
			daoZone.findByPlante(p).ifPresent(zone -> zone.setPlante(null));
			daoPlante.delete(p);
		}
		return new MessageDTO(""+p.getEspece().getQuantite()+" "+p.getEspece().getRessourceProduite().getNomAffichage()+"s produit", true);
    }
}
