package agricore.projet.dto.ressource.response;

import agricore.projet.dto.ressource.request.TransformationRequestDTO;
import agricore.projet.model.ressource.NomRessource;
import agricore.projet.model.ressource.Transformation;

import java.util.Map;
import java.util.stream.Collectors;

public class TransformationResponseDTO {

    private NomRessource product;
    private int requestedQuantity;
    private int producedQuantity;
    private int transformationCount;
    private boolean partial;

    private Map<NomRessource, Integer> consumedResources;
    private Map<NomRessource, Integer> producedResources;

    public TransformationResponseDTO() {
    }

    public TransformationResponseDTO(NomRessource product,
                                     int requestedQuantity,
                                     int producedQuantity,
                                     int transformationCount,
                                     boolean partial,
                                     Map<NomRessource, Integer> consumedResources,
                                     Map<NomRessource, Integer> producedResources) {
        this.product = product;
        this.requestedQuantity = requestedQuantity;
        this.producedQuantity = producedQuantity;
        this.transformationCount = transformationCount;
        this.partial = partial;
        this.consumedResources = consumedResources;
        this.producedResources = producedResources;
    }

    public NomRessource getProduct() {
        return product;
    }

    public void setProduct(NomRessource product) {
        this.product = product;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(int requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    public int getProducedQuantity() {
        return producedQuantity;
    }

    public void setProducedQuantity(int producedQuantity) {
        this.producedQuantity = producedQuantity;
    }

    public int getTransformationCount() {
        return transformationCount;
    }

    public void setTransformationCount(int transformationCount) {
        this.transformationCount = transformationCount;
    }

    public boolean isPartial() {
        return partial;
    }

    public void setPartial(boolean partial) {
        this.partial = partial;
    }

    public Map<NomRessource, Integer> getConsumedResources() {
        return consumedResources;
    }

    public void setConsumedResources(Map<NomRessource, Integer> consumedResources) {
        this.consumedResources = consumedResources;
    }

    public Map<NomRessource, Integer> getProducedResources() {
        return producedResources;
    }

    public void setProducedResources(Map<NomRessource, Integer> producedResources) {
        this.producedResources = producedResources;
    }

    public static TransformationResponseDTO generate(
            TransformationRequestDTO request,
            Transformation transformation,
            int transformationCount) {
        TransformationResponseDTO response = new TransformationResponseDTO();

        response.setProduct(request.getProduct());
        response.setRequestedQuantity(request.getDesiredQuantity());
        response.setTransformationCount(transformationCount);

        int producedQuantity = transformation.getOutput()
                .get(request.getProduct()) * transformationCount;
        response.setProducedQuantity(producedQuantity);
        response.setPartial(producedQuantity < request.getDesiredQuantity() && producedQuantity > 0);

        response.setConsumedResources(
                multiplyResources(transformation.getInput(), transformationCount)
        );

        response.setProducedResources(
                multiplyResources(transformation.getOutput(), transformationCount)
        );

        return response;
    }

    private static Map<NomRessource, Integer> multiplyResources(
            Map<NomRessource, Integer> resources,
            int factor) {

        return resources.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue() * factor
                ));
    }


}