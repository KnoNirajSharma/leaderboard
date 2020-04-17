import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MainPage } from './main.page';
import {EmployeeActivityService} from '../../services/employee-activity.service';
import {AuthorModel} from '../../models/author.model';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {of} from 'rxjs';

describe('MainPage', () => {
  let component: MainPage;
  let fixture: ComponentFixture<MainPage>;
  let mockEmployeeService: EmployeeActivityService;
  const dummyAuthorData: AuthorModel[] = [
    {
      authorName: 'mark',
      score: 100,
      rank: 5,
    }, {
      authorName: 'sam',
      score: 120,
      rank: 2,
    }
  ];

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MainPage ],
    }).compileComponents();
  }));

  beforeEach(() => ({
    imports: [HttpClientTestingModule, RouterTestingModule]
  }
  ));

  beforeEach(() => {
    fixture = TestBed.createComponent(MainPage);
    component = fixture.componentInstance;
    mockEmployeeService = TestBed.get(EmployeeActivityService);
    // fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should return the authorData as per api call', () => {
    spyOn(mockEmployeeService, 'getData').and.returnValue(of(dummyAuthorData));
    component.ngOnInit();
    expect(component.employeeData).toEqual(dummyAuthorData);
  });
});
