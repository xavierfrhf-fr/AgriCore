import { CellOffset } from '../../../model/zone/position/cell-offset';

export interface PositionDTO {
  anchorX:number;
  anchorY:number;
  rotation:string;
  pathSprite:string;
  cells:CellOffset[];
}
