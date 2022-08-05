import { TestBed } from '@angular/core/testing';

import { PomTableStateService } from './pom-table-state.service';

describe('PomTableStateService', () => {
  let service: PomTableStateService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PomTableStateService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
