import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ZoneDTO } from '../../dto/zone/response/zone-dto';

@Injectable({
  providedIn: 'root',
})
export class ZoneService {
  constructor(private httpClient:HttpClient) {}

  public findAll():Observable<ZoneDTO[]>{
    return this.httpClient.get<ZoneDTO[]>("zone");
  }

  public findById(id:number):Observable<ZoneDTO>{
    return this.httpClient.get<ZoneDTO>(`zone/${id}`);
  }

}
