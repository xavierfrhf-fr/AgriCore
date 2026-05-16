package agricore.projet.services;

import java.util.List;

import org.springframework.stereotype.Service;

import agricore.projet.dto.compte.request.TransfertRequestDTO;
import agricore.projet.dto.compte.response.CompteResponseDTO;

//finir si besoins de  logique métier plus pousser mais 
// pour l'instant on de l'interface au controller direct

@Service
public class FinanceService {

    public final FinanceClient financeClient;

    public FinanceService(FinanceClient financeClient) {
        this.financeClient = financeClient;
    }
    
    public void virement(TransfertRequestDTO dto) {
        financeClient.virement(dto);
    }

    public List<CompteResponseDTO> getAll() {
        return financeClient.getAll();
    }
}
