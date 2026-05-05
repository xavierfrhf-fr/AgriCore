import { CellOffset } from '../../../model/zone/position/cell-offset';

export interface ZoneDataDTO {
  nomZone:string;
  shape:CellOffset[];
  typeZone:String;
  zoneUnique:boolean;
  nomRessources:string;
  zoneCreatable:boolean;
  nomAffichage:string;
  description:string;
  pathSprite:string;
}
