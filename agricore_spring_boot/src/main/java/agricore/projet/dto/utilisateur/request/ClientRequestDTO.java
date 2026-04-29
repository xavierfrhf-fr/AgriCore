package agricore.projet.dto.utilisateur.request;

import org.springframework.beans.BeanUtils;
import agricore.projet.model.Client;

public class ClientRequestDTO {
	
	private String login;
	private String password;
	
	public String getLogin() {
		return login;
	}
	public String getPassword() {
		return password;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public static ClientRequestDTO convert (Client client) {
		ClientRequestDTO request = new ClientRequestDTO();
		BeanUtils.copyProperties(client, request);
		return request;
	}

}
