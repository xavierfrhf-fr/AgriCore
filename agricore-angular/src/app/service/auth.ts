import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class Auth {

  private apiUrl = '/auth'

  constructor(private http: HttpClient) {}

  login(username: string, password: string) {
    return this.http.post<string>(`${this.apiUrl}`, {username,password})
  }




}
