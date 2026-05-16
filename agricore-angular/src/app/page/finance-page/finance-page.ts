import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { CompteService } from '../../service/finance/compte-service';
import { Header, SloganItem } from '../../component/header/header';
import { RouterLink } from '@angular/router';
import { CompteResponse } from '../../dto/finance/compte-response';

@Component({
  selector: 'app-finance-page',
  imports: [Header, RouterLink, ReactiveFormsModule, CommonModule],
  templateUrl: './finance-page.html',
  styleUrl: './finance-page.css',
})
export class FinancePage implements OnInit, OnDestroy {
  private compteService = inject(CompteService);
  private fb = inject(FormBuilder);

  protected comptes: CompteResponse[] = [];
  protected message = '';
  protected loading = false;
  protected sloganItems: SloganItem[] = [
    { iconSrc: 'assets/image/argent.png', texte: 'Portefeuille & transactions' },
  ];

  protected transferForm = this.fb.group({
    sourceId: [0, Validators.required],
    destinationId: [0, Validators.required],
    montant: [0, [Validators.required, Validators.min(1)]],
  });

  ngOnInit(): void {
    document.body.style.backgroundColor = '#ebdbc8';
    this.loadComptes();
  }

  ngOnDestroy(): void {
    document.body.removeAttribute('style');
  }

  protected loadComptes(): void {
    this.loading = true;
    this.compteService.getAll().subscribe({
      next: (comptes) => {
        this.comptes = comptes;
        this.loading = false;
      },
      error: () => {
        this.message = 'Impossible de charger les comptes. Vérifiez le backend finance.';
        this.loading = false;
      },
    });
  }

  protected transfer(): void {
    if (this.transferForm.invalid) {
      this.message = 'Remplissez correctement tous les champs du virement.';
      return;
    }

    this.message = '';
    const dto = this.transferForm.getRawValue();
    this.compteService.virement(dto).subscribe({
      next: () => {
        this.message = 'Virement effectué avec succès.';
        this.transferForm.reset({ montant: 0 });
        this.loadComptes();
      },
      error: () => {
        this.message = 'Erreur lors du virement, vérifiez les identifiants du compte ou le montant.';
      },
    });
  }
}
