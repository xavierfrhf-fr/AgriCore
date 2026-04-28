import {CommonModule} from '@angular/common';
import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {RouterLink} from '@angular/router';
import {Observable, startWith, Subject, switchMap} from 'rxjs';

import {AgriModal} from '../../component/agri-modal/agri-modal';
import {AgriSelect} from '../../component/form/agri-select/agri-select';
import {AgriSubmit} from '../../component/form/agri-submit/agri-submit';
import {AgriTextField} from '../../component/form/agri-text-field/agri-text-field';
import {RessourceRequestDto} from '../../dto/ressource/request/ressource-request-dto';
import {RessourceResponseDto} from '../../dto/ressource/response/ressource-response-dto';
import {NomRessource} from '../../enumerator/ressource/nom-ressource';
import {PrixService} from '../../service/ressource/prix-service';
import {TransformationService} from '../../service/ressource/transformation-service';
import {UniteService} from '../../service/ressource/unite-service';

import {NomRessourceService} from './../../service/ressource/nom-ressource-service';
import {RessourceService} from './../../service/ressource/ressource-service';

@Component({
  imports: [
    CommonModule, RouterLink, AgriTextField, AgriSelect, AgriSubmit, AgriModal
  ],
  templateUrl: './ressource-page.html',
  styleUrl: './ressource-page.css',
})
export class RessourcePage implements OnInit {
  private ressourceService: RessourceService = inject(RessourceService);
  private nomRessourceService: NomRessourceService =
      inject(NomRessourceService);
  private uniteService: UniteService = inject(UniteService);
  private prixService: PrixService = inject(PrixService);
  private transformationService: TransformationService =
      inject(TransformationService);

  private formBuilder: FormBuilder = inject(FormBuilder);


  protected ressources$!:
      Observable<RessourceResponseDto[]>;  // D'autres a ajouter
  private refresh$: Subject<void> = new Subject<void>();
  protected nomRessources$!: Observable<NomRessource[]>;

  protected showForm: boolean = false;
  protected ressourceForm!: FormGroup;
  protected nomRessourceCtrl!: FormControl;

  protected editingRessource!: RessourceResponseDto|null;

  /*protected publicationErrorMessages = {
    required: '... est obligatoire',
    pattern: '... doit être au format ...'
  };*/ // Implementer ?

  ngOnInit(): void {
    this.ressources$ = this.refresh$.pipe(
        startWith(null), switchMap(() => this.ressourceService.findAll()));

    this.nomRessources$ = this.refresh$.pipe(
        startWith(null), switchMap(() => this.nomRessourceService.findAll()));

    this.nomRessourceCtrl = this.formBuilder.control(
        '', Validators.required);  // Implementer lui et les autres


    this.ressourceForm = this.formBuilder.group({
      nom: this.nomRessourceCtrl,  // Implementer lui et les autres

    });
  }

  public trackRessource(index: number, value: RessourceResponseDto) {
    return value.id;
  }

  private reload() {
    this.refresh$.next();
  }

  public creerOuModifier() {
    const ressource: RessourceRequestDto = {
      ...this.ressourceForm.getRawValue(),
      id: this.editingRessource?.id
    };

    this.ressourceService.save(ressource).subscribe(() => {
      this.showForm = false;
      this.editingRessource = null;
      this.ressourceForm.reset();

      this.reload();
    });
  }

  public editer(ressource: RessourceResponseDto) {
    this.editingRessource = ressource;
    this.showForm = true;

    this.nomRessourceCtrl.setValue(
        ressource.nom);  // Implementer lui et les autres
  }

  public annulerEditer() {
    this.showForm = false;
    this.editingRessource = null;
    this.ressourceForm.reset();
  }

  public supprimer(ressource: RessourceResponseDto) {
    this.ressourceService.deleteById(ressource.id)
        .subscribe(() => this.reload());
  }
}
