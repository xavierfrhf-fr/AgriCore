import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { PlanteRequest } from '../../../dto/plante/plante-request';
import { PlanteService } from '../../../service/plante/plante-service';
import { DataService } from '../../../service/data-service';
import { Observable, startWith, Subject, switchMap } from 'rxjs';
import { PlanteData } from '../../../dto/plante/PlanteData';
import { AsyncPipe } from '@angular/common';
import { TempsPoussePipe } from '../../../pipe/temps-pousse-pipe';
import { ConsoEauPipe } from '../../../pipe/conso-eau-pipe';

@Component({
  selector: 'app-ajout-plante-compo',
  imports: [AsyncPipe, TempsPoussePipe, ConsoEauPipe],
  templateUrl: './ajout-plante-compo.html',
  styleUrl: './ajout-plante-compo.css',
})
export class AjoutPlanteCompo implements OnInit {
  //EVENT VERS PARENT
  @Output() closeAjout: EventEmitter<void> = new EventEmitter<void>();

  //DATA FROM BACK
  private refresh$: Subject<void> = new Subject<void>();
  protected planteData$!: Observable<PlanteData[]>;

  constructor(
    protected planteService: PlanteService,
    protected dataService: DataService,
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

  public createPlant(nomPlante: string) {
    let planteToAdd: PlanteRequest = { espece: nomPlante };
    this.planteService.add(planteToAdd).subscribe(() => this.reload());
  }
}
