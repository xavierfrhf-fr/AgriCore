import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { PlanteRequest } from '../../../dto/plante/plante-request';
import { PlanteService } from '../../../service/plante/plante-service';
import { DataService } from '../../../service/data-service';
import { Observable, startWith, Subject, switchMap } from 'rxjs';
import { PlanteData } from '../../../dto/plante/PlanteData';
import { AsyncPipe } from '@angular/common';
import { TempsPoussePipe } from '../../../pipe/temps-pousse-pipe';
import { ConsoEauPipe } from '../../../pipe/conso-eau-pipe';
import { ZoneService } from '../../../service/zone/zone-service';
import { ZoneRequest } from '../../../dto/zone/request/zone-request';
import { Message } from '../../../model/message';

@Component({
  selector: 'app-ajout-plante-compo',
  imports: [AsyncPipe, TempsPoussePipe, ConsoEauPipe],
  templateUrl: './ajout-plante-compo.html',
  styleUrl: './ajout-plante-compo.css',
})
export class AjoutPlanteCompo implements OnInit {
  //EVENT VERS PARENT
  @Output() closeAjout: EventEmitter<void> = new EventEmitter<void>();
  @Output() reloadPage: EventEmitter<void> = new EventEmitter<void>();
  @Output() messageEvent: EventEmitter<Message> = new EventEmitter<Message>();

  //DATA FROM BACK
  private refresh$: Subject<void> = new Subject<void>();
  protected planteData$!: Observable<PlanteData[]>;

  statusMessage: { text: string; type: 'message' | 'error' } | null = null;

  constructor(
    protected planteService: PlanteService,
    protected dataService: DataService,
    protected zoneService: ZoneService,
  ) {}

  ngOnInit(): void {
    this.planteData$ = this.refresh$.pipe(
      startWith(0),
      switchMap(() => this.dataService.getPlanteData()),
    );
  }

  private reload() {
    this.refresh$.next();
  }

  protected closeAddPlante(): void {
    this.closeAjout.emit();
  }

  protected createZone(nomZone: string) {
    let zoneReq: ZoneRequest = { nomZone: nomZone } as ZoneRequest;

    this.zoneService.createZoneWithRandomPos(zoneReq).subscribe({
      next: (success) => {
        if (success) {
          this.showMessage(`La zone ${nomZone} a été placée avec succès !`, 'message');
          this.reload();
        } else {
          this.showMessage(`Échec du placement de la zone ${nomZone}.`, 'error');
        }
      },
      error: (err) => {
        this.showMessage('Erreur lors de la communication avec le serveur.', 'error');
      },
    });
  }

  protected createPlant(nomPlante: string) {
    let planteToAdd: PlanteRequest = { espece: nomPlante };
    this.planteService.add(planteToAdd).subscribe(
      () => {
        this.reload();
        this.reloadPage.emit();
        this.showMessage(`La plante ${nomPlante} a été plantée avec succès !`, "message");
      }
    );
  }

  private showMessage(text: string, type: 'message' | 'error') {
    const msg = {} as Message;
    msg.message = text;
    msg.type = type;
    msg.position = {
      top:'35%',
      left: '80%'
    };
    msg.timeout= 5000;

    this.messageEvent.emit(msg)

  }
}
