import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { ComponentsModule } from '../../components/components.module';
import { DetailsPage } from './details.page';
import { EmployeeActivityService } from '../../services/employee-activity.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { IonicModule } from '@ionic/angular';
import { KnolderDetailsModel } from '../../models/knolder-details.model';
import { LoadingControllerService } from '../../services/loading-controller.service ';
import { of } from 'rxjs';
import { RouterTestingModule } from '@angular/router/testing';
import { AngularFireModule } from '@angular/fire';
import { environment } from '../../../environments/environment';
import { AngularFirestoreModule } from '@angular/fire/firestore';
import { AngularFireAuthModule } from '@angular/fire/auth';
import { TrendsModel } from '../../models/trends.model';
import {HallOfFameModel} from '../../models/hallOfFame.model';


describe('DetailsPage', () => {
  let component: DetailsPage;
  let fixture: ComponentFixture<DetailsPage>;
  let mockEmployeeService: EmployeeActivityService;
  let loadingControllerService: LoadingControllerService;
  const dummyKnolderDetails: KnolderDetailsModel = {
    knolderName: 'Muskan Gupta',
    score: 20,
    scoreBreakDown: [
      {
        contributionType: 'Blog',
        contributionCount: 4,
        contributionScore: 20,
        contributionDetails: [
          {
            title: 'Serialization in Lagom',
            date: '2020-05-06 14:16:23'
          }
        ]
      }
    ]
  };

  const dummyTrendsData: TrendsModel[] = [
    {
      month: 'JUNE',
      year: 2020,
      blogScore: 30,
      knolxScore: 20,
      webinarScore: 34,
      techHubScore: 20,
      osContributionScore: 30,
      conferenceScore: 30,
      bookScore: 100,
      researchPaperScore: 0,
    },
    {
      month: 'JULY',
      year: 2020,
      blogScore: 30,
      knolxScore: 20,
      webinarScore: 34,
      techHubScore: 20,
      osContributionScore: 20,
      conferenceScore: 30,
      bookScore: 0,
      researchPaperScore: 50,
    }
  ];
  const mockHallOfFameData: HallOfFameModel[] = [
    { month: 'August',
      year: 2020,
      leaders: [
        { month: 'August',
          year: 2020,
          knolderId: 1,
          knolderName: 'Girish Chandra Bharti',
          monthlyRank: 1,
          monthlyScore: 100,
          allTimeRank: 4,
          allTimeScore: 2000,
        },
        { month: 'August',
          year: 2020,
          knolderId: 15,
          knolderName: 'Gaurav Kumar Shukla',
          monthlyRank: 5, monthlyScore: 100,
          allTimeRank: 4,
          allTimeScore: 2000,
        },
      ]
    }, { month: 'September',
      year: 2020,
      leaders: [
        { month: 'September',
          year: 2020,
          knolderId: 1,
          knolderName: 'Girish Chandra Bharti',
          monthlyRank: 1,
          monthlyScore: 100,
          allTimeRank: 4,
          allTimeScore: 2000,
        },
        { month: 'September',
          year: 2020,
          knolderId: 15,
          knolderName: 'Gaurav Kumar Shukla',
          monthlyRank: 5, monthlyScore: 100,
          allTimeRank: 4,
          allTimeScore: 2000,
        },
      ]
    }
  ];

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [DetailsPage],
      imports: [
        HttpClientTestingModule,
        IonicModule.forRoot(),
        RouterTestingModule,
        BsDatepickerModule.forRoot(),
        FormsModule,
        ReactiveFormsModule,
        ComponentsModule,
        AngularFireModule.initializeApp(environment.firebaseConfig, 'angular-auth-firebase'),
        AngularFirestoreModule,
        AngularFireAuthModule
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(DetailsPage);
    component = fixture.componentInstance;
    mockEmployeeService = TestBed.get(EmployeeActivityService);
    loadingControllerService = TestBed.get(LoadingControllerService);
  }));

  it('should return the knolder monthly details Data as per api call', () => {
    const testMonth = 'june';
    const testYear = 2020;
    spyOn(mockEmployeeService, 'getMonthlyDetails').and.returnValue(of(dummyKnolderDetails));
    component.getMonthlyDetails(testMonth, testYear);
    expect(component.knolderDetails).toEqual(dummyKnolderDetails);
  });

  it('should return the knolder Alltime details Data as per api call', () => {
    spyOn(mockEmployeeService, 'getAllTimeDetails').and.returnValue(of(dummyKnolderDetails));
    component.ngOnInit();
    component.getAllTimeDetails();
    expect(component.knolderDetails).toEqual(dummyKnolderDetails);
  });

  it('should return the trendsData as per api call', () => {
    spyOn(mockEmployeeService, 'getTrendsData').and.returnValue(of(dummyTrendsData));
    component.ngOnInit();
    expect(component.trendsData).toEqual(dummyTrendsData);
  });

  fit('should get the hall of fame data in reverse as per api call', () => {
    spyOn(mockEmployeeService, 'getHallOfFameData').and.returnValue(of(mockHallOfFameData));
    component.ngOnInit();
    // console.log(component.hallOfFameLeaders);
    expect(component.hallOfFameLeaders).toEqual(mockHallOfFameData);
  });

  it('should get knolder achievements from the leaders list', () => {
    spyOn(mockEmployeeService, 'getHallOfFameData').and.returnValue(of(mockHallOfFameData));
    component.knolderId = 1;
    component.ngOnInit();
    // console.log(component.knolderAchievements);
    expect(component.knolderAchievements.length).toEqual(2);
  });

  it('should get tally of medals according to knolder achievements from the leaders list', () => {
    spyOn(mockEmployeeService, 'getHallOfFameData').and.returnValue(of(mockHallOfFameData));
    component.knolderId = 1;
    component.ngOnInit();
    // console.log(component.medalTally);
    expect(component.medalTally.first).toEqual(2);
  });
});
