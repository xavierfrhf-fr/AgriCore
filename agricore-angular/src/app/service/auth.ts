import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';

import {AuthRequestDto} from '../dto/auth/auth-request-dto';
import {AuthResponseDto} from '../dto/auth/auth-response-dto';

@Injectable({
  providedIn: 'root',
})
export class Auth {
  private apiUrl = '/auth';
  private _token: string = localStorage.getItem('token') ?? '';

  constructor(private http: HttpClient) {}

  login(username: string, password: string) {
    return this.http.post<string>(`${this.apiUrl}`, {username, password})
  }

  public get token(): string {
    return this._token;
  }

  public auth(authRequest: AuthRequestDto): Observable<AuthResponseDto> {
    return this.http.post<AuthResponseDto>('/auth', authRequest);
  }

  public set token(value: string) {
    this._token = value;
    localStorage.setItem('token', value);
  }

  public isLogged() {
    return !!this._token;
  }

  public resetAuth() {
    this._token = '';
    localStorage.removeItem('token');
  }
}
