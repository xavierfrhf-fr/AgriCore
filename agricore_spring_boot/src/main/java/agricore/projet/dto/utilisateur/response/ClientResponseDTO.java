package agricore.projet.dto.utilisateur.response;

import org.springframework.beans.BeanUtils;

import agricore.projet.model.Client;

public class ClientResponseDTO {
	
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

	public static ClientResponseDTO convert (Client client) {
		ClientResponseDTO response = new ClientResponseDTO();
		BeanUtils.copyProperties(client, response);
		return response;
	}
}
