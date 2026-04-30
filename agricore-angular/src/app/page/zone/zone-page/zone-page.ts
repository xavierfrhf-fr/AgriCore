import { Component, OnInit } from '@angular/core';
import { MapComponent } from '../map-component/map-component';
import { ZoneDTO } from '../../../dto/zone/response/zone-dto';
import { map, Observable, startWith, Subject, switchMap } from 'rxjs';
import { ZoneService } from '../../../service/zone/zone-service';
import { MapSize } from '../../../model/zone/position/map-size';
import { DataService } from '../../../service/data-service';
import { ZoneShape } from '../../../model/zone/zone-shape';
import { AsyncPipe } from '@angular/common';

@Component({
  selector: 'app-zone-page',
  imports: [MapComponent, AsyncPipe],
  templateUrl: './zone-page.html',
  styleUrl: './zone-page.css',
})
export class ZonePage implements OnInit {
  private refresh$: Subject<void> = new Subject<void>();
  protected zones$!: Observable<ZoneDTO[]>;
  protected zoneShapes$!: Observable<ZoneShape[]>;

  protected displayMap: boolean = false;

  constructor(
    protected zoneService: ZoneService,
    protected dataService: DataService,
  ) {}

  ngOnInit(): void {
    this.zones$ = this.refresh$.pipe(
      startWith(0),
      switchMap(() => this.zoneService.findAll()),
    );

    this.zoneShapes$ = this.zones$.pipe(
      map((zones) => zones.map((z) => ZoneShape.zoneShapeFromPositionDTO(z.position))),
    );
  }
}
