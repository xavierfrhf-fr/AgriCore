import {HttpClient} from '@angular/common/http';
import {inject, Injectable} from '@angular/core';
import {Observable} from 'rxjs';

import {UniteDataDto} from '../../dto/ressource/unite-data-dto';

@Injectable({
  providedIn: 'root',
})
export class UniteService {
  private http: HttpClient = inject(HttpClient);

  private apiUrl: string = '/data/unite';

  public findAll(): Observable<UniteDataDto[]> {
    return this.http.get<UniteDataDto[]>(this.apiUrl);
  }
}
