package agricore.projet.dto.data;

import agricore.projet.model.ressource.NomRessource;
import agricore.projet.model.zone.NomZone;
import agricore.projet.model.zone.TypeZone;
import agricore.projet.model.zone.position.CellOffset;

import java.util.List;

public class ZoneDataDTO {
    private String nomZone;
    private List<CellOffset> shape;
    private TypeZone typeZone;
    private boolean zoneUnique;
    private List<NomRessource> nomRessources;
    private boolean zoneCreatable = false;

    public ZoneDataDTO(String nomZone, List<CellOffset> shape, TypeZone typeZone, boolean zoneUnique,
            List<NomRessource> nomRessources) {
        this.nomZone = nomZone;
        this.shape = shape;
        this.typeZone = typeZone;
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

    public TypeZone getTypeZone() {
        return typeZone;
    }

    public void setTypeZone(TypeZone typeZone) {
        this.typeZone = typeZone;
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

    public boolean isZoneCreatable() {
        return zoneCreatable;
    }

    public void setZoneCreatable(boolean zoneCreatable) {
        this.zoneCreatable = zoneCreatable;
    }

    public static ZoneDataDTO from(NomZone nomZone) {
        ZoneDataDTO dto = new ZoneDataDTO();
        dto.setNomZone(nomZone.name());
        dto.setTypeZone(nomZone.getTypeZone());
        dto.setZoneUnique(nomZone.isZoneUnique());
        dto.setShape(nomZone.getZoneShape()
                .getShape()
                .stream()
                .toList());
        return dto;
    }
}
