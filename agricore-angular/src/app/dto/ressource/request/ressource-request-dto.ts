import {NomRessource} from '../../../enumerator/ressource/nom-ressource';

import {PrixRequestDto} from './prix-request-dto';

export interface RessourceRequestDto {
  id: string;  // Je crois qu'il le faut indiqué ici même si c'est request
  nom: NomRessource;
  quantite: number;
  prixLot: PrixRequestDto;
  stockMin: number;
}