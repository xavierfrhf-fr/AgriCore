package agricore.view;

public class Views {

	public class Common{}
	
	//VIEW POUR ZONE
	public class Zone extends Common {}
	public class ZoneWithAnimal extends Zone {}
	public class ZoneWithVehicule extends Zone {}
	public class ZoneWithRessource extends Zone {}
	// public interface FullZone extends ZoneWithRessource, ZoneWithVehicule, ZoneWithAnimal {} Si necessaire de tout ramener (mais faudra passer les autres views en interfaces)
	
	//VIEW POUR CLIENT / EMPLOYE / FERMIER 
	
	public class Fermier extends Common {}
	
	  public class FermierWithEmploye extends Fermier {}
	  public class FermierWithZone extends Fermier {}
	
	public class Employe extends Common {} 
	
	public class Client extends Common {}
	
	
	
	//VIEW POUR ANIMAL
	public class Animal extends Common{}
	
	
	//VIEW POUR PLANTE
	public class Plante extends Common {}
	
	
	
	//VIEW POUR VEHICULE
	public class Vehicule extends Common {}
	
	
	
	//VIEW POUR RESSOURCE
	public class Ressource extends Common {}
}
