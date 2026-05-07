import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProduitsPage } from './produits-page';

describe('ProduitsPage', () => {
  let component: ProduitsPage;
  let fixture: ComponentFixture<ProduitsPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProduitsPage],
    }).compileComponents();

    fixture = TestBed.createComponent(ProduitsPage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
