import {TestBed} from '@angular/core/testing';
import {EmployeeActivityService} from './employee-activity.service';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {AuthorModel} from '../models/author.model';
import {environment} from '../../environments/environment';
import {KnolderDetailsModel} from '../models/knolder-details.model';

describe('EmployeeActivityService', () => {
    let employeeActivityService: EmployeeActivityService;
    let httpTestingController: HttpTestingController;
    const url = `${environment.api.baseUrl}${environment.api.routes.author.endpoint}`;
    const trendsUrl = environment.api.routes.trends.endpoint;

    const dummyAuthorData: AuthorModel[] = [{
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
        allTimeScore: 10,
        allTimeRank: 2,
        quarterlyStreak: '5-6-7',
        monthlyScore: 7,
        monthlyRank: 1
    }];
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
        {month: 'JUNE',
        year: 2020,
        score: 3},
        {month: 'JULY',
            year: 2020,
            score: 3}
    ];
    const id = 1;
    const month = 'june';
    const year = 2020;

    beforeEach(() => TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [EmployeeActivityService]
        }
    ));

    beforeEach(() => {
        TestBed.configureTestingModule({});
        employeeActivityService = TestBed.get(EmployeeActivityService);
        httpTestingController = TestBed.get(HttpTestingController);
    });

    it('should be created', () => {
        expect(employeeActivityService).toBeTruthy();
    });

    it('should retrieve author data from the API via GET', () => {
        employeeActivityService.getData().subscribe(authorData => {
            expect(authorData).toEqual(dummyAuthorData);
        });
        const requestCheck = httpTestingController.expectOne(url);
        expect(requestCheck.request.method).toBe('GET');
        requestCheck.flush(dummyAuthorData);
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
        employeeActivityService.getTrendsData().subscribe(data => {
            expect(data).toEqual(dummyTrendsData);
        });
        const requestCheck = httpTestingController.expectOne(trendsUrl);
        expect(requestCheck.request.method).toBe('GET');
        requestCheck.flush(dummyTrendsData);
    });

    afterEach(() => {
        httpTestingController.verify();
    });
});
