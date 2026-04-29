import { Component, OnInit } from '@angular/core';
import { MapComponent } from '../map-component/map-component';
import { ZoneDTO } from '../../../dto/zone/response/zone-dto';
import { Observable, startWith, Subject, switchMap } from 'rxjs';
import { ZoneService } from '../../../service/zone/zone-service';
import { MapSize } from '../../../model/zone/position/map-size';
import { DataService } from '../../../service/data-service';

@Component({
  selector: 'app-zone-page',
  imports: [MapComponent],
  templateUrl: './zone-page.html',
  styleUrl: './zone-page.css',
})
export class ZonePage implements OnInit {
  protected zones$!: Observable<ZoneDTO[]>;

  private refresh$:Subject<void> = new Subject<void>()

  constructor(protected zoneService: ZoneService, protected dataService: DataService) {}

  ngOnInit():void {

    this.zones$ = this.refresh$.pipe(
      startWith(0),
      switchMap(()=>this.zoneService.findAll())
    );
  }


}
