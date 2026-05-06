import { Component, inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { EmployeService } from '../../../service/utilisateur/employe/employe-service';
import { Observable, startWith, Subject, switchMap } from 'rxjs';
import { Employe } from '../../../model/utilisateur/employe';

@Component({
  selector: 'app-employe-compte',
  imports: [],
  templateUrl: './employe-compte.html',
  styleUrl: './employe-compte.css',
})
export class EmployeCompte {
  private employeService: EmployeService = inject(EmployeService);
  private formBuilder: FormBuilder = inject(FormBuilder);

  protected employes$!: Observable<Employe[]>;
  private refresh$: Subject<void> = new Subject<void>();

  protected formEmploye!: FormGroup;
  protected formLibelleCtrl!: FormControl;

  /*ngOnInit(): void {
    this.employes$ = this.refresh$.pipe(
      startWith(0),
      switchMap(() => this.employeService.findAll()),
    );

    this.formLibelleCtrl = this.formBuilder; //a modifier

    this.formEmploye = this.formBuilder.group({
      libelle: this.formLibelleCtrl,
    });
  }
  private reload() {
    this.refresh$.next();
  }

  public insertEmploye() {
    const employe: Employe = {
      id: 0,
      libelle: this.formLibelleCtrl.value, //a modifier
    };

    this.employeService.insert(employe).subscribe(() => this.reload());
  }

  public delete(employe: Employe) {
    this.employeService.deleteById(employe.id).subscribe(() => this.reload());
  }*/
}
