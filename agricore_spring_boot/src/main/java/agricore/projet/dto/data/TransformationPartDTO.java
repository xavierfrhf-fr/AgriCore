package agricore.projet.dto.data;

import agricore.projet.model.ressource.NomRessource;
import agricore.projet.model.ressource.Unite;

public record TransformationPartDTO(
        NomRessource nomRessource,
        int quantite,//"stoechiométrie de la transfo"
        Unite unite,
        boolean isProduct,//Pas forcément necessaire, mais indique si c'est un Produit (true) ou ingédient (false)
        int max//indique le max qui peut être produit, ou consommé
) {}
