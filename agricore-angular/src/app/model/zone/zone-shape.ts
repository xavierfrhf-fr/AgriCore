import { CellOffset } from './position/cell-offset';
import { CellGridPosition } from './position/cell-grid-position';
import { CellAbsolutePosition } from './position/cell-absolute-position';
import { PositionDTO } from '../../dto/zone/response/position-dto';
import { ZonePage } from '../../page/zone/zone-page/zone-page';

export class ZoneShape {

  constructor(private _cells:CellOffset[],
              private _anchorX:number,
              private _anchorY:number,
              private _spritePath:string) {}

  public get cells(): CellOffset[] {
    return this._cells;
  }

  public get anchorX(): number {
    return this._anchorX;
  }

  public get anchorY(): number {
    return this._anchorY;
  }

  getCellGridPosition(): CellGridPosition[] {
    return this._cells.map(cell => ({
      x: cell.x + this._anchorX,
      y: cell.y + this._anchorY
    }));
  }

  getAbsolutePosition(originX:number, originY:number):CellAbsolutePosition[]{
    return this.getCellGridPosition().map(cell=>({
      x: cell.x + originX,
      y: cell.y + originY
    }));
  }

  public static zoneShapeFromPositionDTO(position:PositionDTO):ZoneShape{
    return new ZoneShape(position.cells,position.anchorX,position.anchorY,"");
  }
}
