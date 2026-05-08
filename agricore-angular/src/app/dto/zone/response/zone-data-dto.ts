import { CellOffset } from '../../../model/zone/position/cell-offset';
import { TransformationDataDto } from '../../ressource/transformation-data-dto';

export interface ZoneDataDTO {
  nomZone:string;
  shape:CellOffset[];
  typeZone:string;
  zoneUnique:boolean;
  nomRessources:string;
  zoneCreatable:boolean;
  nomAffichage:string;
  description:string;
  pathSprite:string;
  transformations:TransformationDataDto[];
}
