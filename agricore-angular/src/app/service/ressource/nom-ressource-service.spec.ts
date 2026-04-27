import { TestBed } from '@angular/core/testing';

import { NomRessourceService } from './nom-ressource-service';

describe('NomRessourceService', () => {
  let service: NomRessourceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NomRessourceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
