import { Component, inject } from '@angular/core';
import { Observable, startWith, Subject, switchMap } from 'rxjs';
import { Animal } from '../../../model/animal';
import { AnimalService } from '../../../service/animal/animal-service';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink, RouterModule } from "@angular/router";
import { AnimalRequest } from '../../../dto/animal/request/animal-request';
import { FormField } from "@angular/forms/signals";
import { AgriSelect } from '../../../component/form/agri-select/agri-select';
import { ZoneService } from '../../../service/zone/zone-service';
import { ZoneDTO } from '../../../dto/zone/response/zone-dto';
import { AnimalData } from '../../../dto/animal/response/animal-data';
import { DataService } from '../../../service/data-service';
import { VehiculeResponseDTO } from '../../../dto/vehicule/response/vehicule-response-dto';
import { VehiculeService } from '../../../service/vehicule';

@Component({
  selector: 'app-animal-page',
  imports: [FormsModule, CommonModule, ReactiveFormsModule, RouterLink, RouterModule],
  templateUrl: './animal-page.html',
  styleUrl: './animal-page.css',
})
export class AnimalPage {

  private animalService: AnimalService = inject(AnimalService);
  private dataService: DataService = inject(DataService);
  private zoneService: ZoneService = inject(ZoneService);
  protected vehiculeService: VehiculeService = inject(VehiculeService);
  private formBuilder = inject(FormBuilder);

  protected animals$!: Observable<Animal[]>;
  protected especesExistantes$!: Observable<AnimalData[]>;
  protected zonesDisponibles$!: Observable<ZoneDTO[]>;
  protected refresh$: Subject<void> = new Subject<void>();
  protected refreshEspeces$: Subject<void> = new Subject<void>();
  protected vehicules$!: Observable<VehiculeResponseDTO[]>;
  

  protected afficheDetailedInfos: boolean = false;
  protected afficheModifForm: boolean = false;
  protected afficheCreateForm: boolean = false;
  protected createMaleSelected: boolean = false;

  protected animal!: Animal | null;
  protected age!: number;
  protected zone$!: Observable<ZoneDTO>;

  protected animalForm!: FormGroup;
  protected isMaleCtrl!: FormControl;
  protected dateNaissanceCtrl!: FormControl;
  protected dateVaccinationCtrl!: FormControl;
  protected especeCtrl!: FormControl;
  protected zoneCtrl!: FormControl;

  protected initialDate = new Date("1970-01-01");

  ngOnInit(): void {

    this.vehicules$ = this.refresh$.pipe(
      startWith(null),
      switchMap(() => this.vehiculeService.findAll()) //appel api vers le findAll vehicule
    );

    this.animals$ = this.refresh$.pipe(
      startWith(0), switchMap(() => this.animalService.findAll())
    );

    this.especesExistantes$ = this.refreshEspeces$.pipe(
      startWith(0), switchMap(() => this.dataService.getAnimalData())
    );

    this.isMaleCtrl = this.formBuilder.control('false', Validators.required);
    this.dateNaissanceCtrl = this.formBuilder.control(new Date(), Validators.required);
    this.dateVaccinationCtrl = this.formBuilder.control('');
    this.especeCtrl = this.formBuilder.control(null, Validators.required);
    this.zoneCtrl = this.formBuilder.control(-1);


    this.animalForm = this.formBuilder.group({
      male: this.isMaleCtrl,
      dateNaissance: this.dateNaissanceCtrl,
      dateVaccination: this.dateVaccinationCtrl,
      espece: this.especeCtrl,
      zoneId: this.zoneCtrl,
      vehiculeId: [null, Validators.required]
    });

    this.isMaleCtrl.valueChanges.subscribe(value => {
      this.createMaleSelected = value === 'true';
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

    const animalRequest: AnimalRequest = this.animalForm.getRawValue();
    animalRequest.id = this.animal.id;

    this.animalService.update(animalRequest).subscribe(() => {
      this.animal = null;
      this.afficheDetailedInfos = false;
      this.afficheModifForm = false;
      this.animalForm.reset();
      this.refresh$.next();
    })
  }

  cancel(): void {
    this.afficheModifForm = false;
    this.afficheCreateForm = false;
    this.animal = null;
    this.animalForm.reset();
  }

  deleteAnimal(id: number) {
    this.animalService.delete(id).subscribe(() => {
      this.refresh$.next();
    })
  }

  vacciner(animal: Animal) {
    this.animalForm.patchValue({
      male: animal.male ? 'true' : 'false',
      dateNaissance: animal.dateNaissance ? new Date(animal.dateNaissance).toISOString().split('T')[0] : '',
      dateVaccination: animal.dateVaccination ? new Date(Date.now()).toISOString().split('T')[0] : '',
      espece: animal.espece,
      zoneId: animal.zoneId
    });

    const animalRequest: AnimalRequest = this.animalForm.getRawValue();
    animalRequest.id = animal.id;

    this.animalService.update(animalRequest).subscribe(() => {
      this.animalForm.reset();
      this.refresh$.next();
      this.animalService.findById(animal.id).subscribe(updated => {
        this.animal = updated;
      });
    })
  }

  openCreateForm() {
    this.afficheCreateForm = true;
  }

  createAnimal() {
    if (this.animalForm.invalid) return;

    const animalRequest: AnimalRequest = this.animalForm.getRawValue();

    this.animalService.add(animalRequest).subscribe( {
      next: (animalCreated) => {
        let vehiculeId = this.animalForm.value.vehiculeId;
        
        this.vehiculeService.acheterAnimal(
          animalCreated.id, vehiculeId
        ).subscribe( {

          next: () => {
            this.animal = null;
            this.afficheCreateForm = false;
            this.animalForm.reset();
            this.refresh$.next();

          },

           error: (err) => {
          alert(err?.error?.message || "Erreur achat animal");
        }

        })
      }
      
    })
  }
}
