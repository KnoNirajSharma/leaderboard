import {TestBed} from '@angular/core/testing';
import {EmployeeActivityService} from './employee-activity.service';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {AuthorModel} from '../models/author.model';
import {environment} from '../../environments/environment';
import {KnolderDetailsModel} from '../models/knolder-details.model';


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
        const allTimeApiUrl = `${environment.api.baseUrl}${environment.api.routes.author.endpoint}`;
        employeeActivityService.getData().subscribe(authorData => {
            expect(authorData).toEqual(dummyAuthorData);
        });
        const requestCheck = httpTestingController.expectOne(allTimeApiUrl);
        expect(requestCheck.request.method).toBe('GET');
        requestCheck.flush(dummyAuthorData);
    });

    it('should retrieve author data from the API via GET', () => {
        const dummyModalData: KnolderDetailsModel = {
            knolderName: 'mark',
            allTimeScore: 100,
            monthlyScore: 20,
            blogScore: 20,
            blogDetails: [
                {
                    title: 'abc',
                    date: '2020-05-06 13:34:09'
                }
            ]
        };
        const knolderDetailsUrl = environment.api.routes.knolderDetails.endpoint;
        employeeActivityService.getDetails().subscribe(modalData => {
            expect(modalData).toEqual(dummyModalData);
        });
        const requestCheck = httpTestingController.expectOne(knolderDetailsUrl);
        expect(requestCheck.request.method).toBe('GET');
        requestCheck.flush(dummyModalData);
    });

    afterEach(() => {
        httpTestingController.verify();
    });
});
