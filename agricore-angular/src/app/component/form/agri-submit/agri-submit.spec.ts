import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgriSubmit } from './agri-submit';

describe('AgriSubmit', () => {
  let component: AgriSubmit;
  let fixture: ComponentFixture<AgriSubmit>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AgriSubmit],
    }).compileComponents();

    fixture = TestBed.createComponent(AgriSubmit);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
