import {async, ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';
import {IonicModule} from '@ionic/angular';
import {DetailsPage} from './details.page';
import {of} from 'rxjs';
import {KnolderDetailsModel} from '../../models/knolder-details.model';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {EmployeeActivityService} from '../../services/employee-activity.service';
import {BsDatepickerModule} from 'ngx-bootstrap/datepicker';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {By} from '@angular/platform-browser';
import {ComponentsModule} from '../../components/components.module';
import {LoadingControllerService} from '../../services/loading-controller.service ';


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

    const dummyTrendsData = [
        {
            month: 'JUNE',
            year: 2020,
            score: 4
        },
        {
            month: 'JULY',
            year: 2020,
            score: 6
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
            ]
        }).compileComponents();

        fixture = TestBed.createComponent(DetailsPage);
        component = fixture.componentInstance;
        mockEmployeeService = TestBed.get(EmployeeActivityService);
        loadingControllerService = TestBed.get(LoadingControllerService);
        fixture.detectChanges();
    }));

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should call method to get all time details', async(() => {
        spyOn(component, 'getAllTimeDetails');
        component.ngOnInit();
        const button = fixture.debugElement.query(By.css('.all-time-btn'));
        const buttonElem = button.nativeElement;
        buttonElem.dispatchEvent(new Event('click'));
        expect(component.getAllTimeDetails).toHaveBeenCalled();
    }));

    it('should return the knolder monthly details Data as per api call', () => {
        const testMonth = 'june';
        const testYear = 2020;
        spyOn(mockEmployeeService, 'getMonthlyDetails').and.returnValue(of(dummyKnolderDetails));
        component.getMonthlyDetails(testMonth, testYear);
        expect(component.knolderDetails).toEqual(dummyKnolderDetails);
    });

    it('should return the knolder Alltime details Data as per api call', () => {
        spyOn(mockEmployeeService, 'getAllTimeDetails').and.returnValue(of(dummyKnolderDetails));
        component.ngOnInit();
        component.getAllTimeDetails();
        expect(component.knolderDetails).toEqual(dummyKnolderDetails);
    });

    it('should invoke loader', fakeAsync(() => {
        spyOn(loadingControllerService, 'present').and.callThrough();
        component.ngOnInit();
        tick();
        fixture.detectChanges();
        expect(loadingControllerService.present).toHaveBeenCalled();
    }));

    it('should return the trendsData as per api call', () => {
        spyOn(mockEmployeeService, 'getTrendsData').and.returnValue(of(dummyTrendsData));
        component.ngOnInit();
        expect(component.trendsData).toEqual(dummyTrendsData);
    });
});
