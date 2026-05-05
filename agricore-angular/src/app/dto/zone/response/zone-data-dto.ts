import { CellOffset } from '../../../model/zone/position/cell-offset';

export interface ZoneDataDTO {
  nomZone:string;
  cellOffsets:CellOffset[];
  typeZone:String;
  zoneUnique:boolean;
  nomRessources:string;
  zoneCreatable:boolean;
  nomAffichage:string;//to do
  description:string//to do
}
