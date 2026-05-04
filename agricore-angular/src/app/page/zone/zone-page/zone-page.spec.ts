import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ZonePage } from './zone-page';

describe('ZonePage', () => {
  let component: ZonePage;
  let fixture: ComponentFixture<ZonePage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ZonePage],
    }).compileComponents();

    fixture = TestBed.createComponent(ZonePage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
