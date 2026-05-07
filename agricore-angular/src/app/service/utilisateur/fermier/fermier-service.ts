import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { FermierRequest } from '../../../dto/utilisateur/fermier/fermier-request';
import { FermierResponse } from '../../../dto/utilisateur/fermier/fermier-response';

@Injectable({
  providedIn: 'root',
})
export class FermierService {
  constructor(private httpClient: HttpClient) {}

  public findAll(): Observable<FermierResponse[]> {
    return this.httpClient.get<FermierResponse[]>('fermier');
  }

  public findById(id: number): Observable<FermierResponse> {
    return this.httpClient.get<FermierResponse>(`fermier/${id}`);
  }

  public insert(req: FermierRequest): Observable<FermierResponse> {
    return this.httpClient.post<FermierResponse>(`fermier`, req);
  }
}
