import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InfoPlante } from './info-plante';

describe('InfoPlante', () => {
  let component: InfoPlante;
  let fixture: ComponentFixture<InfoPlante>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InfoPlante],
    }).compileComponents();

    fixture = TestBed.createComponent(InfoPlante);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
