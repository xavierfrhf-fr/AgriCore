import {HttpClient} from '@angular/common/http';
import {inject, Injectable} from '@angular/core';
import {Observable} from 'rxjs';

import {VehiculeRequestDTO} from '../dto/vehicule/request/vehicule-request-dto';
import {VehiculeResponseDTO} from '../dto/vehicule/response/vehicule-response-dto';
import {TypeVehiculeDTO} from '../dto/type-vehicule';

@Injectable({
  providedIn: 'root',
})
export class VehiculeService {
  private http: HttpClient = inject(HttpClient);

  private apiUrl: string = '/api/vehicule';

  public findAll(): Observable<VehiculeResponseDTO[]> {
    return this.http.get<VehiculeResponseDTO[]>(this.apiUrl);
  }

  public findById(id: number): Observable<VehiculeResponseDTO> {
    return this.http.get<VehiculeResponseDTO>(`${this.apiUrl}/${id}`);
  }

  public add(vehicule: VehiculeRequestDTO): Observable<VehiculeResponseDTO> {
    return this.http.post<VehiculeResponseDTO>(this.apiUrl, vehicule);
  }

  public update(id: number, vehicule: VehiculeRequestDTO): Observable<VehiculeResponseDTO> {
    return this.http.put<VehiculeResponseDTO>(`${this.apiUrl}/${id}`, vehicule);
  }

  public deleteById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  public getTypes(): Observable<TypeVehiculeDTO[]> {
    return this.http.get<TypeVehiculeDTO[]>(`${this.apiUrl}/types`)
  }
}
