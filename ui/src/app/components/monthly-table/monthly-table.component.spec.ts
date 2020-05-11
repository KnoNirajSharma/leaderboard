import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { MonthlyTableComponent } from './monthly-table.component';
import {EmployeeActivityService} from '../../services/employee-activity.service';
import {MonthlyReputationModel} from '../../models/monthlyReputation.model';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {FormsModule} from '@angular/forms';
import {of} from 'rxjs';

describe('MonthlyTableComponent', () => {
  let component: MonthlyTableComponent;
  let fixture: ComponentFixture<MonthlyTableComponent>;
  let mockEmployeeService: EmployeeActivityService;
  const dummyAuthorData: MonthlyReputationModel[] = [
    {
      authorName: 'mark',
      monthlyScore: 100,
      monthlyRank: 5,
    }, {
      authorName: 'sam',
      monthlyScore: 100,
      monthlyRank: 5,
    }
  ];

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MonthlyTableComponent ],
      imports: [HttpClientTestingModule, IonicModule.forRoot(), RouterTestingModule, FormsModule]
    }).compileComponents();

    fixture = TestBed.createComponent(MonthlyTableComponent);
    component = fixture.componentInstance;
    mockEmployeeService = TestBed.get(EmployeeActivityService);
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('should return the authorData as per api call', () => {
    spyOn(mockEmployeeService, 'getMonthlyData').and.returnValue(of(dummyAuthorData));
    component.ngOnInit();
    expect(component.monthlyEmployeeData).toEqual(dummyAuthorData);
  });
});
