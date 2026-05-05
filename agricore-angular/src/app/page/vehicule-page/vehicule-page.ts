import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Observable, Subject, startWith, switchMap } from 'rxjs';

import { VehiculeRequestDTO } from '../../dto/vehicule/request/vehicule-request-dto';
import { VehiculeResponseDTO } from '../../dto/vehicule/response/vehicule-response-dto';
import { TypeVehiculeDTO } from '../../dto/type-vehicule';
import { ZoneDTO } from '../../dto/zone/response/zone-dto';
import { VehiculeService } from '../../service/vehicule';
import { ZoneService } from '../../service/zone/zone-service';

@Component({
  selector: 'app-vehicule-page',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './vehicule-page.html',
  styleUrl: './vehicule-page.css',
})
export class VehiculePage implements OnInit {
  private vehiculeService = inject(VehiculeService);
  private zoneService = inject(ZoneService);
  private formBuilder = inject(FormBuilder);

  private refresh$ = new Subject<void>();

  protected vehicules$!: Observable<VehiculeResponseDTO[]>;
  protected zones$!: Observable<ZoneDTO[]>;

  protected afficheVehiculeForm = false;

  // États d'édition
  protected ligneEnEdition: VehiculeResponseDTO | null = null;

  // Formulaire création / édition
  protected vehiculeForm!: FormGroup;
  protected typeVehiculeCtrl!: FormControl;
  protected dateControleTechCtrl!: FormControl;
  protected carburantActuelCtrl!: FormControl;
  protected zoneIdCtrl!: FormControl;

  // Types de véhicules (enum values)
  protected typeVehicules$!: Observable<TypeVehiculeDTO[]>
  protected typeVehicules: TypeVehiculeDTO[] = [];

  ngOnInit(): void {
    this.vehicules$ = this.refresh$.pipe(
      startWith(null),
      switchMap(() => this.vehiculeService.findAll())
    );

    this.zones$ = this.zoneService.findAll();
    this.typeVehicules$ = this.vehiculeService.getTypes();
    this.typeVehicules$.subscribe(types => this.typeVehicules = types);
    this.initForm();
  }

  private initForm(): void {
    this.typeVehiculeCtrl = this.formBuilder.control('', Validators.required);
    this.dateControleTechCtrl = this.formBuilder.control('', Validators.required);
    this.carburantActuelCtrl = this.formBuilder.control(0, [Validators.required, Validators.min(0)]);
    this.zoneIdCtrl = this.formBuilder.control('', Validators.required);

    this.vehiculeForm = this.formBuilder.group({
      typeVehicule: this.typeVehiculeCtrl,
      dateControleTech: this.dateControleTechCtrl,
      carburantActuel: this.carburantActuelCtrl,
      zoneId: this.zoneIdCtrl,
    });

    // Validation dynamique pour la capacité du réservoir
    this.typeVehiculeCtrl.valueChanges.subscribe(value => {
      this.updateCarburantValidators(value);
    });
  }

  private updateCarburantValidators(type: TypeVehiculeDTO): void {
    if (type) {
      this.carburantActuelCtrl.setValidators([
        Validators.required,
        Validators.min(0),
        Validators.max(type.capaciteReservoir)
      ]);
    } else {
      this.carburantActuelCtrl.setValidators([Validators.required, Validators.min(0)]);
    }
    this.carburantActuelCtrl.updateValueAndValidity();
  }

  private reload(): void {
    this.refresh$.next();
  }

  // --- Création ---
  ouvrirCreer(): void {
    this.annulerModifier();
    this.vehiculeForm.reset();
    this.afficheVehiculeForm = true;
  }

  validerCreer(): void {
    if (this.vehiculeForm.invalid) return;

    const dto: VehiculeRequestDTO = this.vehiculeForm.getRawValue();
    dto.typeVehicule = this.typeVehiculeCtrl.value.name;
    this.vehiculeService.add(dto).subscribe(() => {
      this.afficheVehiculeForm = false;
      this.vehiculeForm.reset();
      this.reload();
    });
  }

  annulerCreer(): void {
    this.afficheVehiculeForm = false;
    this.vehiculeForm.reset();
  }

  // --- Modification ---
  ouvrirModifier(vehicule: VehiculeResponseDTO): void {
    this.annulerCreer();
    this.vehiculeForm.reset();
    this.ligneEnEdition = vehicule;

    this.vehiculeForm.patchValue({
      typeVehicule: this.typeVehicules.find(t => t.name === vehicule.typeVehicule),
      dateControleTech: vehicule.dateControleTech,
      carburantActuel: vehicule.carburantActuel,
      zoneId: vehicule.zoneId,
    });

    this.updateCarburantValidators(this.typeVehiculeCtrl.value);
  }

  validerModifier(): void {
    if (this.vehiculeForm.invalid || !this.ligneEnEdition) return;

    const dto: VehiculeRequestDTO = this.vehiculeForm.getRawValue();
    dto.typeVehicule = this.typeVehiculeCtrl.value.name;
    this.vehiculeService.update(this.ligneEnEdition.id, dto).subscribe(() => {
      this.ligneEnEdition = null;
      this.vehiculeForm.reset();
      this.reload();
    });
  }

  annulerModifier(): void {
    this.ligneEnEdition = null;
    this.vehiculeForm.reset();
  }

  // --- Suppression ---
  supprimer(vehicule: VehiculeResponseDTO): void {
    this.vehiculeService.deleteById(vehicule.id).subscribe(() => this.reload());
  }

  // --- Helpers ---
  isLigneEnEdition(vehicule: VehiculeResponseDTO): boolean {
    return this.ligneEnEdition?.id === vehicule.id;
  }

  // Méthodes de la classe Vehicule
  rappelControle(vehicule: VehiculeResponseDTO): boolean {
    const date = new Date(vehicule.dateControleTech);
    return date > new Date();
  }

  delaiAvantControle(vehicule: VehiculeResponseDTO): number {
    const date = new Date(vehicule.dateControleTech);
    const now = new Date();
    return Math.ceil((date.getTime() - now.getTime()) / (1000 * 60 * 60 * 24));
  }
}
