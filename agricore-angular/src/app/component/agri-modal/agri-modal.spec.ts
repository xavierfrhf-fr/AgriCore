import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgriModal } from './agri-modal';

describe('AgriModal', () => {
  let component: AgriModal;
  let fixture: ComponentFixture<AgriModal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AgriModal],
    }).compileComponents();

    fixture = TestBed.createComponent(AgriModal);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
