import { Component, inject } from '@angular/core';
import { Observable, startWith, Subject, switchMap } from 'rxjs';
import { Animal } from '../../../model/animal';
import { AnimalService } from '../../../service/animal/animal-service';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-animal-page',
  imports: [FormsModule, CommonModule, ReactiveFormsModule],
  templateUrl: './animal-page.html',
  styleUrl: './animal-page.css',
})
export class AnimalPage {

  private animalService: AnimalService = inject(AnimalService);

  protected animals$!: Observable<Animal[]>;
  protected refresh$: Subject<void> = new Subject<void>();

    ngOnInit(): void {

      this.animals$ = this.refresh$.pipe(
      startWith(0), switchMap(() => this.animalService.findAll())
    );
  }
}
