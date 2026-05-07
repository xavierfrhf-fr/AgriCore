import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CommandePanierPage } from './commande-panier-page';

describe('CommandePanierPage', () => {
  let component: CommandePanierPage;
  let fixture: ComponentFixture<CommandePanierPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CommandePanierPage],
    }).compileComponents();

    fixture = TestBed.createComponent(CommandePanierPage);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
