package agricore.projet.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import agricore.projet.model.Plante;
import agricore.projet.repository.IDAOPlante;

@Service
public class PlanteService {
	@Autowired 
	IDAOPlante daoPlante;
	
	public List<Plante> getAllPlante()
	{
		return daoPlante.findAll();
	}
}
