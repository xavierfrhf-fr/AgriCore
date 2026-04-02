package agricore.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import agricore.view.Views;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Fermier extends Utilisateur {
	
	@OneToMany(mappedBy="utilisateur")
	@JsonView(Views.FermierWithEmploye.class)
	private List<Employe> employe;
	
	@OneToMany(mappedBy="zone")
	@JsonView(Views.FermierWithZone.class)
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
