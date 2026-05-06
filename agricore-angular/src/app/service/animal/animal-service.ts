import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Animal } from '../../model/animal';
import { HttpClient } from '@angular/common/http';

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

  public add(newAnimal: Animal): Observable<Animal> {
    return this.http.post<Animal>("/animal", newAnimal);
  }

   public update(updatedAnimal: Animal): Observable<Animal> {
    return this.http.put<Animal>(`/matiere/${updatedAnimal.id}`,updatedAnimal);
  }

  public delete(id: number): Observable<Animal> {
    return this.http.delete<Animal>(`/animal/${id}`);
  }
}
