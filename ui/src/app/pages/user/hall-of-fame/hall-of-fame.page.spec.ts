import {HttpClientTestingModule} from '@angular/common/http/testing';
import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {IonicModule} from '@ionic/angular';
import {of} from 'rxjs';

import {ComponentsModule} from '../../../components/components.module';
import {HallOfFameModel} from '../../../models/hallOfFame.model';
import {CustomPipesModule} from '../../../pipe/custom-pipes.module';
import {CommonService} from '../../../services/common/common.service';
import {EmployeeActivityService} from '../../../services/employee-activity/employee-activity.service';
import {HallOfFamePage} from './hall-of-fame.page';

describe('HallOfFamePage', () => {
    let component: HallOfFamePage;
    let fixture: ComponentFixture<HallOfFamePage>;
    let mockEmployeeService: EmployeeActivityService;
    let commonService: CommonService;
    const mockHallOfFameData: HallOfFameModel[] = [
        {
            month: 'August',
            year: 2020,
            leaders: [
                {
                    month: 'August',
                    year: 2020,
                    knolderId: 1,
                    knolderName: 'Girish Chandra Bharti',
                    monthlyRank: 1,
                    monthlyScore: 100,
                    allTimeRank: 4,
                    allTimeScore: 2000,
                },
                {
                    month: 'August',
                    year: 2020,
                    knolderId: 15,
                    knolderName: 'Gaurav Kumar Shukla',
                    monthlyRank: 5, monthlyScore: 100,
                    allTimeRank: 4,
                    allTimeScore: 2000,
                },
            ],
        },
        {
            month: 'September',
            year: 2020,
            leaders: [
                {
                    month: 'September',
                    year: 2020,
                    knolderId: 1,
                    knolderName: 'Girish Chandra Bharti',
                    monthlyRank: 1,
                    monthlyScore: 100,
                    allTimeRank: 4,
                    allTimeScore: 2000,
                },
                {
                    month: 'September',
                    year: 2020,
                    knolderId: 15,
                    knolderName: 'Gaurav Kumar Shukla',
                    monthlyRank: 5, monthlyScore: 100,
                    allTimeRank: 4,
                    allTimeScore: 2000,
                },
            ],
        },
    ];

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [HallOfFamePage],
            imports: [
                HttpClientTestingModule,
                IonicModule.forRoot(),
                ComponentsModule,
                RouterTestingModule,
                CustomPipesModule,
            ],
        }).compileComponents();

        fixture = TestBed.createComponent(HallOfFamePage);
        component = fixture.componentInstance;
        mockEmployeeService = TestBed.get(EmployeeActivityService);
        commonService = TestBed.get(CommonService);
    }));

    it('should get the value for number of items in a page in hall of fame', () => {
        spyOn(component, 'setListIndexForPage');
        spyOn(mockEmployeeService, 'getHallOfFameData').and.returnValue(of([...mockHallOfFameData]));
        spyOnProperty(commonService, 'getNumberOfItemsInHallOfFame', 'get').and.returnValue(10);
        component.ngOnInit();
        expect(component.numberOfItemsInPage).toEqual(10);
    });

    it('should return the hall of fame data as per api call', () => {
        spyOn(component, 'setListIndexForPage');
        spyOnProperty(commonService, 'getNumberOfItemsInHallOfFame', 'get');
        spyOn(mockEmployeeService, 'getHallOfFameData').and.returnValue(of([...mockHallOfFameData]));
        component.ngOnInit();
        expect(component.hallOfFameLeaders).toEqual(mockHallOfFameData);
    });

    it('should set start and end index of list for a page', () => {
        component.numberOfItemsInPage = 10;
        component.setListIndexForPage(1);
        expect(component.startIndexOfListForPage).toEqual(10);
        expect(component.lastIndexOfListForPage).toEqual(20);
    });
});
