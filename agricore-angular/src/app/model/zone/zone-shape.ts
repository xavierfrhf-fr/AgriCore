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
    let xPos:number = 20;
    let yPos:number = 0;
    let cell!:CellGridPosition;

    for (let cellGrid of this.getCellGridPosition()){
      console.log(cellGrid)
      if (cellGrid.x <= xPos && cellGrid.y >= yPos){
        console.log("Update of bottom left cell !")
        cell = cellGrid;
        xPos = cellGrid.x;
        yPos = cellGrid.y;
      }
    }
    console.log("bottom left cell :", cell)
    return cell;
  }

  getSpriteAnchor(cellSize:number, originX:number, originY:number):CellAbsolutePosition{
    let cell:CellGridPosition = this.getBottomLeftCell();
    let cellAbs:CellAbsolutePosition = {} as CellAbsolutePosition;
    cellAbs.x = (cell.x) * cellSize + originX;
    cellAbs.y = (cell.y+1) * cellSize + originY;
    return cellAbs;
  }

  getShapeWidth():number{
    let minX:number = 500000;
    let maxX:number = 0;
    for (let cell of this._cells){

      if (cell.x <minX){
        minX = cell.x;
      }
      if (cell.x > maxX){
        maxX = cell.x;
      }
      //console.log(cell);
      //console.log("max x: ", maxX, " ; min x :", minX);
    }
    //console.log(maxX-minX)
    return 1+maxX - minX;
  }

  getSpriteWidth(cellSize:number):number{
    return this.getShapeWidth()*cellSize;
  }

  public static zoneShapeFromPositionDTO(position: PositionDTO): ZoneShape {
    return new ZoneShape(position.cells, position.anchorX, position.anchorY, position.pathSprite);
  }
}
