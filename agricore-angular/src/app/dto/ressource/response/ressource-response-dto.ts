import {PrixResponseDto} from './prix-response-dto';

export interface RessourceResponseDto {
  id: string;
  nom: string;
  uniteAffichage: string;
  quantite: number;
  prixLot: PrixResponseDto;
  stockMin: number;
  zoneId: number;
  zoneNom: string;
}