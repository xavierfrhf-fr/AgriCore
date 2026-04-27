package agricore.projet.dto.utilisateur.response;

import java.util.List;

import org.springframework.beans.BeanUtils;

import agricore.projet.model.Fermier;

public class FermierWithEmployeResponseDTO {
	private Integer Id;
	private String login;
	private List<EmployeResponseDTO> employes;
	
	
	
	public Integer getId() {
		return Id;
	}

	public String getLogin() {
		return login;
	}

	public List<EmployeResponseDTO> getEmployes() {
		return employes;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setEmployes(List<EmployeResponseDTO> employes) {
		this.employes = employes;
	}

	public static FermierWithEmployeResponseDTO convert (Fermier fermier) {
		FermierWithEmployeResponseDTO response = new FermierWithEmployeResponseDTO();
		BeanUtils.copyProperties(fermier, response);
		response.setEmployes(fermier.getEmploye()
				.stream()
				.map(EmployeResponseDTO::convert)
				.toList());		
		return response;
		
	}
	
}
