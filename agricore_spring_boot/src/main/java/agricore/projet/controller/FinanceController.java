package agricore.projet.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import agricore.projet.dto.compte.request.TransfertRequestDTO;
import agricore.projet.dto.compte.response.CompteResponseDTO;
import agricore.projet.services.FinanceService;

@RestController
@RequestMapping("/api/comptes")
public class FinanceController {

    private final FinanceService financeService;

    public FinanceController(FinanceService financeService) {
        this.financeService = financeService;
    }

    @GetMapping
    public List<CompteResponseDTO> getAll() {
        return financeService.getAll();
    }

    @PostMapping("/virement")
    public void virement(@RequestBody TransfertRequestDTO dto) {
        financeService.virement(dto);
    }

}
