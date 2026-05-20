package agricore.projet.contexts;

import agricore.projet.model.EspecePlante;
import agricore.projet.model.Plante;
import agricore.projet.model.ressource.NomRessource;
import agricore.projet.model.ressource.Ressource;
import agricore.projet.model.zone.NomZone;
import agricore.projet.model.zone.Zone;
import agricore.projet.repository.IDAOPlante;
import agricore.projet.repository.IDAORessource;
import agricore.projet.repository.IDAOZone;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.*;

@Component
@RequestScope
public class DataContext {

    private boolean allRessourceLoaded = false;
    private final Map<NomRessource, Ressource> ressourcesByNom = new HashMap<>();
    private final Set<NomRessource> requestedRessources = new HashSet<>();

    private boolean allZoneLoaded = false;
    private final Map<NomZone, List<Zone>> zonesByNom = new HashMap<>();
    private final Set<NomZone> requestedZones = new HashSet<>();

    private boolean allPlantLoaded = false;
    private final Map<Integer, Plante>  plantesById = new HashMap<>();

    //Autres infos a stocké:
    private Set<NomRessource> failledRessourcesCreation = new HashSet<>();

    private final IDAORessource daoRessource;
    private final IDAOZone daoZone;
    private final IDAOPlante daoPlante;

    public DataContext(IDAORessource daoRessource, IDAOZone daoZone, IDAOPlante daoPlante) {
        this.daoRessource = daoRessource;
        this.daoZone = daoZone;
        this.daoPlante = daoPlante;
    }

    public List<Ressource> getRessources() {
        if (!allRessourceLoaded) {
            allRessourceLoaded = true;
            ressourcesByNom.clear();

            for (Ressource ressource : daoRessource.findAll()) {
                ressourcesByNom.put(ressource.getNom(), ressource);
            }
        }

        return new ArrayList<>(ressourcesByNom.values());
    }

    public Optional<Ressource> getRessourceByNom(NomRessource nomRessource) {
        if (ressourcesByNom.containsKey(nomRessource)) {
            return Optional.of(ressourcesByNom.get(nomRessource));
        }

        if (allRessourceLoaded || requestedRessources.contains(nomRessource)) {
            return Optional.empty();
        }

        requestedRessources.add(nomRessource);

        Optional<Ressource> request = daoRessource.findByNom(nomRessource);
        request.ifPresent(ressource -> ressourcesByNom.put(nomRessource, ressource));

        return request;
    }

    public boolean ressourceExists(NomRessource nomRessource) {
        return getRessourceByNom(nomRessource).isPresent();
    }

    public List<Zone> getZones() {
        if (!allZoneLoaded) {
            allZoneLoaded = true;
            zonesByNom.clear();

            for (Zone zone : daoZone.findAll()) {
                zonesByNom
                        .computeIfAbsent(zone.getNomZone(), key -> new ArrayList<>())
                        .add(zone);
            }
            System.out.println("findAll zone in DataContext");
        }

        return zonesByNom.values()
                .stream()
                .flatMap(List::stream)
                .toList();
    }

    public List<Zone> getZonesByNomZone(NomZone nomZone) {
        if (zonesByNom.containsKey(nomZone)) {
            return zonesByNom.get(nomZone);
        }

        if (allZoneLoaded || requestedZones.contains(nomZone)) {
            return List.of();
        }

        requestedZones.add(nomZone);

        List<Zone> request = daoZone.findByName(nomZone);
        zonesByNom.put(nomZone, request);

        return request;
    }

    public boolean zoneExists(NomZone nomZone) {
        return !getZonesByNomZone(nomZone).isEmpty();
    }

    public void putRessource(Ressource ressource) {
        ressourcesByNom.put(ressource.getNom(), ressource);
        requestedRessources.add(ressource.getNom());
    }

    public void putZone(Zone zone) {
        zonesByNom
                .computeIfAbsent(zone.getNomZone(), key -> new ArrayList<>())
                .add(zone);
    }

    public void putPlante(Plante plante){
        plantesById.put(plante.getId(), plante);
    }

    public Optional<Plante> getPlanteById(int planteId) {
        if (plantesById.containsKey(planteId)) {
            return Optional.of(plantesById.get(planteId));
        }
        if (allPlantLoaded){
            return Optional.empty();
        }
        Optional<Plante> request = daoPlante.findById(planteId);
        request.ifPresent(plante -> plantesById.put(planteId, plante));
        return request;
    }

    public List<Plante> getPlantes(){
        if (allPlantLoaded){
            return plantesById.values().stream().toList();
        }
        List<Plante> plantes = daoPlante.findAll();
        plantes.forEach(plante -> plantesById.put(plante.getId(), plante));
        allPlantLoaded = true;
        return plantes;
    }

    public List<Plante> reloadPlantes(){
        List<Plante> plantes = daoPlante.findAll();
        plantes.forEach(plante -> plantesById.put(plante.getId(), plante));
        allPlantLoaded = true;
        return plantes;
    }

    public void addResourceCreationFailed(NomRessource nomRessource) {
        failledRessourcesCreation.add(nomRessource);
    }


}
