import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { MapSize } from '../../../model/zone/position/map-size';
import { DataService } from '../../../service/data-service';
import { AsyncPipe, NgIf } from '@angular/common';

@Component({
  selector: 'app-map-component',
  imports: [NgIf, AsyncPipe],
  templateUrl: './map-component.html',
  styleUrl: './map-component.css',
})
export class MapComponent {
  protected mapSize$!: Observable<MapSize>;
  protected cellSize:number = 64;

  constructor(protected dataService: DataService) {}

  ngOnInit(): void {
    console.log("Init de map component")
    this.mapSize$ = this.dataService.getMapSize();
    this.mapSize$.subscribe(m => console.log("MapSize : " + m.x + " X " + m.y))
  }
}
