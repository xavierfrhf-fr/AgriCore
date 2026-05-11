import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnimalProductionDelay } from './animal-production-delay';

describe('AnimalProductionDelay', () => {
  let component: AnimalProductionDelay;
  let fixture: ComponentFixture<AnimalProductionDelay>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnimalProductionDelay],
    }).compileComponents();

    fixture = TestBed.createComponent(AnimalProductionDelay);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
