import { ChangeDetectorRef, Component, Input } from '@angular/core';
import { Animal } from '../../../model/animal';
import { delay, delayWhen, interval, startWith, Subscription } from 'rxjs';

@Component({
  selector: 'app-animal-production-delay',
  imports: [],
  templateUrl: './animal-production-delay.html',
  styleUrl: './animal-production-delay.css',
})
export class AnimalProductionDelay {
  @Input({ required: true }) animal!: Animal;

  produced: number = 0;

  remainingTime: number = 0;
  displayTimer: string = '00:00';

  private sub?: Subscription;
  private nextProductionDate!: Date;

  constructor(private cdr:ChangeDetectorRef) {
  }

  ngOnInit(): void {
    if (!this.animal.producer || !this.animal.nextProduction || !this.animal.delayProd) {
      return;
    }

    this.nextProductionDate = new Date(this.animal.nextProduction);

    this.sub = interval(1000)
      .pipe(startWith(0))
      .subscribe(() => {
        this.updateTimer();
      });
  }

  ngOnDestroy(): void {
    this.sub?.unsubscribe();
  }

  private updateTimer(): void {
    //console.log("timer update"+this.displayTimer)
    const now = Date.now();
    this.remainingTime = this.nextProductionDate.getTime() - now;

    if (this.remainingTime <= 0 && this.animal.qtyProduced) {
      this.newProduction(now);
    }
    this.cdr.detectChanges();

    this.displayTimer = this.formatDuration(this.remainingTime);
  }

  private newProduction(now: number): void {
    const delay = this.animal.delayProd! * 60 * 1000;
    this.produced++;
    this.nextProductionDate = new Date(now + delay);
    this.remainingTime = delay;
  }

  private formatDuration(ms: number): string {
    const totalSeconds = Math.max(0, Math.floor(ms / 1000));

    const minutes = Math.floor(totalSeconds / 60);
    const seconds = totalSeconds % 60;

    return `${this.pad(minutes)}:${this.pad(seconds)}`;
  }

  private pad(value: number): string {
    return value.toString().padStart(2, '0');
  }
}
