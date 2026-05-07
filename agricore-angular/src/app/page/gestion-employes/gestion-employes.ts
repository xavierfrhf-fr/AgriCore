import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { EmployeService } from '../../service/utilisateur/employe/employe-service';
import { EmployeRequest } from '../../dto/utilisateur/employe/employe-request';
import { EmployeResponse } from '../../dto/utilisateur/employe/employe-response';
import { Observable, startWith, Subject, switchMap } from 'rxjs';

@Component({
  selector: 'app-gestion-employes',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './gestion-employes.html',
  styleUrl: './gestion-employes.css',
})
export class GestionEmployes implements OnInit {
  private formBuilder = inject(FormBuilder);
  private employeService = inject(EmployeService);

  employeForm!: any;
  employe: EmployeResponse[] = [];
  employes$!: Observable<EmployeResponse[]>;
  showForm = false;
  //permet de refresh les données quand demandé 
  private refresh$ = new Subject<void>();

  ngOnInit(): void {

    this.employes$ = this.refresh$.pipe(
          startWith(null),
          switchMap(() => this.employeService.findAll()) //appel api vers le findAll employe
        );
    this.employeForm = this.formBuilder.group({
      nom: ['', Validators.required],
      prenom: ['', Validators.required],
      login: ['', Validators.required],
      password: ['', Validators.required]
    });

  }

  toggleForm(): void {
    this.showForm = !this.showForm;
    if (!this.showForm) {
      this.employeForm.reset();
    }
  }

  onSubmit(): void {
    if (this.employeForm.invalid) {
      console.error('Formulaire invalide');
      return;
    }

    const employeRequest: EmployeRequest = this.employeForm.getRawValue();

    this.employeService.insert(employeRequest).subscribe(() => {
    
        console.log('Employé créé avec succès');
        this.employeForm.reset();
        this.reload();
      
    });
  }

  deleteEmploye(id: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer cet employé ?')) {
      this.employeService.deleteById(id).subscribe({
        next: () => {
          //console.log('Employé supprimé avec succès');
          this.reload();
        },
        error: (error) => {
          console.error('Erreur lors de la suppression de l\'employé:', error);
        }
      });
    }
  }

  //déclenche un refresh des véhicules
  private reload(): void {
    this.refresh$.next();
  }
}
