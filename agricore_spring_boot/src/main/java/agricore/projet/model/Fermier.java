package agricore.projet.model;

import java.util.List;

import agricore.projet.model.zone.Zone;
import com.fasterxml.jackson.annotation.JsonView;

import agricore.projet.view.Views;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Fermier extends Utilisateur {
	
	@OneToMany(mappedBy="fermier")
	private List<Employe> employe;
	
	@OneToMany(mappedBy="fermier")
	private List<Zone> zones;
	

	public Fermier() {}

	public List<Employe> getEmploye() {
		return employe;
	}

	public List<Zone> getZone() {
		return zones;
	}

	public void setEmploye(List<Employe> employe) {
		this.employe = employe;
	}

	public void setZone(List<Zone> zone) {
		this.zones = zone;
	}
	
	
	public Fermier(Integer id, String login, String password) {
		super(id, login, password);
	}

	@Override
	public String toString() {
		return "Fermier [id=" + id + ", login=" + login + ", password=" + password + "]";
	}
	
	

}
