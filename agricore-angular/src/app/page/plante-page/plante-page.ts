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

@Component({
  selector: 'app-plante-page',
  imports: [AjoutPlanteCompo, AsyncPipe, InfoPlante, ConsoEauPipe],
  templateUrl: './plante-page.html',
  styleUrl: './plante-page.css',
})
export class PlantePage implements OnInit {
  private planteService: PlanteService = inject(PlanteService);
  private zoneService = inject(ZoneService);

  //DATA FROM BACK
  private refresh$: Subject<void> = new Subject<void>();
  protected plante$!: Observable<PlanteResponse[]>;

  recolteMessage: { text: string; type: 'success' | 'error' } | null = null;

  //ETAT DE LA PAGE
  protected addPlanteOpen: boolean = false;

  ngOnInit(): void {
    this.plante$ = this.refresh$.pipe(
      startWith(0),
      switchMap(() => this.planteService.findAll()),
    );
  }

  private reload() {
    this.refresh$.next();
  }

  public arroser(id: number) {
    this.planteService.arroser(id).subscribe(() => this.reload());
  }

  //ETAT DE LA PAGE
  public closeAddPlante() {
    this.reload();
    this.addPlanteOpen = false;
  }

  onPlanteMature(id: number): void {
    console.log('Plante mature', id);
  }

  onDeadEvent(id: number): void {
    console.log('Plante morte', id);
  }

  recolter(id: number): void {
    this.planteService.recolter(id).subscribe((msg:MessageDTO) => {
      this.reload();
      this.showRecolteMessage(msg.message, msg.success ? 'success' : 'error');
    }

  );
  }

  private showRecolteMessage(text: string, type: 'success' | 'error') {
    this.recolteMessage = { text, type };
    setTimeout(() => {
      this.recolteMessage = null;
    }, 5000);
  }
}
