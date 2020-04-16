import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MainPage } from './main.page';
import {EmployeeActivityService} from '../../services/employee-activity.service';
import {AuthorModel} from '../../models/author.model';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';

describe('MainPage', () => {
  let component: MainPage;
  let fixture: ComponentFixture<MainPage>;
  let mockEmployeeService: EmployeeActivityService;
  // const mockAuthorData: AuthorModel[];

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
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });



});
