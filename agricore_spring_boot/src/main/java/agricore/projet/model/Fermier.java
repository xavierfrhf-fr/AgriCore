package agricore.projet.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import agricore.projet.view.Views;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Fermier extends Utilisateur {
	
	@OneToMany(mappedBy="fermier")
	private List<Employe> employe;
	
	@OneToMany(mappedBy="fermier")
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
