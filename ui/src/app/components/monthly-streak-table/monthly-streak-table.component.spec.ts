import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { MonthlyStreakTableComponent } from './monthly-streak-table.component';
import {EmployeeActivityService} from '../../services/employee-activity.service';
import {AuthorModel} from '../../models/author.model';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {FormsModule} from '@angular/forms';
import {of} from 'rxjs';
import {StreakReputationModel} from '../../models/streakReputation.model';

describe('MonthlyStreakTableComponent', () => {
  let component: MonthlyStreakTableComponent;
  let fixture: ComponentFixture<MonthlyStreakTableComponent>;
  let mockEmployeeService: EmployeeActivityService;
  const dummyAuthorData: StreakReputationModel[] = [
    {
      authorName: 'mark',
      scoreStreak: 100,
      streakRank: 5,
    }, {
      authorName: 'sam',
      scoreStreak: 100,
      streakRank: 5,
    }
  ];

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [MonthlyStreakTableComponent],
      imports: [HttpClientTestingModule, IonicModule.forRoot(), RouterTestingModule, FormsModule]
    }).compileComponents();

    fixture = TestBed.createComponent(MonthlyStreakTableComponent);
    component = fixture.componentInstance;
    mockEmployeeService = TestBed.get(EmployeeActivityService);
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
  it('should return the authorData as per api call', () => {
    spyOn(mockEmployeeService, 'getStreakData').and.returnValue(of(dummyAuthorData));
    component.ngOnInit();
    expect(component.employeeStreakData).toEqual(dummyAuthorData);
  });
});
