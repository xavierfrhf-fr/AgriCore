import {
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  Output,
  SimpleChanges,
} from '@angular/core';
import { PlanteResponse } from '../../../dto/plante/plante-response';
import { interval, startWith, Subscription } from 'rxjs';
import { DecimalPipe } from '@angular/common';
import { PlanteService } from '../../../service/plante/plante-service';
import { MessageDTO } from '../../../dto/message-dto';
import { ConsoEauPipe } from '../../../pipe/conso-eau-pipe';

@Component({
  selector: 'tr[app-info-plante]',
  imports: [DecimalPipe, ConsoEauPipe],
  templateUrl: './info-plante.html',
  styleUrl: './info-plante.css',
})
export class InfoPlante implements OnDestroy, OnChanges {
  @Input() plante!: PlanteResponse;
  @Output() reloadEvent: EventEmitter<void> = new EventEmitter<void>();
  @Output() infoPlanteMessage: EventEmitter<{ text: string; type: 'success' | 'error' }> =
    new EventEmitter<{ text: string; type: 'success' | 'error' }>();
  protected matureEventDone: boolean = false;
  protected deadEventDone: boolean = false;

  protected humiditeActuelle: number = 0;
  protected croissanceActuelle: number = 0;

  private sub?: Subscription;

  constructor(
    private changeDetectorRef: ChangeDetectorRef,
    protected planteService: PlanteService,
  ) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (!this.plante) {
      return;
    }

    this.sub?.unsubscribe();

    this.matureEventDone = this.plante.mature;
    this.deadEventDone = this.plante.humidite <= 0;

    if (!this.matureEventDone && !this.deadEventDone) {
      this.activateActu();
    }
  }

  protected activateActu(): void {
    this.sub = interval(1000)
      .pipe(startWith(0))
      .subscribe(() => {
        this.updateValues();
        this.changeDetectorRef.detectChanges();
      });
  }

  ngOnDestroy(): void {
    console.log('unsub' + this.plante.id);
    this.sub?.unsubscribe();
  }

  private updateValues(): void {
    console.log('actu ' + this.plante.id);
    if (this.matureEventDone) {
      this.sub?.unsubscribe();
      return;
    }
    const deltaSecondeDepuisDernierUpdate =
      (Date.now() - new Date(this.plante.dernierUpdate).getTime()) / 1000;

    this.humiditeActuelle = Math.max(
      0,
      this.plante.humidite - (this.plante.consoEauParMin / 60) * deltaSecondeDepuisDernierUpdate,
    );

    this.croissanceActuelle = Math.min(
      100,
      this.plante.croissance + this.plante.croissanceParSec * deltaSecondeDepuisDernierUpdate,
    );

    if (this.croissanceActuelle >= 100) {
      this.reloadEvent.emit();
      this.matureEventDone = true;
    }

    if (this.humiditeActuelle <= 0) {
      this.reloadEvent.emit();
    }
  }

  public arroser(id: number) {
    this.planteService.arroser(id).subscribe(() => {
      this.reloadEvent.emit();
    });
  }

  recolter(id: number): void {
    this.planteService.recolter(id).subscribe( {
      next: (msg: MessageDTO) => {
      this.matureEventDone = false;
      this.croissanceActuelle = 0;
      this.humiditeActuelle = Math.min(this.humiditeActuelle,10)
      this.reloadEvent.emit();
      this.infoPlanteMessage.emit({ text: msg.message, type: msg.success ? 'success' : 'error' });
    },
      error: (err) => {
      alert(err?.error?.message || "Erreur récolte")
    }
    })

    }
  }

