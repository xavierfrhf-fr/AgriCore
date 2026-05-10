import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable, startWith, Subject, switchMap } from 'rxjs';
import { PlanteService } from '../../service/plante/plante-service';
import { ZoneService } from '../../service/zone/zone-service';
import { PlanteResponse } from '../../dto/plante/plante-response';
import { PlanteRequest } from '../../dto/plante/plante-request';
import { AjoutPlanteCompo } from './ajout-plante-compo/ajout-plante-compo';
import { AsyncPipe } from '@angular/common';
import { InfoPlante } from './info-plante/info-plante';
import { ConsoEauPipe } from '../../pipe/conso-eau-pipe';
import { MessageDTO } from '../../dto/message-dto';
import { PlanteZoneInfoDTO } from '../../dto/plante/plante-zone-info-dto';
import { AgriModal } from '../../component/agri-modal/agri-modal';
import { Message } from '../../model/message';
import { MessageModal } from '../../component/message-modal/message-modal';

@Component({
  selector: 'app-plante-page',
  imports: [AjoutPlanteCompo, AsyncPipe, InfoPlante, ConsoEauPipe, AgriModal, MessageModal],
  templateUrl: './plante-page.html',
  styleUrl: './plante-page.css',
})
export class PlantePage implements OnInit {
  private planteService: PlanteService = inject(PlanteService);
  private zoneService = inject(ZoneService);

  //DATA FROM BACK
  private refresh$: Subject<void> = new Subject<void>();
  protected plante$!: Observable<PlanteResponse[]>;
  protected planteZoneInfo$!: Observable<PlanteZoneInfoDTO[]>;

  protected message?: Message;

  //ETAT DE LA PAGE
  protected addPlanteOpen: boolean = false;

  ngOnInit(): void {
    this.plante$ = this.refresh$.pipe(
      startWith(0),
      switchMap(() => this.planteService.findAll()),
    );

    this.planteZoneInfo$ = this.refresh$.pipe(
      startWith(0),
      switchMap(() => this.planteService.getPlanteZoneInfo()),
    );
  }

  protected reload() {
    this.refresh$.next();
  }

  //ETAT DE LA PAGE
  public closeAddPlante() {
    this.reload();
    this.addPlanteOpen = false;
  }

  showMessage($event: Message): void {
    this.message = $event;
  }

  clearMessage(): void {
    this.message = undefined;
  }
}
