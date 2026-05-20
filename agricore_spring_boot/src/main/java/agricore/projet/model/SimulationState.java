package agricore.projet.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class SimulationState {

    @Id
    private Integer id = 1;

    @Column(nullable = false)
    private LocalDateTime lastUpdate;

    protected SimulationState() {
    }

    public SimulationState(LocalDateTime lastUpdate) {
        this.id = 1;
        this.lastUpdate = lastUpdate;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}