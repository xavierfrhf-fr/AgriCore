import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { EmployeRequest } from '../../../dto/utilisateur/employe/employe-request';
import { EmployeResponse } from '../../../dto/utilisateur/employe/employe-response';

@Injectable({
  providedIn: 'root',
})
export class EmployeService {
  constructor(private httpClient: HttpClient) {}

  public findAll(): Observable<EmployeResponse[]> {
    return this.httpClient.get<EmployeResponse[]>('employe');
  }

  public findById(id: number): Observable<EmployeResponse> {
    return this.httpClient.get<EmployeResponse>(`employe/${id}`);
  }

  public insert(req: EmployeRequest): Observable<EmployeResponse> {
    return this.httpClient.post<EmployeResponse>(`employe`, req);
  }

  public deleteById(id: number): Observable<void> {
    return this.httpClient.delete<void>(`employe/${id}`);
  }
}
