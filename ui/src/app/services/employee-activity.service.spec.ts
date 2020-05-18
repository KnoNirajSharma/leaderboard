import {TestBed} from '@angular/core/testing';
import {EmployeeActivityService} from './employee-activity.service';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {AuthorModel} from '../models/author.model';


describe('EmployeeActivityService', () => {
    let employeeActivityService: EmployeeActivityService;
    let httpTestingController: HttpTestingController;

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
        const dummyAuthorData: AuthorModel[] = [{
            knolderName: 'mark',
            score: 100,
            rank: 5,
        }, {
            knolderName: 'sam',
            score: 120,
            rank: 2,
        }];
        employeeActivityService.getData().subscribe(authorData => {
            expect(authorData).toEqual(dummyAuthorData);
        });
        const requestCheck = httpTestingController.expectOne('http://34.68.95.196:8000/reputation');
        expect(requestCheck.request.method).toBe('GET');
        requestCheck.flush(dummyAuthorData);
    });

    it('should retrieve monthly author data from the API via GET', () => {
        const dummyMonthlyData: AuthorModel[] = [
            {
                knolderName: 'mark',
                monthlyScore: 10,
                monthlyRank: 2,
            }, {
                knolderName: 'mark',
                monthlyScore: 1,
                monthlyRank: 8,
            }
        ];
        employeeActivityService.getMonthlyData().subscribe(authorData => {
            expect(authorData).toEqual(dummyMonthlyData);
        });
        const requestCheck = httpTestingController.expectOne('http://34.68.95.196:8000/reputation/monthly');
        expect(requestCheck.request.method).toBe('GET');
        requestCheck.flush(dummyMonthlyData);
    });

    it('should retrieve streak data from the API via GET', () => {
        const dummyMonthlyData: AuthorModel[] = [
            {
                knolderName: 'mark',
                streakScore: '9-9-9',
                rank: 2,
            }, {
                knolderName: 'mark',
                streakScore: '9-0-8',
                rank: 8,
            }
        ];
        employeeActivityService.getStreakData().subscribe(authorData => {
            expect(authorData).toEqual(dummyMonthlyData);
        });
        const requestCheck = httpTestingController.expectOne('/assets/data/streakData.json');
        expect(requestCheck.request.method).toBe('GET');
        requestCheck.flush(dummyMonthlyData);
    });
    afterEach(() => {
        httpTestingController.verify();
    });
});
