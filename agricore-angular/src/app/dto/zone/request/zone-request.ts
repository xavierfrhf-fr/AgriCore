import { PositionDTO } from '../response/position-dto';

export interface ZoneRequest {
  position:PositionDTO;
  nomZone:string;
  fermierId:number;
}
