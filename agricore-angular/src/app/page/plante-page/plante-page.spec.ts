import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlantePage } from './plante-page';

describe('PlantePage', () => {
  let component: PlantePage;
  let fixture: ComponentFixture<PlantePage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlantePage],
    }).compileComponents();

    fixture = TestBed.createComponent(PlantePage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
