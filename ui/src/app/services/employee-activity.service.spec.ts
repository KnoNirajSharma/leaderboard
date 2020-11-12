import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { environment } from '../../environments/environment';
import { HallOfFameModel } from '../models/hallOfFame.model';
import { KnolderDetailsModel } from '../models/knolder-details.model';
import { ReputationModel } from '../models/reputation.model';
import {ScoringTableModel} from '../models/scoring-table.model';
import {TrendsModel} from '../models/trends.model';
import {TribeDetailsModel} from '../models/tribe-details.model';
import {TribesSummeryModel} from '../models/tribes-summery.model';
import { EmployeeActivityService } from './employee-activity.service';


describe('EmployeeActivityService', () => {
  let employeeActivityService: EmployeeActivityService;
  let httpTestingController: HttpTestingController;
  const url = `${environment.api.baseUrl}${environment.api.routes.author.endpoint}`;
  const trendsUrl = `${environment.api.baseUrl}${environment.api.routes.trends.endpoint}`;
  const hallOfFameUrl = `${environment.api.baseUrl}${environment.api.routes.hallOfFame.endpoint}`;
  const scoringInfoUrl = `${environment.api.baseUrl}${environment.api.routes.dynamicScoring.endpoint}`;
  const allTribesUrl = '/assets/data/tribes-main.json';
  const tribeDetailUrl = '/assets/data/tribe-details.json';

  const dummyReputationData: ReputationModel = {
    blogs: { monthly: 2, allTime: 3 },
    knolx: { monthly: 2, allTime: 3 },
    webinars: { monthly: 2, allTime: 3 },
    techhubTemplates: { monthly: 2, allTime: 3 },
    osContributions: { monthly: 2, allTime: 3 },
    conferences: { monthly: 2, allTime: 3 },
    books: { monthly: 2, allTime: 3 },
    researchPapers: { monthly: 2, allTime: 3 },
    reputation: [
      {
        knolderId: 1,
        knolderName: 'mark',
        allTimeScore: 10,
        allTimeRank: 7,
        quarterlyStreak: '5-6-7',
        monthlyScore: 10,
        monthlyRank: 1,
      }, {
        knolderId: 2,
        knolderName: 'sam',
        allTimeScore: 20,
        allTimeRank: 6,
        quarterlyStreak: '5-6-8',
        monthlyScore: 10,
        monthlyRank: 1,
      },
    ],
  };
  const dummyDetailData: KnolderDetailsModel = {
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
      meetUpScore: 10,
    },
    {
      month: 'JULY',
      year: 2020,
      blogScore: 40,
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
      ] },
  ];
  const mockScoringData: ScoringTableModel = {
    blog: {points: 5, pointsMultiplier: 1},
    knolx: {points: 5, pointsMultiplier: 1},
    webinar: {points: 5, pointsMultiplier: 1},
    techhubTemplate: {points: 5, pointsMultiplier: 1},
    osContribution: {points: 5, pointsMultiplier: 1},
    conference: {points: 5, pointsMultiplier: 1},
    book: {points: 5, pointsMultiplier: 1},
    researchPaper: {points: 5, pointsMultiplier: 1},
  };
  const mockAllTrendSummeryData: TribesSummeryModel[] = [
    {
      id: 'scala',
      name: 'Scala',
      allTimeScore: 3000,
      monthlyScore: 200,
      memberAvg: 40,
      memberCount: 50,
    },
    {
      id: 'dev-ops',
      name: 'DevOps',
      allTimeScore: 3000,
      monthlyScore: 200,
      memberAvg: 40,
      memberCount: 50,
    },
  ];
  const mockTribeDetailsData: TribeDetailsModel = {
    name: 'DevOps',
    tribeSummery: [
      {name: 'all time score', value: 2000},
      {name: 'monthly score', value: 200},
      {name: 'Per member score', value: 20},
    ],
    allTimeScoreBreakdown: [
      {contributionType: 'Blog', contributionScore: 45},
      {contributionType: 'Knolx', contributionScore: 4},
      {contributionType: 'webinar', contributionScore: 70},
    ],
    trends: [
      {
        month: 'JUNE',
        year: 2020,
        blogScore: 30,
        knolxScore: 20,
        webinarScore: 34,
        techHubScore: 20,
        osContributionScore: 30,
        conferenceScore: 100,
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
        conferenceScore: 0,
        bookScore: 0,
        researchPaperScore: 100,
        meetUpScore: 100,
      },
    ],
    memberReputations: [
      {
        knolderId: 1,
        knolderName: 'mark',
        allTimeScore: 10,
        allTimeRank: 7,
        quarterlyStreak: '5-6-7',
        monthlyScore: 10,
        monthlyRank: 1,
      }, {
        knolderId: 2,
        knolderName: 'sam',
        allTimeScore: 20,
        allTimeRank: 6,
        quarterlyStreak: '5-6-8',
        monthlyScore: 10,
        monthlyRank: 1,
      },
    ],
  };
  const id = 1;
  const month = 'june';
  const year = 2020;
  const tribeId = 'dev-ops';

  beforeEach(() => TestBed.configureTestingModule({
    imports: [HttpClientTestingModule],
    providers: [EmployeeActivityService],
  }));

  beforeEach(() => {
    TestBed.configureTestingModule({});
    employeeActivityService = TestBed.get(EmployeeActivityService);
    httpTestingController = TestBed.get(HttpTestingController);
  });

  it('should be created', () => {
    expect(employeeActivityService).toBeTruthy();
  });

  it('should retrieve reputation data from the API via GET', () => {
    employeeActivityService.getData().subscribe(reputationData => {
      expect(reputationData).toEqual(dummyReputationData);
    });
    const requestCheck = httpTestingController.expectOne(url);
    expect(requestCheck.request.method).toBe('GET');
    requestCheck.flush(dummyReputationData);
  });

  it('should retrieve knolder all time detail data from the API via GET', () => {
    employeeActivityService.getMonthlyDetails(id, month, year).subscribe(data => {
      expect(data).toEqual(dummyDetailData);
    });
    const requestCheck = httpTestingController.expectOne(url + '/' + id + '?month=' + month + '&year=' + year);
    expect(requestCheck.request.method).toBe('GET');
    requestCheck.flush(dummyDetailData);
  });

  it('should retrieve knolder monthly detail data from the API via GET', () => {
    employeeActivityService.getAllTimeDetails(id).subscribe(data => {
      expect(data).toEqual(dummyDetailData);
    });
    const requestCheck = httpTestingController.expectOne(url + '/' + id);
    expect(requestCheck.request.method).toBe('GET');
    requestCheck.flush(dummyDetailData);
  });

  it('should retrieve knolder 12 month trends data from the API via GET', () => {
    employeeActivityService.getTrendsData(id).subscribe(data => {
      expect(data).toEqual(dummyTrendsData);
    });
    const requestCheck = httpTestingController.expectOne(trendsUrl + '/' + id);
    expect(requestCheck.request.method).toBe('GET');
    requestCheck.flush(dummyTrendsData);
  });

  it('should retrieve hall of fame data from the API via GET', () => {
    employeeActivityService.getHallOfFameData().subscribe(data => {
      expect(data).toEqual(mockHallOfFameData);
    });
    const requestCheck = httpTestingController.expectOne(hallOfFameUrl);
    expect(requestCheck.request.method).toBe('GET');
    requestCheck.flush(mockHallOfFameData);
  });

  it('should retrieve scoring info data from the API via GET', () => {
    employeeActivityService.getScoringInfoData().subscribe(data => {
      expect(data).toEqual(mockScoringData);
    });
    const requestCheck = httpTestingController.expectOne(scoringInfoUrl);
    expect(requestCheck.request.method).toBe('GET');
    requestCheck.flush(mockScoringData);
  });

  it('should retrieve all tribes summery data from the API via GET', () => {
    employeeActivityService.getAllTribesData().subscribe(data => {
      expect(data).toEqual(mockAllTrendSummeryData);
    });
    const requestCheck = httpTestingController.expectOne(allTribesUrl);
    expect(requestCheck.request.method).toBe('GET');
    requestCheck.flush(mockAllTrendSummeryData);
  });

  it('should retrieve tribe detail data from the API via GET', () => {
    employeeActivityService.getTribeDetails(tribeId).subscribe(data => {
      expect(data).toEqual(mockTribeDetailsData);
    });
    const requestCheck = httpTestingController.expectOne(tribeDetailUrl);
    expect(requestCheck.request.method).toBe('GET');
    requestCheck.flush(mockTribeDetailsData);
  });

  afterEach(() => {
    httpTestingController.verify();
  });
});
