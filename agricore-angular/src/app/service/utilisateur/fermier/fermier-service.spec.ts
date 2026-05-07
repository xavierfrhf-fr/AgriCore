import { TestBed } from '@angular/core/testing';

import { Fermier } from './fermier-service';

describe('Fermier', () => {
  let service: Fermier;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Fermier);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
