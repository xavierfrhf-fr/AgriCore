package agricore.projet.exception;

import agricore.projet.model.EspecePlante;
import agricore.projet.model.zone.NomZone;

public class PlanteNonAutoriseDansZoneException extends RuntimeException{
    public PlanteNonAutoriseDansZoneException(NomZone nomZone, EspecePlante plante){
        super("Plante :" + plante.name() + " n'est pas autorisée dans cette zone");
    }
}
