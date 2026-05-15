package agricore.projet.dto.utilisateur.request;

public record VirementRequestDTO(
    Integer sourceId,
    Integer destinationId,
    Integer montant
) {

}
