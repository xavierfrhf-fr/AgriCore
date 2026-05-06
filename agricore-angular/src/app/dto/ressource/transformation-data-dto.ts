import {TransformationPartDto} from './transformation-part-dto';

export interface TransformationDataDto {
  transformation: string;
  requiredZone: string;
  zoneExist: boolean;
  maxTransfoPossible: number;
  ingredients: TransformationPartDto[];
  produits: TransformationPartDto[];
}