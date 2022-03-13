import { TestBed } from '@angular/core/testing';

import { PomTableService } from './pom-table.service';

describe('PomTableService', () => {
  let service: PomTableService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PomTableService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
