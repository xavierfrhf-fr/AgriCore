import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';

import {TransformationRequestDto} from '../../dto/ressource/request/transformation-request-dto';
import {TransformationDataDto} from '../../dto/ressource/transformation-data-dto';
import {TransformationResponseDto} from '../../dto/ressource/response/transformation-response-dto';

@Injectable({
  providedIn: 'root',
})
export class TransformationService {
  constructor(private httpClient: HttpClient) {}

  public getAll(): Observable<TransformationDataDto[]> {
    return this.httpClient.get<TransformationDataDto[]>('/api/data/transfo');
  }

  public perform(dto: TransformationRequestDto): Observable<TransformationResponseDto> {
    return this.httpClient.post<TransformationResponseDto>('/api/ressource/transformation', dto);
  }
}