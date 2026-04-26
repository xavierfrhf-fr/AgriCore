import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RessourcePage } from './ressource-page';

describe('RessourcePage', () => {
  let component: RessourcePage;
  let fixture: ComponentFixture<RessourcePage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RessourcePage],
    }).compileComponents();

    fixture = TestBed.createComponent(RessourcePage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
