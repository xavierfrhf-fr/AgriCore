import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';

import {TransformationDataDto} from '../../dto/ressource/transformation-data-dto';

@Injectable({
  providedIn: 'root',
})
export class TransformationService {
  constructor(private httpClient: HttpClient) {}

  public getAll(): Observable<TransformationDataDto[]> {
    return this.httpClient.get<TransformationDataDto[]>('/api/data/transfo');
  }
}