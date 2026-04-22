package agricore.projet.dto.utilisateur.response;

import org.springframework.beans.BeanUtils;

import agricore.projet.model.Fermier;

public class FermierWithEmployeResponseDTO {
	private Integer fermierId;
	private Integer employeId;
	
	
	public Integer getFermierId() {
		return fermierId;
	}
	public Integer getEmployeId() {
		return employeId;
	}
	public void setFermierId(Integer fermierId) {
		this.fermierId = fermierId;
	}
	public void setEmployeId(Integer employeId) {
		this.employeId = employeId;
	}
	
	public static FermierWithEmployeResponseDTO convert (Fermier fermier) {
		FermierWithEmployeResponseDTO response = new FermierWithEmployeResponseDTO();
		BeanUtils.copyProperties(fermier, response);
		response.setEmployeId(fermier.getId());
		return response;
		
	}
	
}
