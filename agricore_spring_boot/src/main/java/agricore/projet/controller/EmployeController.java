package agricore.projet.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import agricore.projet.dto.utilisateur.request.EmployeRequestDTO;
import agricore.projet.dto.utilisateur.response.EmployeResponseDTO;
import agricore.projet.services.EmployeService;

@RestController
@RequestMapping("/api/employe")
public class EmployeController {
	
	private final EmployeService employeService;

    public EmployeController(EmployeService employeService) {
        this.employeService = employeService;
    }

    @GetMapping
    public List<EmployeResponseDTO> findAll() {
        return employeService.getAll();
    }

    @GetMapping("/{id}")
    public EmployeResponseDTO findById(@PathVariable Integer id) {
        return employeService.getById(id);
    }

    @PostMapping
    public EmployeResponseDTO create(@RequestBody EmployeRequestDTO request) {
        return employeService.create(request);
    }

    @PatchMapping("/{id}")
    public EmployeResponseDTO patch(@PathVariable Integer id, @RequestBody EmployeRequestDTO request) {
        return employeService.patch(id, request);
    }

    @PutMapping("/{id}")
    public EmployeResponseDTO update(@PathVariable Integer id, @RequestBody EmployeRequestDTO request) {
        return employeService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        employeService.deleteById(id);
    }

}
