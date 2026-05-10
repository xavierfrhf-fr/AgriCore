import {CommonModule} from '@angular/common';
import {AfterViewInit, Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {combineLatest, map, Observable, startWith, Subject, switchMap} from 'rxjs';

import {AgriModal} from '../../component/agri-modal/agri-modal';
import {AgriSelect} from '../../component/form/agri-select/agri-select';
import {AgriSubmit} from '../../component/form/agri-submit/agri-submit';
import {PrixRequestDto} from '../../dto/ressource/request/prix-request-dto';
import {RessourceRequestDto} from '../../dto/ressource/request/ressource-request-dto';
import {TransformationRequestDto} from '../../dto/ressource/request/transformation-request-dto';
import {PrixResponseDto} from '../../dto/ressource/response/prix-response-dto';
import {RessourceResponseDto} from '../../dto/ressource/response/ressource-response-dto';
import {RessourceDataDto} from '../../dto/ressource/ressource-data-dto';
import {TransformationDataDto} from '../../dto/ressource/transformation-data-dto';
import {UniteDataDto} from '../../dto/ressource/unite-data-dto';
import {TransformationService} from '../../service/ressource/transformation-service';
import {UniteService} from '../../service/ressource/unite-service';

import {NomRessourceService} from './../../service/ressource/nom-ressource-service';
import {RessourceService} from './../../service/ressource/ressource-service';
import {ZoneService} from '../../service/zone/zone-service';
import {DataService} from '../../service/data-service';

@Component({
  imports:
      [CommonModule, ReactiveFormsModule, AgriSelect, AgriSubmit, AgriModal],
  templateUrl: './ressource-page.html',
  styleUrl: './ressource-page.css',
})
export class RessourcePage implements OnInit, AfterViewInit {
  private ressourceService = inject(RessourceService);
  private nomRessourceService = inject(NomRessourceService);
  private uniteService = inject(UniteService);
  private transformationService = inject(TransformationService);
  private zoneService = inject(ZoneService);
  private dataService = inject(DataService);
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

  protected transformations$!: Observable<TransformationDataDto[]>;
  protected transformationsList: TransformationDataDto[] = [];
  protected ressourcesList: RessourceResponseDto[] = [];

  protected afficheRessourceForm = false;
  protected affichePrixForm = false;
  protected afficheTransformationForm = false;
  protected afficheTransformationList = false;
  protected creationDepuisTransformation = false;

  protected ressourceEnTransformation: RessourceResponseDto|null = null;
  protected transformationsDuForm: TransformationDataDto[] = [];
  protected transformationSelectionnee: TransformationDataDto|null = null;

  // États d'édition
  protected ligneEnEdition: RessourceResponseDto|null = null;
  protected celluleEnEdition: {id: string; champ: string;}|null = null;

  // Formulaire partagé création / édition inline
  protected ressourceForm!: FormGroup;
  protected nomCtrl!: FormControl;
  protected quantiteCtrl!: FormControl;
  protected stockMinCtrl!: FormControl;

  // Formulaire spécifique pour la création de prix
  protected prixCtrl!: FormGroup;
  protected prixParCtrl!: FormControl;
  protected quantiteLotCtrl!: FormControl;
  protected uniteCtrl!: FormControl;

  protected transformationForm!: FormGroup;
  protected produitTransfoCtrl!: FormControl;
  protected quantiteDesireCtrl!: FormControl;
  protected partielCtrl!: FormControl;
  protected bypassStockMinCtrl!: FormControl;

  protected colWidths: string[] = [];


  ngOnInit(): void {
    this.ressources$ = this.refresh$.pipe(
        startWith(null), switchMap(() => this.ressourceService.findAll()));

    this.ressources$.subscribe(list => {
      this.ressourcesList = list;
    });

    this.nomRessources$ = this.refresh$.pipe(
        startWith(null), switchMap(() => this.nomRessourceService.findAll()));

    // Filtrage côté client pour n'afficher que les ressources non encore
    // utilisées CombineLatest prends les deux fluxs ressources et nomRessources
    // et à chaque fois que l'un change, met à jour les deux
    const zonesExistantes$ = this.zoneService.findAll();
    const zoneDataAll$ = this.dataService.getZoneData();

    this.nomRessourcesFiltre$ =
        combineLatest([
          this.nomRessources$, this.ressources$, zonesExistantes$, zoneDataAll$
        ]).pipe(map(([nomDisponibles, ressourcesPresentes, zones, zoneData]) => {
          const nomsUtilises = new Set(ressourcesPresentes.map(
              ressourcePresente => ressourcePresente.nom));
          const nomZonesExistantes = new Set(zones.map(z => z.nomZone.trim().toLowerCase()));
          const nomAffichageDisponibles = new Set(
              zoneData
                .filter(zd => nomZonesExistantes.has(zd.nomZone.trim().toLowerCase()))
                .map(zd => zd.nomAffichage.trim().toLowerCase())
          );
          return nomDisponibles.filter(
              nomRessource => !nomsUtilises.has(nomRessource.nom) &&
              nomAffichageDisponibles.has(nomRessource.nomAffichageZone.trim().toLowerCase()));
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

    this.transformations$ = this.refresh$.pipe(
        startWith(null), switchMap(() => this.transformationService.getAll()));

    this.transformationService.getAll().subscribe(list => {
      this.transformationsList = list;
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
      prixLot: this.prixCtrl,
    });

    this.produitTransfoCtrl = this.formBuilder.control('', Validators.required);
    this.quantiteDesireCtrl =
        this.formBuilder.control(1, [Validators.required, Validators.min(1)]);
    this.partielCtrl = this.formBuilder.control(false);
    this.bypassStockMinCtrl = this.formBuilder.control(false);
    this.transformationForm = this.formBuilder.group({
      produit: this.produitTransfoCtrl,
      quantiteDesire: this.quantiteDesireCtrl,
      partial: this.partielCtrl,
      bypassStockMin: this.bypassStockMinCtrl,
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


  ouvrirTransformationList(): void {
    this.annulerModifier();
    this.annulerCreer();
    this.annulerEditionCellule();
    this.afficheTransformationList = true;
  }

  fermerTransformationList(): void {
    this.afficheTransformationList = false;
  }

  ouvrirTransformationForm(ressource: RessourceResponseDto): void {
    this.annulerModifier();
    this.annulerCreer();
    this.ressourceEnTransformation = ressource;
    this.transformationsDuForm = this.transformationsList.filter(
        t => t.ingredients.some(i => i.nomRessource === ressource.nom));
    this.selectionnerTransformation(this.transformationsDuForm[0] ?? null);
    this.afficheTransformationForm = true;
  }

  selectionnerTransformation(transfo: TransformationDataDto|null): void {
    this.transformationSelectionnee = transfo;
    this.produitTransfoCtrl.setValue(transfo?.produits[0]?.nomRessource ?? '');
    this.quantiteDesireCtrl.setValue(1);
  }

  estTransformationDisponible(transfo: TransformationDataDto): boolean {
    return transfo.zoneExist && transfo.maxTransfoPossible > 0;
  }

  aPossibleTransformation(ressource: RessourceResponseDto): boolean {
    return this.transformationsList.some(
        t => t.ingredients.some(i => i.nomRessource === ressource.nom));
  }

  getQuantiteMax(): number {
    if (!this.transformationSelectionnee) return 1;
    return this.transformationSelectionnee.produits[0]?.max ?? 1;
  }

  validerTransformationForm(): void {
    if (this.transformationForm.invalid || !this.transformationSelectionnee)
      return;
    const dto: TransformationRequestDto = {
      product: this.produitTransfoCtrl.value,
      desiredQuantity: this.quantiteDesireCtrl.value,
      partial: this.partielCtrl.value,
      bypassStockMin: this.bypassStockMinCtrl.value,
    };
    this.transformationService.perform(dto).subscribe(() => {
      this.annulerTransformationForm();
      this.reload();
    });
  }

  annulerTransformationForm(): void {
    this.afficheTransformationForm = false;
    this.ressourceEnTransformation = null;
    this.transformationSelectionnee = null;
  }

  // Prix d'un produit de transformation — édition inline dans le formulaire

  getPrixAffichageProduit(nomRessource: string): string {
    const r = this.ressourcesList.find(res => res.nom === nomRessource);
    if (!r?.prixLot) return '';
    return r.prixLot.prixPar > 0 ? r.prixLot.affPrix : '';
  }

  ouvrirPrixProduitTransfo(nomRessource: string): void {
    const ressource = this.ressourcesList.find(r => r.nom === nomRessource);

    this.prixCtrl.reset({
      prixPar: ressource?.prixLot?.prixPar ?? 0,
      quantiteLot: ressource?.prixLot?.quantiteLot ?? 1,
      unite: ressource?.prixLot?.unite ?? '',
    });

    // édition d'une ressource existante
    this.ligneEnEdition = ressource ?? null;

    // création d'une nouvelle ressource depuis transformation
    if (!ressource) {
      this.creationDepuisTransformation = true;

      this.ressourceForm.patchValue({
        nom: nomRessource,
        quantite: 0,
        stockMin: 0,
      });
    }

    this.affichePrixForm = true;
  }

  private reload(): void {
    this.refresh$.next();
    this.transformationService.getAll().subscribe(list => {
      this.transformationsList = list;
    });
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
    } else if (this.creationDepuisTransformation) {
      this.affichePrixForm = false;
      this.afficheRessourceForm = false;

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