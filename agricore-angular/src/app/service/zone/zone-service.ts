import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ZoneDTO } from '../../dto/zone/response/zone-dto';
import { ZoneRequest } from '../../dto/zone/request/zone-request';

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

  public insert(req:ZoneRequest):Observable<ZoneDTO>{
    return this.httpClient.post<ZoneDTO>(`zone`,req);
  }

}
