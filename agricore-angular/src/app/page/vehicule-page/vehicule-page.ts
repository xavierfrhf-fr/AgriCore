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

  //injection des services (pour appeler le crud )
  private vehiculeService = inject(VehiculeService);
  private zoneService = inject(ZoneService);
  private formBuilder = inject(FormBuilder);

  //permet de refresh les données quand demandé 
  private refresh$ = new Subject<void>();

  //les données affichées dans le html
  protected vehicules$!: Observable<VehiculeResponseDTO[]>;
  protected zones$!: Observable<ZoneDTO[]>;

  //etat du formulaire 
  protected afficheVehiculeForm = false;

  // États d'édition | stocke le vehicule en édition null si non
  protected ligneEnEdition: VehiculeResponseDTO | null = null;

  // Formulaire création / édition
  protected vehiculeForm!: FormGroup;

  //champs du formulaire
  protected typeVehiculeCtrl!: FormControl;
  protected dateControleTechCtrl!: FormControl;
  protected carburantActuelCtrl!: FormControl;
  protected zoneIdCtrl!: FormControl;

  // Types de véhicules (enum values)
  protected typeVehicules$!: Observable<TypeVehiculeDTO[]>
  protected typeVehicules: TypeVehiculeDTO[] = [];

  //executé au chargement du composant
  ngOnInit(): void {
    this.vehicules$ = this.refresh$.pipe(
      startWith(null),
      switchMap(() => this.vehiculeService.findAll()) //appel api vers le findAll vehicule
    );

    //chargement des zones
    this.zones$ = this.zoneService.findAll();

    //récupération des types de véhicules depuis le backend sur le endpoint /types
    this.typeVehicules$ = this.vehiculeService.getTypes(); 
    this.typeVehicules$.subscribe(types => this.typeVehicules = types); //valeur local des types de véhicules
    this.initForm(); //appel de la fonction pour initialiser le formulaire
  }


  private initForm(): void {
    //champs de formulaire obligatoire
    this.typeVehiculeCtrl = this.formBuilder.control('', Validators.required);
    this.dateControleTechCtrl = this.formBuilder.control('', Validators.required);
    this.carburantActuelCtrl = this.formBuilder.control(0, [Validators.required, Validators.min(0)]);//verif que le minimum du reservoir soit 0
    this.zoneIdCtrl = this.formBuilder.control('', Validators.required);

    //création du formulaire en regroupant tous les champs 
    this.vehiculeForm = this.formBuilder.group({
      typeVehicule: this.typeVehiculeCtrl,
      dateControleTech: this.dateControleTechCtrl,
      carburantActuel: this.carburantActuelCtrl,
      zoneId: this.zoneIdCtrl,
    });

    // Validation dynamique pour la capacité du réservoir quand le type change 
    this.typeVehiculeCtrl.valueChanges.subscribe(value => {
      this.updateCarburantValidators(value);
    });
  }

  // fonction pour set la valeur du validator de caburant quand le typevehicule change 
  private updateCarburantValidators(type: TypeVehiculeDTO): void {
    if (type) {
      this.carburantActuelCtrl.setValidators([ //redéfinition des règle de validation
        Validators.required,
        Validators.min(0),
        Validators.max(type.capaciteReservoir)
      ]);
    } else {
      this.carburantActuelCtrl.setValidators([Validators.required, Validators.min(0)]); //règle de base si pas de type
    }
    this.carburantActuelCtrl.updateValueAndValidity(); // recalcule de la validité du champ
  }

  //déclenche un refresh des véhicules
  private reload(): void {
    this.refresh$.next();
  }

  // --- Création ---
  ouvrirCreer(): void {
    this.annulerModifier();
    this.vehiculeForm.reset(); //reset formulaire
    this.afficheVehiculeForm = true; //affiche le formulaire 
  }

  validerCreer(): void { 
    if (this.vehiculeForm.invalid) return;

    const dto: VehiculeRequestDTO = this.vehiculeForm.getRawValue(); //récupère les valeurs du formulaire 
    dto.typeVehicule = this.typeVehiculeCtrl.value.name; //mapping de la valeur typevehicule du dto avec la valeur du select du form 
    this.vehiculeService.add(dto).subscribe(() => {  //appel l'api post 
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
