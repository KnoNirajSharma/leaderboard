import { TestBed } from '@angular/core/testing';

import { EmployeeActivityService } from './employee-activity.service';
import { HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
// import {AuthorModel} from '../models/author.model';

describe('EmployeeActivityService', () => {
  let employeeActivityService: EmployeeActivityService;
  // let httpTestingController: HttpTestingController;

  beforeEach(() => TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      }
  ));

  beforeEach(() => {
    TestBed.configureTestingModule({});
    employeeActivityService = TestBed.get(EmployeeActivityService);
    // httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(employeeActivityService).toBeTruthy();
  });

  // it('should return list of employees activity', () => {
  //   employeeActivityService.getData().subscribe(values => {
  //     expect(values).toBeTruthy('No output returned');
  //
  //     const requestCheck = httpTestingController.expectOne(/assets/data/authorProfile.json);
  //   } );
  // });

  // it('should retrieve author data from the API via GET', () => {
  //   const dummyAuthorData: AuthorModel[] = [{
  //     authorName: 'mark';
  //     score: 100;
  //     rank: 5;
  //   }, {
  //     authorName: 'sam';
  //     score: 120;
  //     rank: 2;
  //   }];
  //   employeeActivityService.getData().subscribe(data =>{
  //     expect(data);
  //   });
  // });
});
