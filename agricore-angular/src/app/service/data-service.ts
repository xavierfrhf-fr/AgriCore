import { Injectable } from '@angular/core';
import { MapSize } from '../model/zone/position/map-size';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ZoneDataDTO } from '../dto/zone/response/zone-data-dto';

@Injectable({
  providedIn: 'root',
})
export class DataService {
  constructor(private httpClient:HttpClient) {
  }
  public getMapSize():Observable<MapSize>{
    return this.httpClient.get<MapSize>("data/zone/mapSize");
  }

  public getZoneData():Observable<ZoneDataDTO[]>{
    return this.httpClient.get<ZoneDataDTO[]>("data/zone");
  }
}
