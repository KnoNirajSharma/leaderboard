import { TestBed } from '@angular/core/testing';

import { EmployeeActivityService } from './employee-activity.service';

describe('EmployeeActivityService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EmployeeActivityService = TestBed.get(EmployeeActivityService);
    expect(service).toBeTruthy();
  });
});
