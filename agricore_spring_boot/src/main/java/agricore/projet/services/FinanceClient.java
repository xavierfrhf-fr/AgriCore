package agricore.projet.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import agricore.projet.dto.compte.response.CompteResponseDTO;

@FeignClient(name = "finance-service", url = "http://localhost:8081/api/comptes")
public interface FinanceClient {

    @PostMapping("/virement") 
    public void virement(Integer sourceId, Integer destinationId, Integer montant);

    @GetMapping
    public CompteResponseDTO getAll();

    @GetMapping("/user/{id}")
    public CompteResponseDTO getByUserId(Integer id);

}
