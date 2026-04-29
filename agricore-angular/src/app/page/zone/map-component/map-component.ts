import { Component, ElementRef, ViewChild } from '@angular/core';
import { Observable } from 'rxjs';
import { MapSize } from '../../../model/zone/position/map-size';
import { DataService } from '../../../service/data-service';
import { AsyncPipe, NgIf } from '@angular/common';
import { ZoneShape } from '../../../model/zone/zone-shape';
import { CellAbsolutePosition } from '../../../model/zone/position/cell-absolute-position';
import { CellOffset } from '../../../model/zone/position/cell-offset';

@Component({
  selector: 'app-map-component',
  imports: [NgIf, AsyncPipe],
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
        { x: 2, y: 1 }
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

  @ViewChild('overlayCanvas')
  set overlayCanvasSetter(canvas: ElementRef<HTMLCanvasElement> | undefined) {
    if (!canvas) {
      return;
    }

    this.overlayCanvas = canvas;
    this.renderShapes(this.fakeZoneShapes);
  }

  private overlayCanvas!: ElementRef<HTMLCanvasElement>;

  constructor(protected dataService: DataService) {}

  ngOnInit(): void {
    console.log('Init de map component');
    this.mapSize$ = this.dataService.getMapSize();
    this.mapSize$.subscribe((m) => console.log('MapSize : ' + m.x + ' X ' + m.y));
  }

  drawShape(ctx: CanvasRenderingContext2D, shape: ZoneShape): void {
    const rect = this.overlayCanvas.nativeElement.getBoundingClientRect();

    const xCanva = rect.left; // Distance depuis le bord gauche de la fenêtre
    const yCanva = rect.top;

    const cellAbsolutePositionStrings = new Set(shape.getAbsolutePosition(xCanva, yCanva).map((c) => `${c.x},${c.y}`));

    ctx.fillStyle = 'rgba(10, 10, 10, 0.35)';
    ctx.strokeStyle = 'rgba(0, 0, 0, 1)';
    ctx.lineWidth = 2;

    for (const cell of shape.getAbsolutePosition(0, 0)) {
      const px = cell.x * this.cellSize;
      const py = cell.y * this.cellSize;

      ctx.fillRect(px, py, this.cellSize, this.cellSize);

      //this.drawCellBorders(ctx, cell, cellAbsolutePositionStrings);
    }
  }

  renderShapes(shapes: ZoneShape[]): void {
    const canvas = this.overlayCanvas.nativeElement;
    const ctx = canvas.getContext('2d');

    if (!ctx) {
      return;
    }

    ctx.clearRect(0, 0, canvas.width, canvas.height);

    for (const shape of shapes) {
      this.drawShape(ctx, shape);
    }
  }
}
