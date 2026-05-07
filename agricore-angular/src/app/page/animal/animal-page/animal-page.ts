import { Component, inject } from '@angular/core';
import { Observable, startWith, Subject, switchMap } from 'rxjs';
import { Animal } from '../../../model/animal';
import { AnimalService } from '../../../service/animal/animal-service';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink, RouterModule } from "@angular/router";
import { AnimalRequest } from '../../../dto/animal/animal-request';
import { FormField } from "@angular/forms/signals";

@Component({
  selector: 'app-animal-page',
  imports: [FormsModule, CommonModule, ReactiveFormsModule, RouterLink, RouterModule, FormField],
  templateUrl: './animal-page.html',
  styleUrl: './animal-page.css',
})
export class AnimalPage {

  private animalService: AnimalService = inject(AnimalService);
  private formBuilder = inject(FormBuilder);

  protected animals$!: Observable<Animal[]>;
  protected refresh$: Subject<void> = new Subject<void>();

  protected afficheDetailedInfos: boolean = false;
  protected afficheModifForm: boolean = false;
  protected animal!: Animal | null;
  protected age!: number;

  protected animalForm!: FormGroup;
  protected isMaleCtrl!: FormControl;
  protected dateNaissanceCtrl!: FormControl;
  protected dateVaccinationCtrl!: FormControl;
  protected especeCtrl!: FormControl;
  protected zoneCtrl!: FormControl;

  ngOnInit(): void {
    this.animals$ = this.refresh$.pipe(
      startWith(0), switchMap(() => this.animalService.findAll())
    );

    this.isMaleCtrl = this.formBuilder.control('', Validators.required);
    this.dateNaissanceCtrl = this.formBuilder.control(new Date(), Validators.required);
    this.dateVaccinationCtrl = this.formBuilder.control(new Date(), Validators.required);
    this.especeCtrl = this.formBuilder.control('', Validators.required);
    this.zoneCtrl = this.formBuilder.control(0, Validators.required);


    this.animalForm = this.formBuilder.group({
      male: this.isMaleCtrl,
      dateNaissance: this.dateNaissanceCtrl,
      dateVaccination: this.dateVaccinationCtrl,
      espece: this.especeCtrl,
      zoneId: this.zoneCtrl
    });
  }

  detailedInfos(animal: Animal) {
    this.afficheDetailedInfos = true;
    this.animal = animal;
  }

  closeDetailedInfos() {
    this.afficheDetailedInfos = false;
    this.animal = null;
  }

  updateInfos(animal: Animal) {
    this.animal = animal;
    this.afficheModifForm = true;

    this.animalForm.patchValue({
      male: animal.male,
      dateNaissasnce: animal.dateNaissance,
      dateVaccination: animal.dateVaccination,
      espece: animal.espece,
      zone: animal.zoneId
    });
  }

  validateUpdateInfos(): void {
    if (this.animalForm.invalid || !this.animal) return;

    const animalResponse: AnimalRequest = this.animalForm.getRawValue();
    animalResponse.id = this.animal.id;

    this.animalService.update(animalResponse).subscribe(() => {
      this.animal = null;
      this.afficheDetailedInfos = false;
      this.afficheModifForm = false;
    })
  }

cancel(): void {
  this.afficheModifForm = false;
  this.animal = null;
}
}
