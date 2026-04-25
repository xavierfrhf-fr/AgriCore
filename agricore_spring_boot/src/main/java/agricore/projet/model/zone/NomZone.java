package agricore.projet.model.zone;

import agricore.projet.model.NomRessource;
import agricore.projet.model.zone.position.ZoneShape;

import java.util.Set;

public enum NomZone {
	CHAMPS(ZoneShape.rectangle(4,4),
			true,
			Set.of()
	),
	POULAILLER(ZoneShape.rectangle(4,2),
			false,
			Set.of()
	),
	ETABLE(ZoneShape.rectangle(3,3),
			false,
			Set.of()
	),
	SILO(ZoneShape.rectangle(1,3),
			false,
			Set.of(NomRessource.Blé,
					NomRessource.Colza,
					NomRessource.Tournesol,
					NomRessource.Mais)
	),
	CUVE(ZoneShape.rectangle(2,2),
			false,
			Set.of(NomRessource.Lait,NomRessource.JusDePomme)
	),
	STOCK_DE_FRUIT(ZoneShape.rectangle(1,1),
			false,
			Set.of(NomRessource.Pomme,NomRessource.Poire,NomRessource.Fraise)
	),
	CHAMBRE_FROIDE(ZoneShape.rectangle(3,3),
			false,
			Set.of(NomRessource.Fromage)
	);

	private final ZoneShape zoneShape;
	private final boolean autorisePlant;
	private final Set<NomRessource> setRessource; //ATENTION ! Une ressource ne doit pas se retrouver dans 2 batiments !!
	//ATENTION n°2 : Un batiment stockant des ressources doit être unique ! (isZoneUnique())
	//private final String imageLink //Je pose ca là au cas où
	
	
	private NomZone(ZoneShape zoneShape, boolean autorisePlant, Set<NomRessource> setRessource) {
		this.zoneShape = zoneShape;
		this.autorisePlant = autorisePlant;
		this.setRessource = setRessource;
	}

	public ZoneShape getZoneShape() {
		return zoneShape;
	}

	public boolean isAutorisePlant() {
		return autorisePlant;
	}

	public Set<NomRessource> getSetRessource() {
		return setRessource;
	}

	public boolean isZoneUnique(){
		return !setRessource.isEmpty();
	}
}
