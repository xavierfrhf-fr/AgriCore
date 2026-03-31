package agricore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class Employe extends Utilisateur{
	
	@OneToOne(mappedBy="utilisateur")
	private Fermier fermier;
	
	public Employe() {}

	public Employe(Integer id, String login, String password) {
		super(id, login, password);
	}

	@Override
	public String toString() {
		return "Employe [id=" + id + ", login=" + login + ", password=" + password + "]";
	}
	
	
	

}
