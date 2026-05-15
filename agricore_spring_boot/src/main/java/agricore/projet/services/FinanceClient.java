package agricore.projet.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "finance-service", url = "http://localhost:8081")
public interface FinanceClient {

    @PostMapping("/api/comptes/virement") 
    void virement(Integer sourceId, Integer destinationId, Integer montant);

}
