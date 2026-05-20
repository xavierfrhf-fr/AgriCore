package agricore.projet.dto.serviceDTO;

import agricore.projet.model.ressource.Ressource;

public record RessourceOperationResult(
        boolean success,
        Ressource ressource,
        String errorMessage
) {
    public static RessourceOperationResult valid(Ressource ressource) {
        return new RessourceOperationResult(true, ressource, null);
    }

    public static RessourceOperationResult failure(String errorMessage) {
        return new RessourceOperationResult(false, null, errorMessage);
    }

    public static RessourceOperationResult valid(){
        return new RessourceOperationResult(true, null, null);
    }
}
