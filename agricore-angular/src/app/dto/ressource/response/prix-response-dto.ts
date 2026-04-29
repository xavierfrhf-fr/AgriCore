import {Unite} from '../../../enumerator/ressource/unite';

export interface PrixResponseDto {
  prixPar: number;
  unite: Unite;
  quantiteLot: number;
  affPrix: string;
  affPrixUnitaire: string;
}