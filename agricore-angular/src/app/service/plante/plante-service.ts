import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PlanteRequest } from '../../dto/plante/plante-request';
import { PlanteResponse } from '../../dto/plante/plante-response';

@Injectable({
  providedIn: 'root',
})
export class PlanteService {
constructor(private http: HttpClient){}


public findAll():Observable<PlanteResponse[]> {
  return this.http.get<PlanteResponse[]>("/plante");
}

public add(newPlante: PlanteRequest): Observable<PlanteResponse> {
    return this.http.post<PlanteResponse>("/plante", newPlante);
  }

   public update(updatedPlante: PlanteRequest, id: number): Observable<PlanteResponse> {
    return this.http.put<PlanteResponse>(`/plante/${id}`,updatedPlante);
  }

  public delete(id: number): Observable<void> {
    return this.http.delete<void>(`/plante/${id}`);
  }
}
