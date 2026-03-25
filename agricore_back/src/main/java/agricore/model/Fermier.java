package agricore.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Fermier extends Utilisateur {
	
	@OneToMany(mappedBy="utilisateur")
	private List<Employe> employe;
	
	@OneToMany(mappedBy="zone")
	private List<Zone> zone;
	
	public Fermier() {}

	public Fermier(Integer id, String login, String password) {
		super(id, login, password);
	}

	@Override
	public String toString() {
		return "Fermier [id=" + id + ", login=" + login + ", password=" + password + "]";
	}
	
	

}
