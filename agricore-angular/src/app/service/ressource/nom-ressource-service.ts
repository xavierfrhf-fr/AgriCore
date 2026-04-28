import {HttpClient} from '@angular/common/http';
import {inject, Injectable} from '@angular/core';
import {Observable, Subject} from 'rxjs';

import {RessourceDataDto} from '../../dto/ressource/ressource-data-dto';

@Injectable({
  providedIn: 'root',
})
export class NomRessourceService {
  private http: HttpClient = inject(HttpClient);

  private apiUrl: string = '/data/ressource';
  private refresh$: Subject<void> = new Subject<void>();

  public findAll(): Observable<RessourceDataDto[]> {
    return this.http.get<RessourceDataDto[]>(this.apiUrl);
  }
}
