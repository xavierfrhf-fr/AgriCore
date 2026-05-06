import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ClientRequest } from '../../../dto/utilisateur/client/client-request';
import { ClientResponse } from '../../../dto/utilisateur/client/client-response';

@Injectable({
  providedIn: 'root',
})
export class ClientService {
  constructor(private httpClient: HttpClient) {}

  public findAll(): Observable<ClientResponse[]> {
    return this.httpClient.get<ClientResponse[]>('client');
  }

  public findById(id: number): Observable<ClientResponse> {
    return this.httpClient.get<ClientResponse>(`client/${id}`);
  }

  public insert(req: ClientRequest): Observable<ClientResponse> {
    return this.httpClient.post<ClientResponse>(`client`, req);
  }

  public deleteById(id: number): Observable<void> {
    return this.httpClient.delete<void>(`client/${id}`);
  }
}
