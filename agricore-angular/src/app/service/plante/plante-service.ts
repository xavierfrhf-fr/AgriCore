import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Plante } from '../../model/plante';

@Injectable({
  providedIn: 'root',
})
export class PlanteService {
constructor(private http: HttpClient){}
public findAll():Observable<Plante[]> {
  return this.http.get<Plante[]>("/plante");
}

}
