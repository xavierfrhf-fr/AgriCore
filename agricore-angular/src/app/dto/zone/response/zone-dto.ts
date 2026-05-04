import { PositionDTO } from './position-dto';

export interface ZoneDTO {
  id:number;
  position:PositionDTO;
  nomZone:String;
  fermierId:number;
}
