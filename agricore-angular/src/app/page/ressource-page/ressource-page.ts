import {CommonModule} from '@angular/common';
import {AfterViewInit, Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {combineLatest, map, Observable, startWith, Subject, switchMap} from 'rxjs';

import {AgriModal} from '../../component/agri-modal/agri-modal';
import {AgriSelect} from '../../component/form/agri-select/agri-select';
import {AgriSubmit} from '../../component/form/agri-submit/agri-submit';
import {NavigationAgricore} from '../../component/navigation/navigation';
import {PrixRequestDto} from '../../dto/ressource/request/prix-request-dto';
import {RessourceRequestDto} from '../../dto/ressource/request/ressource-request-dto';
import {PrixResponseDto} from '../../dto/ressource/response/prix-response-dto';
import {RessourceResponseDto} from '../../dto/ressource/response/ressource-response-dto';
import {RessourceDataDto} from '../../dto/ressource/ressource-data-dto';
import {UniteDataDto} from '../../dto/ressource/unite-data-dto';
import {UniteService} from '../../service/ressource/unite-service';

import {NomRessourceService} from './../../service/ressource/nom-ressource-service';
import {RessourceService} from './../../service/ressource/ressource-service';

@Component({
  imports: [
    CommonModule, ReactiveFormsModule, AgriSelect, AgriSubmit, AgriModal,
    NavigationAgricore
  ],
  templateUrl: './ressource-page.html',
  styleUrl: './ressource-page.css',
})
export class RessourcePage implements OnInit, AfterViewInit {
  private ressourceService = inject(RessourceService);
  private nomRessourceService = inject(NomRessourceService);
  private uniteService = inject(UniteService);
  private formBuilder = inject(FormBuilder);

  private refresh$ = new Subject<void>();

  protected ressources$!:
      Observable<RessourceResponseDto[]>;  // D'autres à ajouter

  protected nomRessources$!: Observable<RessourceDataDto[]>;
  protected nomRessourcesList: RessourceDataDto[] = [];
  protected nomRessourcesFiltre$!: Observable<RessourceDataDto[]>;

  protected unites$!: Observable<UniteDataDto[]>;
  protected unitesList: UniteDataDto[] = [];

  protected prix$!: Observable<PrixResponseDto[]>;

  protected afficheRessourceForm = false;
  protected affichePrixForm = false;

  // États d'édition

  protected ligneEnEdition: RessourceResponseDto|null = null;

  protected celluleEnEdition: {id: string; champ: string;}|null = null;

  // Formulaire partagé création / édition inline
  protected ressourceForm!: FormGroup;
  protected nomCtrl!: FormControl;
  protected quantiteCtrl!: FormControl;
  protected stockMinCtrl!: FormControl;

  // Formulaire spécifique pour la création de prix
  // prixPar: number;
  // quantiteLot: number;
  // unite: string;
  protected prixCtrl!: FormGroup;
  protected prixParCtrl!: FormControl;
  protected quantiteLotCtrl!: FormControl;
  protected uniteCtrl!: FormControl;
  // ajouter les controles nécessaires pour le prix

  // TODO: formulaire transformation (à extraire dans son propre composant)
  // protected transformationForm!: FormGroup;
  // protected produitTransfoCtrl!: FormControl;
  // protected quantiteDesireCtrl!: FormControl;
  // protected partielCtrl!: FormControl;
  // protected bypassStockMinCtrl!: FormControl;

  protected colWidths: string[] = [];


  ngOnInit(): void {
    this.ressources$ = this.refresh$.pipe(
        startWith(null), switchMap(() => this.ressourceService.findAll()));

    this.nomRessources$ = this.refresh$.pipe(
        startWith(null), switchMap(() => this.nomRessourceService.findAll()));

    // Filtrage côté client pour n'afficher que les ressources non encore
    // utilisées CombineLatest prends les deux fluxs ressources et nomRessources
    // et à chaque fois que l'un change, met à jour les deux
    this.nomRessourcesFiltre$ =
        combineLatest([
          this.nomRessources$, this.ressources$
        ]).pipe(map(([nomDisponibles, ressourcesPresentes]) => {
          const nomsUtilises = new Set(ressourcesPresentes.map(
              ressourcePresente => ressourcePresente.nom));
          return nomDisponibles.filter(
              nomRessource => !nomsUtilises.has(nomRessource.nom));
        }));

    this.nomRessourceService.findAll().subscribe(list => {
      this.nomRessourcesList = list;
    });

    this.unites$ = this.refresh$.pipe(
        startWith(null), switchMap(() => this.uniteService.findAll()));

    this.uniteService.findAll().subscribe(list => {
      this.unitesList = list;
    });

    this.nomRessourceService.findAll().subscribe(list => {
      this.nomRessourcesList = list;
    });

    this.initForm();
  }

  // Observe la largeur des colonnes après l'init et la fixe pour éviter que
  // les colonnes bougent lors des changements d'état création, édition
  ngAfterViewInit(): void {
    const ths = Array.from(document.querySelectorAll('table thead th'));
    this.colWidths = ths.map(th => `${th.getBoundingClientRect().width}px`);
  }

  private initForm(): void {
    // Prix

    this.prixParCtrl =
        this.formBuilder.control(0, [Validators.required, Validators.min(0)]);

    this.quantiteLotCtrl =
        this.formBuilder.control(1, [Validators.required, Validators.min(1)]);

    this.uniteCtrl = this.formBuilder.control('', Validators.required);

    this.prixCtrl = this.formBuilder.group({
      prixPar: this.prixParCtrl,
      quantiteLot: this.quantiteLotCtrl,
      unite: this.uniteCtrl
    });

    // Ressource

    this.nomCtrl = this.formBuilder.control('', Validators.required);

    this.quantiteCtrl =
        this.formBuilder.control(0, [Validators.required, Validators.min(0)]);

    this.stockMinCtrl = this.formBuilder.control(0, Validators.min(0));

    this.ressourceForm = this.formBuilder.group({
      nom: this.nomCtrl,
      quantite: this.quantiteCtrl,
      stockMin: this.stockMinCtrl,
      prixLot: this.prixCtrl
    });
  }



  getData(nom: string): RessourceDataDto|undefined {
    return this.nomRessourcesList.find(r => r.nom === nom);
  }

  getUniteAffichage(nom: string): string|undefined {
    return this.unitesList.find(unite => unite.nom === nom)?.affichage;
  }

  hasSurplus(ressource: RessourceResponseDto): boolean {
    return ressource.quantite > ressource.stockMin;
  }

  private reload(): void {
    this.refresh$.next();
  }

  // --- Création ---

  ouvrirCreer(): void {
    this.annulerModifier();
    this.annulerEditionCellule();
    this.ressourceForm.reset();
    this.afficheRessourceForm = true;
  }

  validerCreer(): void {
    if (this.ressourceForm.invalid) return;

    const dto: RessourceRequestDto = this.ressourceForm.getRawValue();
    this.ressourceService.add(dto).subscribe(() => {
      this.afficheRessourceForm = false;
      this.ressourceForm.reset();
      this.reload();
    });
  }

  annulerCreer(): void {
    this.afficheRessourceForm = false;
    this.ressourceForm.reset();
  }


  ouvrirCreerPrix(): void {
    this.affichePrixForm = true;
  }

  validerCreerPrix(): void {
    if (this.prixCtrl.invalid) return;

    if (this.ligneEnEdition) {
      // Mode édition d'une ligne existante : PATCH via ressourceService
      const dto = {
        prixLot: this.prixCtrl.getRawValue(),
      };
      this.ressourceService.patch(this.ligneEnEdition.id, dto as any)
          .subscribe(() => {
            this.affichePrixForm = false;
            this.ligneEnEdition = null;
            this.reload();
          });
    } else {
      // Mode création : le prix est déjà dans prixCtrl, embarqué dans
      // ressourceForm Le bouton "Créer" de la modale principale enverra tout
      // d'un coup
      this.affichePrixForm = false;
    }
  }

  annulerCreerPrix(): void {
    this.affichePrixForm = false;
    // On ne réinitialise pas le formulaire principal pour ne pas perdre les
    // autres champs
  }

  // --- Modification ligne complète ---


  ouvrirModifier(ressource: RessourceResponseDto): void {
    this.annulerCreer();
    this.annulerEditionCellule();  // Au début de transformer aussi

    this.ressourceForm.reset();
    this.ligneEnEdition = ressource;

    this.ressourceForm.patchValue({
      nom: ressource.nom,
      quantite: ressource.quantite,
      stockMin: ressource.stockMin,
      prixLot: {
        prixPar: ressource.prixLot.prixPar,
        quantiteLot: ressource.prixLot.quantiteLot,
        unite: ressource.prixLot.unite,
      }
    });
  }

  validerModifier(): void {  // refactor avec les deux creer ?
    if (this.ressourceForm.invalid || !this.ligneEnEdition) return;

    const dto: RessourceRequestDto = this.ressourceForm.getRawValue();
    dto.id = this.ligneEnEdition.id;

    this.ressourceService.update(dto).subscribe(() => {
      this.ligneEnEdition = null;
      this.afficheRessourceForm = false;
      this.ressourceForm.reset();
      this.reload();
    });
  }

  validerModifierPrix(): void {
    if (this.prixCtrl.invalid) return;

    if (this.ligneEnEdition) {
      // Mode édition d'une ligne existante : PATCH via ressourceService
      const dto = {
        prixLot: this.prixCtrl.getRawValue(),
      };
      this.ressourceService.patch(this.ligneEnEdition.id, dto as any)
          .subscribe(() => {
            this.affichePrixForm = false;
            this.ligneEnEdition = null;
            this.reload();
          });
    } else {
      // Mode création : le prix est déjà dans prixCtrl, embarqué dans
      // ressourceForm Le bouton "Créer" de la modale principale enverra tout
      // d'un coup
      this.affichePrixForm = false;
    }
  }

  annulerModifier(): void {
    this.ligneEnEdition = null;
    this.ressourceForm.reset();
  }

  // --- Édition cellule unique (future PATCH) ---

  modifierCellule(ressource: RessourceResponseDto, champ: string): void {
    this.afficheRessourceForm = false;
    this.annulerModifier();

    this.celluleEnEdition = {
      id: ressource.id,
      champ,
    };
  }

  validerCellule(): void {}

  annulerEditionCellule(): void {
    this.celluleEnEdition = null;
  }

  // --- Suppression ---

  supprimer(ressource: RessourceResponseDto): void {
    this.ressourceService.deleteById(ressource.id)
        .subscribe(() => this.reload());
  }

  // --- Helpers ---

  // Pour put
  isLigneEnEdition(ressource: RessourceResponseDto): boolean {
    if (!ressource.id) return false;
    return this.ligneEnEdition?.id === ressource.id;
  }

  // Pour patch
  isCelluleEnEdition(ressource: RessourceResponseDto, champ: string): boolean {
    if (!ressource.id) return false;
    return this.celluleEnEdition?.id === ressource.id &&
        this.celluleEnEdition?.champ === champ;
  }
}