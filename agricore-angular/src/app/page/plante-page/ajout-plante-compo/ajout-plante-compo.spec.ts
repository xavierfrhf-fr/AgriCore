import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AjoutPlanteCompo } from './ajout-plante-compo';

describe('AjoutPlanteCompo', () => {
  let component: AjoutPlanteCompo;
  let fixture: ComponentFixture<AjoutPlanteCompo>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AjoutPlanteCompo],
    }).compileComponents();

    fixture = TestBed.createComponent(AjoutPlanteCompo);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
