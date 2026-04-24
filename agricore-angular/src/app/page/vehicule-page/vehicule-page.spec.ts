import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VehiculePage } from './vehicule-page';

describe('VehiculePage', () => {
  let component: VehiculePage;
  let fixture: ComponentFixture<VehiculePage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VehiculePage],
    }).compileComponents();

    fixture = TestBed.createComponent(VehiculePage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
