import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransformationDisplay } from './transformation-display';

describe('TransformationDisplay', () => {
  let component: TransformationDisplay;
  let fixture: ComponentFixture<TransformationDisplay>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransformationDisplay],
    }).compileComponents();

    fixture = TestBed.createComponent(TransformationDisplay);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
