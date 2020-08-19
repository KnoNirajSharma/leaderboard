import { TestBed } from '@angular/core/testing';
import { EmployeeActivityService } from './employee-activity.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { environment } from '../../environments/environment';
import { KnolderDetailsModel } from '../models/knolder-details.model';
import { ReputationModel } from '../models/reputation.model';

describe('EmployeeActivityService', () => {
  let employeeActivityService: EmployeeActivityService;
  let httpTestingController: HttpTestingController;
  const url = `${environment.api.baseUrl}${environment.api.routes.author.endpoint}`;
  const trendsUrl = `${environment.api.baseUrl}${environment.api.routes.trends.endpoint}`;

  const dummyReputationData: ReputationModel = {
    monthlyBlogCount: 2,
    monthlyKnolxCount: 2,
    monthlyWebinarCount: 2,
    monthlyTechHubCount: 2,
    allTimeBlogCount: 3,
    allTimeKnolxCount: 2,
    allTimeWebinarCount: 2,
    allTimeTechHubCount: 3,
    reputation: [
      {
        knolderId: 1,
        knolderName: 'mark',
        allTimeScore: 10,
        allTimeRank: 2,
        quarterlyStreak: '5-6-7',
        monthlyScore: 7,
        monthlyRank: 1
      }, {
        knolderId: 2,
        knolderName: 'sam',
        allTimeScore: 15,
        allTimeRank: 1,
        quarterlyStreak: '5-6-8',
        monthlyScore: 5,
        monthlyRank: 2
      }
    ]
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
            date: '2020-05-06 14:16:23'
          }
        ]
      }
    ]
  };

  const dummyTrendsData = [
    {
      month: 'JUNE',
      year: 2020,
      blogScore: 30,
      knolxScore: 20,
      webinarScore: 34,
      techHubScore: 20,
    },
    {
      month: 'JULY',
      year: 2020,
      blogScore: 30,
      knolxScore: 20,
      webinarScore: 34,
      techHubScore: 20,
    }
  ];
  const id = 1;
  const month = 'june';
  const year = 2020;

  beforeEach(() => TestBed.configureTestingModule({
    imports: [HttpClientTestingModule],
    providers: [EmployeeActivityService]
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

  afterEach(() => {
    httpTestingController.verify();
  });
});
