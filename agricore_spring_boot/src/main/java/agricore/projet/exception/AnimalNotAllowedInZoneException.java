package agricore.projet.exception;

import agricore.projet.model.EspeceAnimal;
import agricore.projet.model.zone.NomZone;

public class AnimalNotAllowedInZoneException extends RuntimeException {
    public AnimalNotAllowedInZoneException(String message) {
        super(message);
    }
  public AnimalNotAllowedInZoneException(NomZone nomZone, EspeceAnimal animal) {
    super("Animal :" + animal.name() + " are not allowed in zone " + nomZone.name() + ". (They must be in "+ animal.getAllowedZone().name());
  }
}
