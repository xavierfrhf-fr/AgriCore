import { PositionDTO } from './position-dto';
import { Animal } from '../../../model/animal';
import { RessourceResponseDto } from '../../ressource/response/ressource-response-dto';
import { VehiculeResponseDTO } from '../../vehicule/response/vehicule-response-dto';

export interface ZoneDTO {
  typeZone: string;
  id:number;
  position:PositionDTO;
  nomZone:string;
  fermierId:number;
  animals:Animal[]
  plante?:null
  ressources?:Array<RessourceResponseDto>
  vehicules?:Array<VehiculeResponseDTO>
}
