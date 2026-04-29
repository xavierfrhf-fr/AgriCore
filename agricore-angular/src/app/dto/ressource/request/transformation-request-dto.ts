import {NomRessource} from '../../../enumerator/ressource/nom-ressource';

export interface TransformationRequestDto {
  product: NomRessource;
  desiredQuantity: number;
  partial: boolean;
  bypassStockMin: boolean;
}