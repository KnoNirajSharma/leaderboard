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
import {of, throwError} from 'rxjs';
import { RouterTestingModule } from '@angular/router/testing';
import { AngularFireModule } from '@angular/fire';
import { environment } from '../../../environments/environment';
import { AngularFirestoreModule } from '@angular/fire/firestore';
import { AngularFireAuthModule } from '@angular/fire/auth';
import { TrendsModel } from '../../models/trends.model';
import { ActivatedRoute } from '@angular/router';
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
    {
      month: 'August',
      year: 2020,
      leaders: [
        {
          month: 'August',
          year: 2020,
          knolderId: 1,
          knolderName: 'Girish Chandra Bharti',
          monthlyRank: 1,
          monthlyScore: 100,
          allTimeRank: 4,
          allTimeScore: 2000,
        },
        {
          month: 'August',
          year: 2020,
          knolderId: 15,
          knolderName: 'Gaurav Kumar Shukla',
          monthlyRank: 5, monthlyScore: 100,
          allTimeRank: 4,
          allTimeScore: 2000,
        },
      ]
    },
    {
      month: 'September',
      year: 2020,
      leaders: [
        {
          month: 'September',
          year: 2020,
          knolderId: 1,
          knolderName: 'Girish Chandra Bharti',
          monthlyRank: 1,
          monthlyScore: 100,
          allTimeRank: 4,
          allTimeScore: 2000,
        },
        {
          month: 'September',
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
      ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            params: of({
              id: 1,
            })
          }
        }]
    }).compileComponents();

    fixture = TestBed.createComponent(DetailsPage);
    component = fixture.componentInstance;
    mockEmployeeService = TestBed.get(EmployeeActivityService);
    loadingControllerService = TestBed.get(LoadingControllerService);
  }));

  it('should get knolder id from params and also initialize all data', () => {
    component.currentDate = new Date();
    spyOn(component, 'calenderInitialisation');
    spyOn(component, 'getMonthlyDetails');
    spyOn(component, 'getTrendsData');
    spyOn(component, 'getAllTimeDetails');
    spyOn(component, 'getHallOfFameData');
    spyOn(loadingControllerService, 'present');
    component.ngOnInit();
    expect(component.knolderId).toEqual(1);
    expect(loadingControllerService.present).toHaveBeenCalled();
  });

  it('should set values for datepickerConfig', () => {
    component.calenderInitialisation();
    expect(component.datepickerConfig.containerClass).toEqual('theme-dark-blue');
  });

  it('should return the knolder monthly details Data as per api call', () => {
    spyOn(mockEmployeeService, 'getMonthlyDetails').and.returnValue(of(dummyKnolderDetails));
    component.getMonthlyDetails('june', 2020);
    expect(component.knolderDetails).toEqual(dummyKnolderDetails);
  });

  it('should handle error if error occurs in monthly api', () => {
    spyOn(mockEmployeeService, 'getMonthlyDetails').and.returnValue(throwError({status: 404}));
    component.getMonthlyDetails('june', 2020);
  });


  it('should return the trendsData as per api call', () => {
    spyOn(mockEmployeeService, 'getTrendsData').and.returnValue(of(dummyTrendsData));
    component.getTrendsData();
    expect(component.trendsData).toEqual(dummyTrendsData);
  });

  it('should handle error if error occurs in trend api', () => {
    spyOn(mockEmployeeService, 'getTrendsData').and.returnValue(throwError({status: 404}));
    component.getTrendsData();
  });

  it('should return the knolder Alltime details Data as per api call', () => {
    spyOn(mockEmployeeService, 'getAllTimeDetails').and.returnValue(of(dummyKnolderDetails));
    component.getAllTimeDetails();
    expect(component.allTimeDetails).toEqual(dummyKnolderDetails);
  });

  it('should dissmiss the loader when error occurs in all time api service', () => {
    spyOn(mockEmployeeService, 'getAllTimeDetails').and.returnValue(throwError({status: 404}));
    spyOn(loadingControllerService, 'dismiss');
    component.getAllTimeDetails();
    expect(loadingControllerService.dismiss).toHaveBeenCalled();
  });

  it('should change alltimeSelected value to false', () => {
    component.onDateChange(new Date());
    expect(component.allTimeSelected).toEqual(false);
  });

  it('should change alltimeSelected value to true', () => {
    component.setAllTimeDetailsOnClick();
    expect(component.allTimeSelected).toEqual(true);
  });

  it('should change knolderDetails value to alltimeDetails', () => {
    component.allTimeDetails = dummyKnolderDetails;
    component.setAllTimeDetailsOnClick();
    expect(component.knolderDetails).toEqual(component.allTimeDetails);
  });

  it('should return the hall of data per api call', () => {
    spyOn(component, 'setKnolderAchievements');
    spyOn(component, 'setMedalTally');
    spyOn(mockEmployeeService, 'getHallOfFameData').and.returnValue(of(mockHallOfFameData));
    component.getHallOfFameData();
    expect(component.hallOfFameLeaders).toEqual(mockHallOfFameData);
  });

  it('should set knolderAchievements by matching the knolderID', () => {
    component.knolderId = 1;
    component.hallOfFameLeaders = mockHallOfFameData;
    component.setKnolderAchievements();
    expect(component.knolderAchievements.length).toEqual(2);
  });

  it('should set medal tally by checking for each type of achievement by the knolder', () => {
    component.knolderAchievements = [
      { ...mockHallOfFameData[0].leaders[0], position: 0 },
      { ...mockHallOfFameData[1].leaders[0], position: 0 }
      ];
    component.setMedalTally();
    expect(component.medalTally.gold).toEqual(2);
  });
});
