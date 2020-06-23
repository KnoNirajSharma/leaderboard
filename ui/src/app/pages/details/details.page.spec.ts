import {async, ComponentFixture, TestBed} from '@angular/core/testing';
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

describe('DetailsPage', () => {
    let component: DetailsPage;
    let fixture: ComponentFixture<DetailsPage>;
    let mockEmployeeService: EmployeeActivityService;
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

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [DetailsPage],
            imports: [HttpClientTestingModule,
                IonicModule.forRoot(),
                RouterTestingModule,
                BsDatepickerModule.forRoot(),
                FormsModule,
                ReactiveFormsModule,
                ComponentsModule]
        }).compileComponents();

        fixture = TestBed.createComponent(DetailsPage);
        component = fixture.componentInstance;
        mockEmployeeService = TestBed.get(EmployeeActivityService);
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
});
