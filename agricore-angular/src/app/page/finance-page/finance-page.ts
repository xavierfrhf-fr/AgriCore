import { CommonModule } from '@angular/common';
import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Observable, startWith, Subject, switchMap } from 'rxjs';
import { Header, SloganItem } from '../../component/header/header';
import { CompteResponse } from '../../dto/finance/compte-response';
import { CompteService } from '../../service/finance/compte-service';

@Component({
  selector: 'app-finance-page',
  imports: [Header, RouterLink, ReactiveFormsModule, CommonModule],
  templateUrl: './finance-page.html',
  styleUrl: './finance-page.css',
})
export class FinancePage implements OnInit, OnDestroy {
    
  private compteService = inject(CompteService);
  private fb = inject(FormBuilder);

  //permet de refresh les données quand demandé 
    private refresh$ = new Subject<void>();
  
  protected comptes$!: Observable<CompteResponse[]>;
  //protected comptes: CompteResponse[] = [];
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
    
    this.comptes$ = this.refresh$.pipe(
          startWith(null),
          switchMap(() => this.compteService.getAll()) //appel api vers le findAll comptes
        );
  }

  ngOnDestroy(): void {
    document.body.removeAttribute('style');
  }

  private reload(): void {
    this.refresh$.next();
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
        this.reload();        
      },
      error: () => {
        this.message = 'Erreur lors du virement, vérifiez les identifiants du compte ou le montant.';
      },
    });
  }
}
