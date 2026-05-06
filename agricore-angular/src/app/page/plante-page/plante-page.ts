import { Component, inject, OnInit } from '@angular/core';
import { Observable, startWith, Subject, switchMap } from 'rxjs';
import { Plante } from '../../model/plante';
import { PlanteService } from '../../service/plante/plante-service';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
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
  protected formEspeceCtrl!: FormControl;
  protected formZoneIdCtrl!: FormControl;

  ngOnInit(): void {
    this.plante$ = this.refresh$.pipe(
      startWith(0),
      switchMap(() => this.planteService.findAll())
    )
  }

  private reload() {
    this.refresh$.next();
  }
}
