import { TestBed } from '@angular/core/testing';
import {HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpIntercept } from './http.intercept';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import {EmployeeActivityService} from '../services/employee-activity.service';

describe(`AuthHttpInterceptor`, () => {
    let service: EmployeeActivityService;
    let httpMock: HttpTestingController;
    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [
                EmployeeActivityService,
                {
                    provide: HTTP_INTERCEPTORS,
                    useClass: HttpIntercept,
                    multi: true,
                },
            ],
        });
        service = TestBed.get(EmployeeActivityService);
        httpMock = TestBed.get(HttpTestingController);
    });
    it('should add and Accept header', () => {
        service.getData().subscribe(response => {
            expect(response).toBeTruthy();
        });
        const httpRequest = httpMock.expectOne('/assets/data/authorProfile.json');
        expect(httpRequest.request.headers.has('Accept')).toEqual(true);
    });
});
