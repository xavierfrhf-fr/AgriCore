package agricore.projet.model.ressource;

import agricore.projet.model.zone.Zone;
import jakarta.persistence.*;

@Entity
@Table(name = "ressource")
public class Ressource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ressource_id")
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(50)")
    private NomRessource nom;
    @Column(nullable = false)
    private int quantite;
    @Embedded
    @Column(nullable = false)
    private PrixLot prixLot;
    @Column(name = "stock_min", nullable = false)
    private int stockMin;
    @ManyToOne
    @JoinColumn(name = "zone_id", nullable = false)
    private Zone zone;

    public Ressource() {
    }

    public Ressource(NomRessource nom, int quantite, PrixLot prixLot, int stockMin, Zone zone) {
        this.nom = nom;
        this.quantite = quantite;
        this.prixLot = prixLot;
        this.stockMin = stockMin;
        this.zone = zone;
    }

    public Ressource(
            Integer id, NomRessource nom, int quantite, PrixLot prixLot, int stockMin, Zone zone) {
        this.id = id;
        this.nom = nom;
        this.quantite = quantite;
        this.prixLot = prixLot;
        this.stockMin = stockMin;
        this.zone = zone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public NomRessource getNom() {
        return nom;
    }

    public void setNom(NomRessource nom) {
        this.nom = nom;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public PrixLot getPrixLot() {
        return prixLot;
    }

    public void setPrixLot(PrixLot prixLot) {
        this.prixLot = prixLot;
    }

    public int getStockMin() {
        return stockMin;
    }

    public void setStockMin(int stockMin) {
        this.stockMin = stockMin;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    @Override
    public String toString() {
        return "Ressource{"
                + "id=" + id + ", nom=" + nom + ", quantite=" + quantite + ", prix=" + prixLot
                + ", stockMin=" + stockMin + ", zone=" + zone + '}';
    }
}
