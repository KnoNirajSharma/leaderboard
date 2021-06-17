import {HttpClientTestingModule} from '@angular/common/http/testing';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {IonicModule} from '@ionic/angular';
import {of} from 'rxjs';

import {ComponentsModule} from '../../../components/components.module';
import {TribesSummeryModel} from '../../../models/tribes-summery.model';
import {EmployeeActivityService} from '../../../services/employee-activity/employee-activity.service';
import {TribesPage} from './tribes.page';

describe('TribesPage', () => {
    let component: TribesPage;
    let fixture: ComponentFixture<TribesPage>;
    let mockEmployeeService: EmployeeActivityService;
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

    beforeEach(waitForAsync(() => {
        TestBed.configureTestingModule({
            declarations: [TribesPage],
            imports: [
                IonicModule.forRoot(),
                ComponentsModule,
                HttpClientTestingModule,
                IonicModule.forRoot(),
                RouterTestingModule,
            ],
        }).compileComponents();

        fixture = TestBed.createComponent(TribesPage);
        component = fixture.componentInstance;
        mockEmployeeService = TestBed.get(EmployeeActivityService);
    }));

    it('should return the all tribes summery data as per api call', () => {
        spyOn(mockEmployeeService, 'getAllTribesData').and.returnValue(of([...mockAllTrendSummeryData]));
        component.ngOnInit();
        expect(component.tribesList).toEqual(mockAllTrendSummeryData);
    });
});
