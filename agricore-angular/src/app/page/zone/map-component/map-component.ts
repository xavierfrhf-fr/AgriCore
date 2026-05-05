import {
  Component,
  ElementRef,
  EventEmitter,
  HostListener,
  Input,
  Output,
  ViewChild,
} from '@angular/core';
import { Observable } from 'rxjs';
import { MapSize } from '../../../model/zone/position/map-size';
import { DataService } from '../../../service/data-service';
import { AsyncPipe } from '@angular/common';
import { ZoneShape } from '../../../model/zone/zone-shape';
import { CellAbsolutePosition } from '../../../model/zone/position/cell-absolute-position';
import { CellOffset } from '../../../model/zone/position/cell-offset';
import { ZoneRequest } from '../../../dto/zone/request/zone-request';
import { PositionDTO } from '../../../dto/zone/response/position-dto';

@Component({
  selector: 'app-map-component',
  standalone: true,
  imports: [AsyncPipe],
  templateUrl: './map-component.html',
  styleUrl: './map-component.css',
})
export class MapComponent {
  protected mapSize$!: Observable<MapSize>;
  protected cellSize: number = 64;
  protected fakeZoneShapes: ZoneShape[] = [
    new ZoneShape(
      [
        { x: 0, y: 0 },
        { x: 1, y: 0 },
        { x: 1, y: 1 },
        { x: 0, y: 1 },
        { x: 2, y: 1 },
      ], // _cells
      0, // _anchorX
      0, // _anchorY
      '',
    ),

    new ZoneShape(
      [
        { x: 0, y: 0 },
        { x: 1, y: 0 },
        { x: 0, y: 1 },
      ],
      4,
      4,
      '',
    ),
  ];
  @Input() shapes: ZoneShape[] | null = null;

  @Input() mapMode: MapMode = 'VIEW';
  @Input() placementShape?: ZoneShape;
  @Output() zoneCreation = new EventEmitter<{ x: number; y: number }>();
  @Output() cancelCreation = new EventEmitter<void>();
  @Output() deleteZoneByPos = new EventEmitter<{ x: number; y: number }>();
  private imageCache = new Map<string, HTMLImageElement>();

  protected ghostX = 0;
  protected ghostY = 0;
  protected ghostWidth = 0;

  private overlayCanvas?: ElementRef<HTMLCanvasElement>;

  @ViewChild('overlayCanvas')
  set overlayCanvasSetter(canvas: ElementRef<HTMLCanvasElement> | undefined) {
    this.overlayCanvas = canvas;
    this.tryRender();
  }

  ngOnChanges(): void {
    this.tryRender();
  }

  private tryRender(): void {
    if (!this.overlayCanvas || !this.shapes) {
      return;
    }

    this.renderShapes(this.shapes);
  }

  constructor(protected dataService: DataService) {}

  ngOnInit(): void {
    console.log('Init de map component');
    this.mapSize$ = this.dataService.getMapSize();
    this.mapSize$.subscribe((m) => console.log('MapSize : ' + m.x + ' X ' + m.y));
  }

  getImage(path: string): HTMLImageElement {
    if (!this.imageCache.has(path)) {
      const img = new Image();
      img.src = path;
      this.imageCache.set(path, img);
    }
    return this.imageCache.get(path)!;
  }

  drawShape(ctx: CanvasRenderingContext2D, shape: ZoneShape): void {
    if (this.overlayCanvas == null) {
      return;
    }
    const rect = this.overlayCanvas.nativeElement.getBoundingClientRect();

    const xCanva = rect.left; // Distance depuis le bord gauche de la fenêtre
    const yCanva = rect.top;

    const cellAbsolutePositionStrings = new Set(
      shape.getAbsoluteCanvasPosition(this.cellSize).map((c) => `${c.x},${c.y}`),
    );

    ctx.fillStyle = 'rgba(10, 10, 10, 0.35)';
    ctx.strokeStyle = 'rgb(234,0,255)';
    ctx.lineWidth = 2;

    //console.log("X canvas :", xCanva)
    //console.log("Y canvas :", yCanva);

    for (const cell of shape.getAbsoluteCanvasPosition(this.cellSize)) {
      ctx.fillRect(cell.x, cell.y, this.cellSize, this.cellSize);
      //console.log('cell Pos on canvas:');
      //console.log(cell);
      //console.log(cellAbsolutePositionStrings);

      this.drawCellBorders(ctx, cell, cellAbsolutePositionStrings);
    }
  }

  private drawCellBorders(
    ctx: CanvasRenderingContext2D,
    cell: CellAbsolutePosition,
    occupied: Set<string>,
  ): void {
    const x = cell.x;
    const y = cell.y;
    const s = this.cellSize;
    //Pour chaque cellule d'une forme on dessine les traits si elle n'a pas de voisin
    const hasTop = occupied.has(`${cell.x},${cell.y - s}`);
    const hasRight = occupied.has(`${cell.x + s},${cell.y}`);
    const hasBottom = occupied.has(`${cell.x},${cell.y + s}`);
    const hasLeft = occupied.has(`${cell.x - s},${cell.y}`);

    ctx.beginPath(); //On dessine les contours de la forme

    //En haut a gauche x, y
    //En haut a droite x + s, y
    //En bas a gauche x, y + s
    //En bas a droite x + s, y + s

    if (!hasTop) {
      //Haut gauche -> haut droite
      ctx.moveTo(x, y);
      ctx.lineTo(x + s, y);
    }

    if (!hasRight) {
      //Haut droite -> bas droite
      ctx.moveTo(x + s, y);
      ctx.lineTo(x + s, y + s);
    }

    if (!hasBottom) {
      //Bas droite -> bas gauche
      ctx.moveTo(x + s, y + s);
      ctx.lineTo(x, y + s);
    }

    if (!hasLeft) {
      //Bas gauche -> haut gauche
      ctx.moveTo(x, y + s);
      ctx.lineTo(x, y);
    }

    ctx.stroke();
  }

