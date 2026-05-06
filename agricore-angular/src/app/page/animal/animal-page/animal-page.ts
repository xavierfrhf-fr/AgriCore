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

  protected afficheDetailedInfos: boolean = false;
  protected animal!: Animal;

    ngOnInit(): void {

      this.animals$ = this.refresh$.pipe(
      startWith(0), switchMap(() => this.animalService.findAll())
    );
  }

  detailedInfos(animal: Animal) {
    this.afficheDetailedInfos = true;
    this.animal = animal;
  }

  closeDetailedInfos() {
    this.afficheDetailedInfos = false;
    this.animal = {id: 0, male: true, dateNaissance: new Date(), dateVaccination: new Date(), espece: "", zone: "", pathSprite: "", nomAffichage: "", delaisVaccination: 0};
  }
}
