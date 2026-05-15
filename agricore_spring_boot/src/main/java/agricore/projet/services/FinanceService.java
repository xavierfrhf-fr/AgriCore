package agricore.projet.services;

import org.springframework.stereotype.Service;

@Service
public class FinanceService {

    public final FinanceClient financeClient;

    public FinanceService(FinanceClient financeClient) {
        this.financeClient = financeClient;
    }
    
    public void virement(Integer sourceId, Integer destinationId, Integer montant) {
        financeClient.virement(sourceId, destinationId, montant);
    }

    public void getAll() {
        financeClient.getAll();
    }
}
