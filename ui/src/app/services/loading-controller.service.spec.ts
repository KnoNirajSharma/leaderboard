import {TestBed} from '@angular/core/testing';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {LoadingControllerService} from './loading-controller.service ';

describe('LoadingControllerService', () => {
    let loadingControllerService: LoadingControllerService;
    let httpTestingController: HttpTestingController;
    const options: object = { message: 'Loading'};

    beforeEach(() => TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [LoadingControllerService]
        }
    ));

    beforeEach(() => {
        TestBed.configureTestingModule({});
        loadingControllerService = TestBed.get(LoadingControllerService);
        httpTestingController = TestBed.get(HttpTestingController);
    });

    it('should present', async () => {
        const presentSpy = spyOn(loadingControllerService, 'present');
        loadingControllerService.present(options);
        expect(presentSpy).toHaveBeenCalled();
    });

    it('should dismiss', async () => {
        const dismissSpy = spyOn(loadingControllerService, 'dismiss');
        loadingControllerService.dismiss();
        expect(dismissSpy).toHaveBeenCalled();
    });
});
