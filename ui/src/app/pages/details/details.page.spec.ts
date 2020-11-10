import { HttpClientTestingModule } from '@angular/common/http/testing';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { AngularFireModule } from '@angular/fire';
import { AngularFireAuthModule } from '@angular/fire/auth';
import { AngularFirestoreModule } from '@angular/fire/firestore';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { IonicModule } from '@ionic/angular';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';
import { of } from 'rxjs';

import { environment } from '../../../environments/environment';
import { ComponentsModule } from '../../components/components.module';
import { HallOfFameModel } from '../../models/hallOfFame.model';
import { KnolderDetailsModel } from '../../models/knolder-details.model';
import { TrendsModel } from '../../models/trends.model';
import { CommonService } from '../../services/common.service';
import { EmployeeActivityService } from '../../services/employee-activity.service';
import { ScoreDetailsComponent } from './components/score-details/score-details.component';
import { ScoreDrilldownComponent } from './components/score-drilldown/score-drilldown.component';
import { DetailsPage } from './details.page';


describe('DetailsPage', () => {
  let component: DetailsPage;
  let fixture: ComponentFixture<DetailsPage>;
  let mockEmployeeService: EmployeeActivityService;
  let commonService: CommonService;
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
            date: '2020-05-06 14:16:23',
          },
        ],
      },
    ],
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
      meetUpScore: 0,
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
      meetUpScore: 0,
    },
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
      ],
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
      ],
    },
  ];

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [DetailsPage, ScoreDrilldownComponent, ScoreDetailsComponent],
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
        AngularFireAuthModule,
      ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            queryParams: of({
              id: 1,
              year: 2020,
              month: 'january',
            }),
          },
        }],
    }).compileComponents();

    fixture = TestBed.createComponent(DetailsPage);
    component = fixture.componentInstance;
    mockEmployeeService = TestBed.get(EmployeeActivityService);
    commonService = TestBed.get(CommonService);
  }));

  it('should get knolder id from params and set contributionsTypeColorList', () => {
    component.currentDate = new Date();
    spyOn(component, 'calenderInitialisation');
    spyOn(component, 'getMonthlyDetails');
    spyOn(component, 'getTrendsData');
    spyOn(component, 'getAllTimeDetails');
    spyOn(component, 'getHallOfFameData');
    spyOnProperty(commonService, 'colorScheme', 'get').and.returnValue({domain: ['blue']});
    component.ngOnInit();
    expect(component.knolderId).toEqual(1);
    expect(component.yearFromRoute).toEqual(2020);
    expect(component.monthFromRoute).toEqual('january');
    expect(component.contributionsTypeColorList[0]).toEqual('blue');
  });

  it('should set values for datepickerConfig', () => {
    component.monthFromRoute = 'january';
    component.yearFromRoute = 2020;
    component.calenderInitialisation();
    expect(component.datepickerConfig.containerClass).toEqual('theme-dark-blue');
    expect(component.dateFromRoute.month()).toEqual(0);
    expect(component.dateFromRoute.year()).toEqual(2020);
  });

  it('should return the knolder monthly details Data as per api call', () => {
    spyOn(mockEmployeeService, 'getMonthlyDetails').and.returnValue(of(dummyKnolderDetails));
    component.getMonthlyDetails('june', 2020);
    expect(component.knolderDetails).toEqual(dummyKnolderDetails);
  });

  it('should return the trendsData as per api call', () => {
    spyOn(mockEmployeeService, 'getTrendsData').and.returnValue(of(dummyTrendsData));
    component.getTrendsData();
    expect(component.trendsData).toEqual(dummyTrendsData);
  });

  it('should return the knolder Alltime details Data as per api call', () => {
    spyOn(mockEmployeeService, 'getAllTimeDetails').and.returnValue(of(dummyKnolderDetails));
    component.getAllTimeDetails();
    expect(component.allTimeDetails).toEqual(dummyKnolderDetails);
  });

  it('should change alltimeSelected value to false', () => {
    spyOn(component, 'getMonthlyDetails');
    const date = new Date();
    date.setMonth(0);
    date.setFullYear(2020);
    component.onDateChange(date);
    expect(component.allTimeSelected).toEqual(false);
    expect(component.getMonthlyDetails).toHaveBeenCalledWith('january', 2020);
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

  it('should set medal tally by checking count of each rank of achievement by the knolder', () => {
    component.knolderAchievements = [
      { ...mockHallOfFameData[0].leaders[0], position: 0 },
      { ...mockHallOfFameData[1].leaders[0], position: 0 },
      ];
    component.setMedalTally();
    expect(component.medalTally.gold.count).toEqual(2);
  });
});
