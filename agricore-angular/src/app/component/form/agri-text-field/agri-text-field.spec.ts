import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgriTextField } from './agri-text-field';

describe('AgriTextField', () => {
  let component: AgriTextField;
  let fixture: ComponentFixture<AgriTextField>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AgriTextField],
    }).compileComponents();

    fixture = TestBed.createComponent(AgriTextField);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
