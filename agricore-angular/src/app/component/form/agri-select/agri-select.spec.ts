import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgriSelect } from './agri-select';

describe('AgriSelect', () => {
  let component: AgriSelect;
  let fixture: ComponentFixture<AgriSelect>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AgriSelect],
    }).compileComponents();

    fixture = TestBed.createComponent(AgriSelect);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
