import {HttpClient} from '@angular/common/http';
import {inject, Injectable} from '@angular/core';
import {Observable, Subject} from 'rxjs';

import {RessourceRequestDto} from '../../dto/ressource/request/ressource-request-dto';
import {RessourceResponseDto} from '../../dto/ressource/response/ressource-response-dto';



@Injectable({
  providedIn: 'root',
})
export class RessourceService {
  private http: HttpClient = inject(HttpClient);

  private apiUrl: string = '/ressource';
  private refresh$: Subject<void> = new Subject<void>();

  public findAll(): Observable<RessourceResponseDto[]> {
    return this.http.get<RessourceResponseDto[]>(this.apiUrl);
  }

  public findById(id: number): Observable<RessourceResponseDto> {
    return this.http.get<RessourceResponseDto>(`${this.apiUrl}/${id}`);
  }

  public save(ressource: RessourceRequestDto): Observable<RessourceRequestDto> {
    if (!ressource.id) {
      return this.http.post<RessourceRequestDto>(this.apiUrl, ressource);
    }

    return this.http.put<RessourceRequestDto>(
        `${this.apiUrl}/${ressource.id}`, ressource);
  }

  public deleteById(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
