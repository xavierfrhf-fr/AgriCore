import { Component, OnInit } from '@angular/core';
import { MapComponent } from '../map-component/map-component';
import { ZoneDTO } from '../../../dto/zone/response/zone-dto';
import {
  async,
  asyncScheduler,
  combineLatest,
  filter,
  map,
  Observable,
  startWith,
  Subject,
  switchMap,
  take,
  tap,
} from 'rxjs';
import { ZoneService } from '../../../service/zone/zone-service';
import { MapSize } from '../../../model/zone/position/map-size';
import { DataService } from '../../../service/data-service';
import { ZoneShape } from '../../../model/zone/zone-shape';
import { AsyncPipe, TitleCasePipe } from '@angular/common';
import { ZoneDataDTO } from '../../../dto/zone/response/zone-data-dto';
import { ZoneRequest } from '../../../dto/zone/request/zone-request';
import { PositionDTO } from '../../../dto/zone/response/position-dto';
import { ZoneInfoPipe } from '../../../pipe/zone-info-pipe';

@Component({
  selector: 'app-zone-page',
  imports: [MapComponent, AsyncPipe, TitleCasePipe, ZoneInfoPipe],
  templateUrl: './zone-page.html',
  styleUrl: './zone-page.css',
})
export class ZonePage implements OnInit {
  private refreshZone$: Subject<void> = new Subject<void>();
  protected zones$!: Observable<ZoneDTO[]>;
  private refreshZoneData$: Subject<void> = new Subject<void>();
  protected zoneDatas$!: Observable<ZoneDataDTO[]>;
  protected zoneShapes$!: Observable<ZoneShape[]>;

  protected mapMode: MapMode = 'VIEW';
  protected placementShape?: ZoneShape;
  protected zoneCreationType?: string;
  protected isCreation: boolean = false;

  protected zonesWithData$!: Observable<{ zone: ZoneDTO; zoneData: ZoneDataDTO | undefined; }[]>;

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

    this.zonesWithData$ = combineLatest([this.zones$, this.zoneDatas$]).pipe(
      map(([zones, zoneDatas]) =>
        zones.map((zone) => ({
          zone,
          zoneData: zoneDatas.find(
            (data) => data.nomZone?.trim().toLowerCase() === zone.nomZone?.trim().toLowerCase(),
          ),
        })),
      ),
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

  protected startZoneCreation(zoneData: ZoneDataDTO) {
    this.mapMode = 'CREATE';
    this.placementShape = ZoneShape.zoneShapeFromZoneDataDTO(zoneData);
    this.zoneCreationType = zoneData.nomZone;
  }

  protected createZone(event: { x: number; y: number }): void {
    if (this.zoneCreationType == null) {
      return;
    }

    let zoneRequest: ZoneRequest = {} as ZoneRequest;
    let positionRequest: PositionDTO = {} as PositionDTO;
    positionRequest.anchorX = event.x;
    positionRequest.anchorY = event.y;
    positionRequest.rotation = 'DEG_0';
    zoneRequest.position = positionRequest;
    zoneRequest.nomZone = this.zoneCreationType;
    zoneRequest.fermierId = 3;
    this.zoneService.insert(zoneRequest).subscribe(() => this.reloadAll());

    delete this.zoneCreationType;
    delete this.placementShape;
    this.mapMode = 'VIEW';
  }

  protected stopZoneCreation() {
    delete this.zoneCreationType;
    delete this.placementShape;
    this.mapMode = 'VIEW';
  }

  protected deleteZoneByPos(event: { x: number; y: number }) {
    console.log('deleteZoneByPos', event);

    this.zones$
      .pipe(
        take(1),
        map((zones) =>
          zones.find((z) => z.position.anchorX === event.x && z.position.anchorY === event.y),
        ),
        filter((zone): zone is ZoneDTO => !!zone),
        switchMap((zone) => {
          console.log('appel service', zone.id);
          return this.zoneService.deleteZoneById(zone.id);
        }),
      )
      .subscribe(() => {
        this.reloadAll();
      });
  }

  protected deleteZoneById(id:number):void{
    this.zoneService.deleteZoneById(id).subscribe(()=>this.reloadAll());
  }

  private getZoneAtPos(x: number, y: number): Observable<ZoneDTO | undefined> {
    return this.zones$.pipe(
      map((zones) =>
        zones.find((zone) => zone.position.anchorX === x && zone.position.anchorY === y),
      ),
    );
  }

  protected readonly ZoneShape = ZoneShape;
}
