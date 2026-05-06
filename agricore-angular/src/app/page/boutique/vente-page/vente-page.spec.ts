import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VentePage } from './vente-page';

describe('VentePage', () => {
  let component: VentePage;
  let fixture: ComponentFixture<VentePage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VentePage],
    }).compileComponents();

    fixture = TestBed.createComponent(VentePage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
