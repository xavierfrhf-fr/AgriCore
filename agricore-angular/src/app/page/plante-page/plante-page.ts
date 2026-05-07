import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable, startWith, Subject, switchMap } from 'rxjs';
import { PlanteService } from '../../service/plante/plante-service';
import { ZoneService } from '../../service/zone/zone-service';

@Component({
  selector: 'app-plante-page',
  imports: [],
  templateUrl: './plante-page.html',
  styleUrl: './plante-page.css',
})
export class PlantePage implements OnInit {

  private planteService: PlanteService = inject(PlanteService);
  private zoneService = inject(ZoneService);
  private formBuilder = inject(FormBuilder);

  private refresh$: Subject<void> = new Subject<void>();

  protected plante$!: Observable<Plante[]>;

  protected formPlante!: FormGroup;
  protected datePlantationCtrl!: FormControl;
  protected dateRecolteCtrl!: FormControl;
  protected especeCtrl!: FormControl;
  protected zoneCtrl!: FormControl;

  ngOnInit(): void {
    this.plante$ = this.refresh$.pipe(
      startWith(0),
      switchMap(() => this.planteService.findAll())
    )
  this.datePlantationCtrl = this.formBuilder.control(new Date(), Validators.required);
    this.dateRecolteCtrl = this.formBuilder.control(new Date(), Validators.required);
    this.especeCtrl = this.formBuilder.control('', Validators.required);
    this.zoneCtrl = this.formBuilder.control(0, Validators.required);


  this.formPlante = this.formBuilder.group({
      datePlantation: this.datePlantationCtrl,
      dateRecolte: this.dateRecolteCtrl,
      espece: this.especeCtrl,
      zoneId: this.zoneCtrl
    });
  
  }

  private reload() {
    this.refresh$.next();
  }
}
