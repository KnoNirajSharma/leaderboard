import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {IonicModule} from '@ionic/angular';

import {MainPage} from './main.page';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {EmployeeActivityService} from '../../services/employee-activity.service';
import {AuthorModel} from '../../models/author.model';
import {CardComponent} from '../../components/card/card.component';
import {HeadersComponent} from '../../components/headers/headers.component';
import {of} from 'rxjs';
import {SidebarComponent} from '../../components/sidebar/sidebar.component';
import {TableComponent} from '../../components/table/table.component';
import {By} from '@angular/platform-browser';

describe('MainPage', () => {
    let component: MainPage;
    let fixture: ComponentFixture<MainPage>;
    let mockEmployeeService: EmployeeActivityService;
    const dummyAuthorData: AuthorModel[] = [
        {
            knolderName: 'mark',
            score: 10,
            rank: 2,
        }, {
            knolderName: 'sam',
            score: 10,
            rank: 2,
        }
    ];
    const dummyMonthlyAuthorData: AuthorModel[] = [
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
    const dummyStreakAuthorData: AuthorModel[] = [
        {
            knolderName: 'mark',
            streakScore: '10-10-5',
            streakRank: 2,
        }, {
            knolderName: 'mark',
            streakScore: '10-9-8',
            streakRank: 8,
        }
    ];
    const dummyBlogCount = '4';
    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [MainPage, CardComponent, HeadersComponent, TableComponent, SidebarComponent],
            imports: [HttpClientTestingModule, IonicModule.forRoot(), RouterTestingModule]
        }).compileComponents();

        fixture = TestBed.createComponent(MainPage);
        component = fixture.componentInstance;
        mockEmployeeService = TestBed.get(EmployeeActivityService);
        fixture.detectChanges();
    }));

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should return the sum of the work', () => {
        spyOn(mockEmployeeService, 'getData').and.returnValue(of(dummyAuthorData));
        component.ngOnInit();
        expect(component.getTotalCount('blogs')).toEqual(dummyBlogCount);
    });

    it('should return the authorData as per api call', () => {
        spyOn(mockEmployeeService, 'getData').and.returnValue(of(dummyAuthorData));
        component.ngOnInit();
        expect(component.employeeData).toEqual(dummyAuthorData);
    });

    it('should call selectTab method', () => {
        component.tabData = [
            {tabName: 'All time', id: 'overall'},
            {tabName: 'Monthly', id: 'monthly'},
            {tabName: '3 month streak', id: 'streak'}
        ];
        component.currentlySelectedTab = 'overall';
        spyOn(component, 'selectTab');
        const butn = fixture.debugElement.query(By.css('#streak'));
        butn.triggerEventHandler('click', {});
        fixture.detectChanges();
        expect(component.selectTab).toHaveBeenCalled();
    });

    it('should call change currentlySelectedTab to overall', () => {
        component.currentlySelectedTab = 'null';
        component.selectTab('overall');
        expect(component.currentlySelectedTab).toEqual('overall');
    });

    it('should call change currentlySelectedTab to monthly', () => {
        component.currentlySelectedTab = 'monthly';
        component.selectTab('monthly');
        expect(component.currentlySelectedTab).toEqual('monthly');
    });

    it('should call populateTable', () => {
        spyOn(component, 'populateTable');
        component.selectTab('streak');
        expect(component.populateTable).toHaveBeenCalled();
    });

    it('should populate the table to with all time', () => {
        component.currentlySelectedTab = 'overall';
        spyOn(mockEmployeeService, 'getData').and.returnValue(of(dummyAuthorData));
        component.populateTable();
        expect(component.employeeData).toEqual(dummyAuthorData);
    });

    it('should populate the table with monthly data', () => {
        component.currentlySelectedTab = 'monthly';
        spyOn(mockEmployeeService, 'getMonthlyData').and.returnValue(of(dummyMonthlyAuthorData));
        component.populateTable();
        expect(component.employeeData).toEqual(dummyMonthlyAuthorData);
    });

    it('should populate the table with streak data', () => {
        component.currentlySelectedTab = 'streak';
        spyOn(mockEmployeeService, 'getStreakData').and.returnValue(of(dummyStreakAuthorData));
        component.populateTable();
        expect(component.employeeData).toEqual(dummyStreakAuthorData);
    });
});
