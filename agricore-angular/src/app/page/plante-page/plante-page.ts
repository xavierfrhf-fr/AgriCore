import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable, startWith, Subject, switchMap } from 'rxjs';
import { PlanteService } from '../../service/plante/plante-service';
import { ZoneService } from '../../service/zone/zone-service';
import { PlanteResponse } from '../../dto/plante/plante-response';

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

  protected plante$!: Observable<PlanteResponse[]>;

  ngOnInit(): void {
    this.plante$ = this.refresh$.pipe(
      startWith(0),
      switchMap(() => this.planteService.findAll())
    )
  
  }

  private reload() {
    this.refresh$.next();
  }

  public arroser (id: number) {
    this.planteService.arroser(id).subscribe(
      ()=> this.reload()
    );

  }
}
