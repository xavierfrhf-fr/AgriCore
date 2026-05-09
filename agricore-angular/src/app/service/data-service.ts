import { Injectable } from '@angular/core';
import { MapSize } from '../model/zone/position/map-size';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ZoneDataDTO } from '../dto/zone/response/zone-data-dto';
import { AnimalData } from '../dto/animal/response/animal-data';
import { PlanteData } from '../dto/plante/PlanteData';

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

  public getAnimalData(): Observable<AnimalData[]>{
    return this.httpClient.get<AnimalData[]>("data/animal")
  }

  public getPlanteData(): Observable<PlanteData[]>{
    return this.httpClient.get<PlanteData[]>("data/plante")
  }
}
