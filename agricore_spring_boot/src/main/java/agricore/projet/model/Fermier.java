package agricore.projet.model;

import java.util.List;

import agricore.projet.model.zone.Zone;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Fermier extends Utilisateur {
	
	@OneToMany(mappedBy="fermier")
	private List<Employe> employe;


	public Fermier() {}

	public List<Employe> getEmploye() {
		return employe;
	}

	public void setEmploye(List<Employe> employe) {
		this.employe = employe;
	}

	
	public Fermier(Integer id, String login, String password, String nom, String prenom) {
		super(id, login, password, nom, prenom);
	}

	@Override
	public String toString() {
		return "Fermier [id=" + id + ", login=" + login + ", password=" + password + "]";
	}

}
