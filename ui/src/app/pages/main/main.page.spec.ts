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
import {TabComponent} from '../../components/tab/tab.component';

describe('MainPage', () => {
    let component: MainPage;
    let fixture: ComponentFixture<MainPage>;
    let mockEmployeeService: EmployeeActivityService;
    const dummyAuthorData: AuthorModel[] = [
        {
            authorName: 'mark',
            score: 10,
            rank: 2,
        }, {
            authorName: 'sam',
            score: 10,
            rank: 2,
        }
    ];
    const dummyMonthlyAuthorData: AuthorModel[] = [
        {
            authorName: 'mark',
            monthlyScore: 10,
            monthlyRank: 2,
        }, {
            authorName: 'mark',
            monthlyScore: 1,
            monthlyRank: 8,
        }
    ];
    const dummyStreakAuthorData: AuthorModel[] = [
        {
            authorName: 'mark',
            streakScore: '10-10-5',
            streakRank: 2,
        }, {
            authorName: 'mark',
            streakScore: '10-9-8',
            streakRank: 8,
        }
    ];
    const dummyBlogCount = '4';
    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [MainPage, CardComponent, HeadersComponent, TableComponent, SidebarComponent, TabComponent],
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

    it('should change the employeeData array', () => {
        component.tabValue = 'overall';
        spyOn(mockEmployeeService, 'getData').and.returnValue(of(dummyAuthorData));
        component.createComponent();
        expect(component.employeeData).toEqual(dummyAuthorData);
        component.tabValue = 'monthly';
        spyOn(mockEmployeeService, 'getMonthlyData').and.returnValue(of(dummyMonthlyAuthorData));
        component.createComponent();
        expect(component.employeeData).toEqual(dummyMonthlyAuthorData);
        component.tabValue = 'streak';
        spyOn(mockEmployeeService, 'getStreakData').and.returnValue(of(dummyStreakAuthorData));
        component.createComponent();
        expect(component.employeeData).toEqual(dummyStreakAuthorData);
    });
});
