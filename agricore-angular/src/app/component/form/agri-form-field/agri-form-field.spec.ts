import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgriFormField } from './agri-form-field';

describe('AgriFormField', () => {
  let component: AgriFormField;
  let fixture: ComponentFixture<AgriFormField>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AgriFormField],
    }).compileComponents();

    fixture = TestBed.createComponent(AgriFormField);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
