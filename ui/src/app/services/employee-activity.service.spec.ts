import { TestBed } from '@angular/core/testing';
import { EmployeeActivityService } from './employee-activity.service';
import { HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {AuthorModel} from '../models/author.model';


describe('EmployeeActivityService', () => {
  let employeeActivityService: EmployeeActivityService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [EmployeeActivityService]
      }
  ));

  beforeEach(() => {
    TestBed.configureTestingModule({});
    employeeActivityService = TestBed.get(EmployeeActivityService);
    httpTestingController = TestBed.get(HttpTestingController);
  });

  it('should be created', () => {
    expect(employeeActivityService).toBeTruthy();
  });

  it('should retrieve author data from the API via GET', () => {
  const dummyAuthorData: AuthorModel[] = [{
    authorName: 'mark',
    score: 100,
    rank: 5,
  }, {
    authorName: 'sam',
    score: 120,
    rank: 2,
  }];
  employeeActivityService.getData().subscribe(authorData => {
    expect(authorData).toEqual(dummyAuthorData);
  });
  const requestCheck = httpTestingController.expectOne('/assets/data/authorProfile.json');
  expect(requestCheck.request.method).toBe('GET');
  requestCheck.flush(dummyAuthorData);
});

  afterEach(() => {
  httpTestingController.verify();
});
});
