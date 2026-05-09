import { ZoneDataDTO } from '../zone/response/zone-data-dto';
import { RessourceDataDto } from '../ressource/ressource-data-dto';
import { TypeVehiculeDTO } from '../type-vehicule';

export interface PlanteData {
  nomEnum: string;
  nomAffichage: string;
  tempsPousseMinute: number;
  consommationEauParMin: number;
  vehiculeRequis: string;
  isCreatable: boolean;
  numberCreatable: boolean;
  isProductStorable: boolean;
  isVehiculeAvailable:boolean;
  pathSprite: string;
  production: number;
  tree:boolean;
  zone:ZoneDataDTO;
  ressource:RessourceDataDto;
  vehicule:TypeVehiculeDTO;
}
