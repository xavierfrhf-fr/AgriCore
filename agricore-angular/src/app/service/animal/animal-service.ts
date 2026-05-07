import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Animal } from '../../model/animal';
import { HttpClient } from '@angular/common/http';
import { AnimalRequest } from '../../dto/animal/request/animal-request';

@Injectable({
  providedIn: 'root',
})
export class AnimalService {
  protected animals$!: Observable<Animal[]>;

  constructor(private http: HttpClient) { }

  public findAll(): Observable<Animal[]> {
    return this.http.get<Animal[]>("/animal").pipe(
    map(animaux => animaux.map(a => ({
      ...a,
      dateVaccination: new Date(a.dateVaccination),
      dateNaissance: new Date(a.dateNaissance)
    })))
  );
  }

  public findById(id: number): Observable<Animal> {
    return this.http.get<Animal>(`/animal/${id}`);
  }

  public add(newAnimal: AnimalRequest): Observable<Animal> {
    return this.http.post<Animal>("/animal", newAnimal);
  }

   public update(updatedAnimal: AnimalRequest): Observable<Animal> {
    return this.http.put<Animal>(`/animal/${updatedAnimal.id}`,updatedAnimal);
  }

  public delete(id: number): Observable<Animal> {
    return this.http.delete<Animal>(`/animal/${id}`);
  }
}
