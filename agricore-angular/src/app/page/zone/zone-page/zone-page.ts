import { Component, OnInit } from '@angular/core';
import { MapComponent } from '../map-component/map-component';
import { ZoneDTO } from '../../../dto/zone/response/zone-dto';
import { map, Observable, startWith, Subject, switchMap } from 'rxjs';
import { ZoneService } from '../../../service/zone/zone-service';
import { MapSize } from '../../../model/zone/position/map-size';
import { DataService } from '../../../service/data-service';
import { ZoneShape } from '../../../model/zone/zone-shape';
import { AsyncPipe } from '@angular/common';
import { ZoneDataDTO } from '../../../dto/zone/response/zone-data-dto';

@Component({
  selector: 'app-zone-page',
  imports: [MapComponent, AsyncPipe],
  templateUrl: './zone-page.html',
  styleUrl: './zone-page.css',
})
export class ZonePage implements OnInit {
  private refreshZone$: Subject<void> = new Subject<void>();
  protected zones$!: Observable<ZoneDTO[]>;
  private refreshZoneData$: Subject<void> = new Subject<void>();
  protected zoneDatas$!: Observable<ZoneDataDTO[]>;
  protected zoneShapes$!: Observable<ZoneShape[]>;

  protected displayMap: boolean = false;

  constructor(
    protected zoneService: ZoneService,
    protected dataService: DataService,
  ) {}

  ngOnInit(): void {
    this.zones$ = this.refreshZone$.pipe(
      startWith(0),
      switchMap(() => this.zoneService.findAll()),
    );
    this.zoneShapes$ = this.zones$.pipe(
      map((zones) => zones.map((z) => ZoneShape.zoneShapeFromPositionDTO(z.position))),
    );

    this.zoneDatas$ = this.refreshZoneData$.pipe(
      startWith(0),
      switchMap(() => this.dataService.getZoneData()),
    );
  }

  protected reloadZone(): void {
    this.refreshZone$.next();
    this.zoneShapes$ = this.zones$.pipe(
      map((zones) => zones.map((z) => ZoneShape.zoneShapeFromPositionDTO(z.position))),
    );
  }

  protected reloadZoneData(): void {
    this.refreshZoneData$.next();
  }

  protected reloadAll(): void {
    this.reloadZone();
    this.reloadZoneData();
  }
}
