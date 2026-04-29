import { Injectable } from '@angular/core';
import { MapSize } from '../model/zone/position/map-size';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class DataService {
  constructor(private httpClient:HttpClient) {
  }
  public getMapSize():Observable<MapSize>{
    return this.httpClient.get<MapSize>("data/zone/mapSize");
  }
}
