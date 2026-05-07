import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, FormControl } from '@angular/forms';
import { Observable, Subject, startWith, switchMap } from 'rxjs';
import { Client } from '../../../model/utilisateur/client';
import { ClientService } from '../../../service/utilisateur/client/client-service';

@Component({
  selector: 'app-client-compte',
  imports: [],
  templateUrl: './client-compte.html',
  styleUrl: './client-compte.css',
})
export class ClientCompte {
  private clientService: ClientService = inject(ClientService);
  private formBuilder: FormBuilder = inject(FormBuilder);

  protected employes$!: Observable<Client[]>;
  private refresh$: Subject<void> = new Subject<void>();

  protected formClient!: FormGroup;
  protected formLibelleCtrl!: FormControl;

  /*ngOnInit(): void {
    this.clients$ = this.refresh$.pipe(
      startWith(0),
      switchMap(() => this.clientService.findAll()),
    );

    this.formLibelleCtrl = this.formBuilder; //a modifier

    this.formClient = this.formBuilder.group({
      libelle: this.formLibelleCtrl,
    });
  }
  private reload() {
    this.refresh$.next();
  }

  public insertClient() {
    const client: Client = {
      id: 0,
      libelle: this.formLibelleCtrl.value, //a modifier
    };

    this.clientService.insert(client).subscribe(() => this.reload());
  }

  public delete(client: Client) {
    this.clientService.deleteById(client.id).subscribe(() => this.reload());
  }*/
}
