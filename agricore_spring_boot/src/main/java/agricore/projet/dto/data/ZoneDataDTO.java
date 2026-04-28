package agricore.projet.dto.data;

import agricore.projet.model.ressource.NomRessource;
import agricore.projet.model.zone.NomZone;
import agricore.projet.model.zone.position.CellOffset;

import java.util.List;

public class ZoneDataDTO {
    private String nomZone;
    private List<CellOffset> shape;
    private boolean autorisePlant;
    private boolean zoneUnique;
    private List<NomRessource> nomRessources;

    public ZoneDataDTO(String nomZone, List<CellOffset> shape, boolean autorisePlant, boolean zoneUnique,
            List<NomRessource> nomRessources) {
        this.nomZone = nomZone;
        this.shape = shape;
        this.autorisePlant = autorisePlant;
        this.zoneUnique = zoneUnique;
        this.nomRessources = nomRessources;
    }

    public ZoneDataDTO() {
    }

    public String getNomZone() {
        return nomZone;
    }

    public void setNomZone(String nomZone) {
        this.nomZone = nomZone;
    }

    public List<CellOffset> getShape() {
        return shape;
    }

    public void setShape(List<CellOffset> shape) {
        this.shape = shape;
    }

    public boolean isAutorisePlant() {
        return autorisePlant;
    }

    public void setAutorisePlant(boolean autorisePlant) {
        this.autorisePlant = autorisePlant;
    }

    public boolean isZoneUnique() {
        return zoneUnique;
    }

    public void setZoneUnique(boolean zoneUnique) {
        this.zoneUnique = zoneUnique;
    }

    public List<NomRessource> getNomRessources() {
        return nomRessources;
    }

    public void setNomRessources(List<NomRessource> nomRessources) {
        this.nomRessources = nomRessources;
    }

    public static ZoneDataDTO from(NomZone nomZone) {
        ZoneDataDTO dto = new ZoneDataDTO();
        dto.setNomZone(nomZone.name());
        dto.setAutorisePlant(nomZone.isAutorisePlant());
        dto.setZoneUnique(NomRessource.isZoneUnique(nomZone));
        dto.setShape(nomZone.getZoneShape()
                .getShape()
                .stream()
                .toList());
        return dto;
    }
}
