import { CellOffset } from './position/cell-offset';
import { CellGridPosition } from './position/cell-grid-position';
import { CellAbsolutePosition } from './position/cell-absolute-position';
import { PositionDTO } from '../../dto/zone/response/position-dto';
import { ZonePage } from '../../page/zone/zone-page/zone-page';

export class ZoneShape {
  constructor(
    private _cells: CellOffset[],
    private _anchorX: number,
    private _anchorY: number,
    private _spritePath: string,
  ) {}

  public get cells(): CellOffset[] {
    return this._cells;
  }

  public get anchorX(): number {
    return this._anchorX;
  }

  public get anchorY(): number {
    return this._anchorY;
  }

  get spritePath(): string {
    return this._spritePath;
  }

  getCellGridPosition(): CellGridPosition[] {
    return this._cells.map((cell) => ({
      x: cell.x + this._anchorX,
      y: cell.y + this._anchorY,
    }));
  }

  getAbsolutePosition(originX: number, originY: number, cellSize: number): CellAbsolutePosition[] {
    return this.getCellGridPosition().map((cell) => ({
      x: cell.x * cellSize + originX,
      y: cell.y * cellSize + originY,
    }));
  }

  getAbsoluteCanvasPosition(cellSize: number): CellAbsolutePosition[] {
    return this.getCellGridPosition().map((cell) => ({
      x: cell.x * cellSize,
      y: cell.y * cellSize,
    }));
  }

  getBottomLeftCell():CellGridPosition{
    let xPos:number = 0;
    let yPos:number = 0;
    let cell!:CellGridPosition;

    for (let cellGrid of this.getCellGridPosition()){
      if (cellGrid.x > xPos && cellGrid.y > yPos){
        cell = cellGrid;
        xPos = cellGrid.x;
        yPos = cellGrid.y;
      }
    }
    return cell;
  }

  getSpriteAnchor(cellSize:number, originX:number, originY:number):CellAbsolutePosition{
    let cell:CellGridPosition = this.getBottomLeftCell();
    let cellAbs:CellAbsolutePosition = {} as CellAbsolutePosition;
    cellAbs.x = (cell.x+1) * cellSize + originX;
    cellAbs.y = (cell.y + 1) * cellSize + originY;
    return cellAbs;
  }

  getShapeWidth():number{
    let minY:number = this._cells[0].y;
    let maxY:number = this._cells[0].y;
    for (let cell of this._cells){
      if (cell.y <minY){
        minY = cell.y;
      }
      if (cell.y > maxY){
        maxY = cell.y;
      }
    }
    return maxY - minY;
  }

  getSpriteWidth(cellSize:number):number{
    return this.getShapeWidth()*cellSize;
  }

  public static zoneShapeFromPositionDTO(position: PositionDTO): ZoneShape {
    return new ZoneShape(position.cells, position.anchorX, position.anchorY, "");
  }
}
