import { TestBed } from '@angular/core/testing';

import { GitManagementService } from './git-management.service';

describe('GitManagementService', () => {
  let service: GitManagementService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GitManagementService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
