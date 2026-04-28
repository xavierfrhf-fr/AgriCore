import {NomRessource} from '../../../enumerator/ressource/nom-ressource';
import {NomZone} from '../../../enumerator/ressource/nom-zone';

import {PrixResponseDto} from './prix-response-dto';

export interface RessourceResponseDto {
  id: string;
  nom: NomRessource;
  uniteAffichage: string;
  quantite: number;
  prixLot: PrixResponseDto;
  stockMin: number;
  zoneId: number;
  zoneNom: NomZone;
}