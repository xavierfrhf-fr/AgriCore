package agricore.projet.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ressource")
public class Ressource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ressource_id")
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NomRessource nom;
    @Column(nullable = false)
    private int quantite;
    @Column(nullable = false)
    private double prix;
    @Column(name = "stock_min", nullable = false)
    private int stockMin;
    @ManyToOne
    @JoinColumn(name = "zone_id", nullable = false)
    private Zone zone;

    public Ressource() {
    }

    public Ressource(NomRessource nom, int quantite, double prix, int stockMin, Zone zone) {
        this.nom = nom;
        this.quantite = quantite;
        this.prix = prix;
        this.stockMin = stockMin;
        this.zone = zone;
    }

    public Ressource(
            Integer id, NomRessource nom, int quantite, double prix, int stockMin, Zone zone) {
        this.id = id;
        this.nom = nom;
        this.quantite = quantite;
        this.prix = prix;
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

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
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
                + "id=" + id + ", nom=" + nom + ", quantite=" + quantite + ", prix=" + prix
                + ", stockMin=" + stockMin + ", zone=" + zone + '}';
    }
}
