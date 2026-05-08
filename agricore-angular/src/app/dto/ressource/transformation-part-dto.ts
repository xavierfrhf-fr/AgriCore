import { RessourceDataDto } from './ressource-data-dto';

export interface TransformationPartDto {
  nomRessource: string;
  ressourceDataDTO: RessourceDataDto;
  quantite: number;
  unite: string;
  isProduct: boolean;
  max: number;
}
