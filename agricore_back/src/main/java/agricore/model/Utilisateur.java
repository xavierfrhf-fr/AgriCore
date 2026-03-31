package agricore.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name="utilisateur")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type_personne", columnDefinition="ENUM('Fermier','Employe',Client'")
public abstract class Utilisateur {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	protected Integer id;
	@Column(nullable=false,unique=true)//rajouter length
	protected String login;
	@Column(nullable=false)//rajouter length
	protected String password;
	
	
	
	public Utilisateur() {}


	public Utilisateur(Integer id, String login, String password) {
		this.id = id;
		this.login = login;
		this.password = password;
	}


	public Integer getId() {
		return id;
	}


	public String getLogin() {
		return login;
	}


	public String getPassword() {
		return password;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
