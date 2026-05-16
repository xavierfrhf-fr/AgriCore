import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CompteResponse } from '../../dto/finance/compte-response';
import { CompteRequestCreate } from '../../dto/finance/compte-request-create';
import { CompteRequest } from '../../dto/finance/compte-request';
import { TransfertRequest } from '../../dto/finance/transfert-request';

@Injectable({
  providedIn: 'root',
})
export class CompteService {
  private apiUrl = '/api/comptes';

  constructor(private http: HttpClient) {}

  public getAll(): Observable<CompteResponse[]> {
    return this.http.get<CompteResponse[]>(this.apiUrl);
  }

  public getByUserId(userId: number): Observable<CompteResponse> {
    return this.http.get<CompteResponse>(`${this.apiUrl}/user/${userId}`);
  }

  public getById(id: number): Observable<CompteResponse> {
    return this.http.get<CompteResponse>(`${this.apiUrl}/${id}`);
  }

  public create(req: CompteRequestCreate): Observable<CompteResponse> {
    return this.http.post<CompteResponse>(this.apiUrl, req);
  }

  public update(id: number, req: CompteRequest): Observable<CompteResponse> {
    return this.http.put<CompteResponse>(`${this.apiUrl}/${id}`, req);
  }

  public delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  public virement(dto: TransfertRequest): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/virement`, dto);
  }
}
