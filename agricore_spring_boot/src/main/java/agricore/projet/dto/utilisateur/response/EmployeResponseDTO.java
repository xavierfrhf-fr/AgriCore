package agricore.projet.dto.utilisateur.response;

import org.springframework.beans.BeanUtils;

import agricore.projet.model.Employe;

public class EmployeResponseDTO {
	
	private Integer id;
	private String login;
	
	
	public Integer getId() {
		return id;
	}
	public String getLogin() {
		return login;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	
	public static EmployeResponseDTO convert (Employe employe) {
		EmployeResponseDTO response = new EmployeResponseDTO();
		BeanUtils.copyProperties(employe, response);
		return response;
	}
	

}
