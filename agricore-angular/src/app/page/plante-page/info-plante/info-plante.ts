import {
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnDestroy,
  OnInit,
  Output,
} from '@angular/core';
import { PlanteResponse } from '../../../dto/plante/plante-response';
import { interval, startWith, Subscription } from 'rxjs';
import { DecimalPipe } from '@angular/common';

@Component({
  selector: 'app-info-plante',
  imports: [DecimalPipe],
  templateUrl: './info-plante.html',
  styleUrl: './info-plante.css',
})
export class InfoPlante implements OnInit, OnDestroy {
  @Input() plante!: PlanteResponse;
  @Output() planteMature: EventEmitter<number> = new EventEmitter<number>();
  @Output() deadEvent: EventEmitter<number> = new EventEmitter<number>();
  protected matureEventDone: boolean = false;
  protected deadEventDone: boolean = false;

  protected humiditeActuelle: number = 0;
  protected croissanceActuelle: number = 0;

  private sub?: Subscription;

  constructor(private changeDetectorRef: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.sub = interval(5000)
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
    const deltaSecondeDepuisDernierUpdate =
      (Date.now() - new Date(this.plante.dernierUpdate).getTime()) / 1000;

    if (!this.matureEventDone)
      this.humiditeActuelle = Math.max(
        0,
        this.plante.humidite - (this.plante.consoEauParMin / 60) * deltaSecondeDepuisDernierUpdate,
      );

    this.croissanceActuelle = Math.min(
      100,
      this.plante.croissance + this.plante.croissanceParSec * deltaSecondeDepuisDernierUpdate,
    );

    if (this.croissanceActuelle >= 100) {
      this.planteMature.emit(this.plante.id);
      this.matureEventDone = true;
    }

    if (this.humiditeActuelle <= 0) {
      this.croissanceActuelle = 0;
      this.deadEvent.emit(this.plante.id);
      this.deadEventDone = true;
    }

    if (this.matureEventDone || this.deadEventDone) {
      console.log('unsub' + this.plante.id);
      this.sub?.unsubscribe();
    }
  }
}
