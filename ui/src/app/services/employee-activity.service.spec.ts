import {TestBed} from '@angular/core/testing';
import {EmployeeActivityService} from './employee-activity.service';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {AuthorModel} from '../models/author.model';
import {environment} from '../../environments/environment';


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
        const allTimeApiUrl = `${environment.api.baseUrl}${environment.api.routes.author.endpoint}`;
        employeeActivityService.getData().subscribe(authorData => {
            expect(authorData).toEqual(dummyAuthorData);
        });
        const requestCheck = httpTestingController.expectOne(allTimeApiUrl);
        expect(requestCheck.request.method).toBe('GET');
        requestCheck.flush(dummyAuthorData);
    });

    afterEach(() => {
        httpTestingController.verify();
    });
});
