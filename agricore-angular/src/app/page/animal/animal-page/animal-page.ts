import { Component, inject } from '@angular/core';
import { Observable, startWith, Subject, switchMap } from 'rxjs';
import { Animal } from '../../../model/animal';
import { AnimalService } from '../../../service/animal/animal-service';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink, RouterModule } from "@angular/router";
import { AnimalRequest } from '../../../dto/animal/animal-request';
import { FormField } from "@angular/forms/signals";
import { AgriSelect } from '../../../component/form/agri-select/agri-select';
import { ZoneService } from '../../../service/zone/zone-service';
import { ZoneDTO } from '../../../dto/zone/response/zone-dto';

@Component({
  selector: 'app-animal-page',
  imports: [FormsModule, CommonModule, ReactiveFormsModule, RouterLink, RouterModule],
  templateUrl: './animal-page.html',
  styleUrl: './animal-page.css',
})
export class AnimalPage {

  private animalService: AnimalService = inject(AnimalService);
  private zoneService: ZoneService = inject(ZoneService);
  private formBuilder = inject(FormBuilder);

  protected animals$!: Observable<Animal[]>;
  protected zonesDisponibles$!: Observable<ZoneDTO[]>;
  protected refresh$: Subject<void> = new Subject<void>();

  protected afficheDetailedInfos: boolean = false;
  protected afficheModifForm: boolean = false;
  protected animal!: Animal | null;
  protected age!: number;
  protected zone$!: Observable<ZoneDTO>;

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
    this.zone$ = this.refresh$.pipe(
      startWith(0), switchMap(() => this.zoneService.findById(animal.zoneId))
    );
    this.zonesDisponibles$ = this.refresh$.pipe(
      startWith(0),
      switchMap(() => this.zone$),
      switchMap(zone => this.zoneService.findByName(zone.nomZone))
    );

    console.log(this.zonesDisponibles$)

    this.animalForm.patchValue({
      male: animal.male ? 'true' : 'false',
      dateNaissance: animal.dateNaissance ? new Date(animal.dateNaissance).toISOString().split('T')[0] : '',
      dateVaccination: animal.dateVaccination ? new Date(animal.dateVaccination).toISOString().split('T')[0] : '',
      espece: animal.espece,
      zoneId: animal.zoneId
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
      this.animalForm.reset();
      this.refresh$.next();
    })
  }

  cancel(): void {
    this.afficheModifForm = false;
    this.animal = null;
  }

  deleteAnimal(id: number) {
    this.animalService.delete(id).subscribe(() => {
      this.refresh$.next();
    })
  }
}