  placeSprite(shape: ZoneShape, ctx: CanvasRenderingContext2D) {
    let cell: CellAbsolutePosition = shape.getSpriteAnchor(this.cellSize, 0, 0);
    let xPos: number = cell.x;
    let yPos: number = cell.y;
    let targetWidth: number = shape.getSpriteWidth(this.cellSize);
    let spritePath: string = shape.spritePath;

    const img = new Image();
    console.log(spritePath);
    console.log('target width :', targetWidth);
    console.log('Anchor :', cell);
    img.src = spritePath;

    img.onload = () => {
      // 1. Calcul de la hauteur proportionnelle
      const aspectRatio = img.height / img.width;
      const targetHeight = targetWidth * aspectRatio;

      // 2. Calcul du point supérieur gauche (Top-Left)
      // On soustrait la hauteur car l'axe Y descend vers le bas
      const finalY = yPos - targetHeight;

      // 3. Affichage sur le contexte (ctx)
      // drawImage(image, x, y, width, height)
      ctx.drawImage(img, xPos, finalY, targetWidth, targetHeight);
    };
  }

  renderShapes(shapes: ZoneShape[]): void {
    if (this.overlayCanvas == null) {
      return;
    }
    const canvas = this.overlayCanvas.nativeElement;
    const ctx = canvas.getContext('2d');

    if (!ctx) {
      return;
    }

    ctx.clearRect(0, 0, canvas.width, canvas.height);

    for (const shape of shapes) {
      this.drawShape(ctx, shape);
      this.placeSprite(shape, ctx);
    }
  }

  getSpriteOffsetY(shape: ZoneShape): number {
    const img = this.getImage(shape.spritePath);

    if (!img.complete) {
      return 0; // fallback temporaire
    }

    const targetWidth = shape.getSpriteWidth(this.cellSize);
    const aspectRatio = img.height / img.width;
    const spriteHeight = targetWidth * aspectRatio;

    const shapeHeight = shape.getShapeHeightPx(this.cellSize);

    return spriteHeight - shapeHeight;
  }

  followMouse(event: MouseEvent) {
    if (this.mapMode !== 'CREATE' || !this.placementShape) {
      return;
    }

    const { x, y } = this.getGridPosition(event);

    const offsetY = this.getSpriteOffsetY(this.placementShape);

    this.ghostX = x * this.cellSize;
    this.ghostY = y * this.cellSize - offsetY;

    this.ghostWidth = this.placementShape.getSpriteWidth(this.cellSize);
  }

  mouseClick(event: MouseEvent) {
    if (this.mapMode === 'CREATE' && this.placementShape) {
      const { x, y } = this.getGridPosition(event);
      this.zoneCreation.emit({ x, y });
    }
  }

  @HostListener('document:keydown.escape', ['$event'])
  onEscape(event: Event) {
    if (!(event instanceof KeyboardEvent)) return;
    this.closeContextMenu();

    if (this.mapMode === 'CREATE') {
      this.cancelCreation.emit();
    }
  }

  @HostListener('document:click', ['$event'])
  onClickOutside(event: MouseEvent) {
    this.closeContextMenu();
    if (!this.overlayCanvas) {
      return;
    }
    const clickedInside = this.overlayCanvas.nativeElement.contains(event.target as Node);

    if (!clickedInside && this.mapMode === 'CREATE') {
      this.cancelCreation.emit();
    }
  }

  getGridPosition(event: MouseEvent): { x: number; y: number } {
    if (!this.overlayCanvas) {
      return { x: 0, y: 0 };
    }

    const rect = this.overlayCanvas.nativeElement.getBoundingClientRect();

    const mouseX = event.clientX - rect.left;
    const mouseY = event.clientY - rect.top;

    const x = Math.floor(mouseX / this.cellSize);
    const y = Math.floor(mouseY / this.cellSize);

    return { x, y };
  }

  getZoneShapeAt(x: number, y: number): ZoneShape | null {
    if (!this.shapes) {
      return null;
    }
    for (const zoneShape of this.shapes) {
      for (const cell of zoneShape.getCellGridPosition()) {
        if (cell.x === x && cell.y === y) {
          return zoneShape;
        }
      }
    }
    return null;
  }

  contextMenu = {
    visible: false,
    x: 0,
    y: 0,
    building: null as any,
  };

  openContextMenu(x: number, y: number, building: any) {
    this.contextMenu = {
      visible: true,
      x,
      y,
      building,
    };
  }

  closeContextMenu() {
    this.contextMenu.visible = false;
  }

  @HostListener('contextmenu', ['$event'])
  onRightClick(event: MouseEvent) {
    event.preventDefault();

    const { x, y } = this.getGridPosition(event);

    const zoneShape = this.getZoneShapeAt(x, y);
    if (!zoneShape) return;

    this.openContextMenu(event.x, event.y, zoneShape);
  }

  deleteBuilding(shape: ZoneShape) {
    this.deleteZoneByPos.emit({x:shape.anchorX, y:shape.anchorY})
  }
}
