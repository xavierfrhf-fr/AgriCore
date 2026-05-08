package agricore.projet.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
//permet de decider comment on cree une plante
import java.util.List;

import agricore.projet.dto.MessageDTO;
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
			plante.updateHumidite(false);
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

	public void arroser(Integer id) {
		
		Plante p =daoPlante.findById(id).orElseThrow(()->new PlanteNotFoundException(id));

		p.arroser();
		this.daoPlante.save(p);

	}

    public MessageDTO recolter(Integer id) {
		Plante p = daoPlante.findById(id).orElseThrow(()->new PlanteNotFoundException(id));

		if (p.getCroissance() < 99.9){
			System.out.println("Plante pas mature " + p.getCroissance());
			return new MessageDTO("Plante pas encore mature",false);
		}

		try {
			System.out.println(p.getCroissance());
			this.transformationService.createRessourceIfNotExist(p.getEspece().getRessourceProduite());
			System.out.println("OK");
			p.setCroissance(0);
			p.setMature(false);
		}catch (ZoneNotFoundException e) {
			return new MessageDTO("Zone "+p.getEspece().getRessourceProduite().getZoneStockage().getNomAffichage()+", manquante pour le stockage de "+p.getEspece().getRessourceProduite().getNomAffichage(), false);
		}

		this.transformationService.changeQuantity(p.getEspece().getRessourceProduite(), p.getEspece().getQuantite());
		this.daoPlante.save(p);
		return new MessageDTO(""+p.getEspece().getQuantite()+" "+p.getEspece().getRessourceProduite().getNomAffichage()+"s produit", true);
    }
}
