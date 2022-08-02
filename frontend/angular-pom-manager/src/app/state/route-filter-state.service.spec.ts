import { TestBed } from '@angular/core/testing';

import { RouteFilterStateService } from './route-filter-state.service';

describe('RouteFilterStateService', () => {
  let service: RouteFilterStateService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RouteFilterStateService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
