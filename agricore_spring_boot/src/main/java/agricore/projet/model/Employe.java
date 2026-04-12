package agricore.projet.model;

import com.fasterxml.jackson.annotation.JsonView;

import agricore.projet.view.Views;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Employe extends Utilisateur{
	
	@ManyToOne
	@JoinColumn(name="fermier_id")
	@JsonView(Views.Employe.class)
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
