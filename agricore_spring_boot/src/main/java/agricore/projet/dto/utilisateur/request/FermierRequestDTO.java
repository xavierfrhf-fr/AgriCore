package agricore.projet.dto.utilisateur.request;

import org.springframework.beans.BeanUtils;
import agricore.projet.model.Fermier;

public class FermierRequestDTO {
	
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
	
	public static FermierRequestDTO convert (Fermier fermier) {
		FermierRequestDTO request = new FermierRequestDTO();
		BeanUtils.copyProperties(fermier, request);
		return request;
	}

}
