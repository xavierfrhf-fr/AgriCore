package agricore.projet.services;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import agricore.projet.dto.compte.request.CompteRequestCreateDTO;
import agricore.projet.dto.compte.request.CompteRequestDTO;
import agricore.projet.dto.compte.request.TransfertRequestDTO;
import agricore.projet.dto.compte.response.CompteResponseDTO;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "finance-service", url = "http://localhost:8081/api/comptes")
public interface FinanceClient {

    @PostMapping("/virement") 
    public void virement(@RequestBody TransfertRequestDTO dto);

    @PostMapping
    public CompteResponseDTO create(@RequestBody CompteRequestCreateDTO compteRequestDTO);

    @GetMapping
    public List<CompteResponseDTO> getAll();

    @GetMapping("/user/{id}")
    public CompteResponseDTO getByUserId(@PathVariable Integer id);

    @PutMapping("/{id}")
    public CompteResponseDTO update(@PathVariable Integer id, @RequestBody CompteRequestDTO compteRequestDTO);

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id);

}
