package agricore.projet.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import agricore.projet.dto.compte.request.CompteRequestCreateDTO;
import agricore.projet.dto.compte.request.CompteRequestDTO;
import agricore.projet.dto.compte.request.TransfertRequestDTO;
import agricore.projet.dto.compte.response.CompteResponseDTO;
import agricore.projet.services.FinanceService;
import agricore.projet.services.FinanceClient;

@RestController
@RequestMapping("/api/comptes")
public class FinanceController {

    private final FinanceService financeService;
    private final FinanceClient financeClient;

    public FinanceController(FinanceService financeService, FinanceClient financeClient) {
        this.financeService = financeService;
        this.financeClient = financeClient;
    }

    @GetMapping
    public List<CompteResponseDTO> getAll() {
        return financeService.getAll();
    }

    @GetMapping("/user/{id}")
    public CompteResponseDTO getByUserId(@PathVariable Integer id) {
        return financeClient.getByUserId(id);
    }

    @PostMapping("/virement")
    public void virement(@RequestBody TransfertRequestDTO dto) {
        financeClient.virement(dto);
    }

    @PostMapping
    public CompteResponseDTO create(@RequestBody CompteRequestCreateDTO compteRequestDTO) {
        return financeClient.create(compteRequestDTO);
    }

    @PutMapping("/{id}")
    public CompteResponseDTO update(@PathVariable Integer id, @RequestBody CompteRequestDTO compteRequestDTO) {
        return financeClient.update(id, compteRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        financeClient.delete(id);
    }



}
