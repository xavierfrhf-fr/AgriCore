import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';

import { AuthResponse } from '../dto/auth-response';

@Injectable({
  providedIn: 'root',
})
export class Auth {

  private apiUrl = '/auth'

  constructor(private http: HttpClient) {}


  login(username: string, password: string) {
    return this.http.post<AuthResponse>(`${this.apiUrl}`, {username,password})
  }
  // return this.http.post<string>(`${this.apiUrl}`, {username,password})

  register(login: string , password: string, nom: string, prenom: string, email:string) {
    return this.http.post<string>(`${this.apiUrl}/register`, {login,password,nom,prenom,email})
  }

  // Méthodes utilitaires pour gérer l'authentification
  saveAuthData(authResponse: AuthResponse): void {
    localStorage.setItem('token', authResponse.token);
    //localStorage.setItem('role', authResponse.role);
    //localStorage.setItem('username', authResponse.username);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getRole(): string | null {
    const token = localStorage.getItem('token');

    if (!token) {
      return null;
    }

    const decoded: any = jwtDecode(token);
    //console.log(decoded.role?.[0] ?? null);

    return decoded.role;

    //return localStorage.getItem('role');
  }

  getUsername(): string | null {

    const token = localStorage.getItem('token');

    if (!token) {
      return null;
    }

    const decoded: any = jwtDecode(token);
    return decoded.sub;
    //return localStorage.getItem('username');
  }

  isLoggedIn(): boolean {
    return this.getToken() !== null;
  }

  logout(): void {
    localStorage.removeItem('token');
    //localStorage.removeItem('role');
    //localStorage.removeItem('username');
  }

  // Vérifications de rôle
  isFermier(): boolean {
    return this.getRole() === 'ROLE_FERMIER';
  }

  isEmploye(): boolean {
    return this.getRole() === 'ROLE_EMPLOYE';
  }

  isClient(): boolean {
    return this.getRole() === 'ROLE_CLIENT';
  }

}
