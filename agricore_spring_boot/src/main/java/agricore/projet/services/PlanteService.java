package agricore.projet.services;
//permet de decider comment on cree une plante
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import agricore.projet.dto.plante.response.PlanteResponseDTO;
import agricore.projet.model.Plante;
import agricore.projet.repository.IDAOPlante;
import agricore.projet.repository.IDAOZone;

@Service
public class PlanteService {
	@Autowired 
	IDAOPlante daoPlante;
	IDAOZone daoZone;
	
	PlanteService(IDAOPlante daoPlante, IDAOZone daoZone){
		this.daoPlante = daoPlante;
		this.daoZone = daoZone;
		}
	//le controller a besoin de la methode findAll
	//on veut parcourir la liste
	/*public List<Plante> getAllPlante()
	{
		return daoPlante.findAll()//recupere ttes les plantes dans la base
				.stream()//permet de pouvoir par la suite tranformer les donnees
				.map(PlanteResponseDTO::convert) //transforme chaque plante en PlanteResponse - meme chose que .map(plante ->PlanteResponseDTO.convert(plante))
				.toList();//permet de produire une liste
		}*/
	}

