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
    const dummyBlogCount = 4;
    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [MainPage, CardComponent, HeadersComponent],
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

    it('should return the authorData as per api call', () => {
        spyOn(mockEmployeeService, 'getData').and.returnValue(of(dummyAuthorData));
        component.ngOnInit();
        expect(component.employeeData).toEqual(dummyAuthorData);
    });

    it('should return the sum of the work', () => {
        spyOn(mockEmployeeService, 'getData').and.returnValue(of(dummyAuthorData));
        component.ngOnInit();
        expect(component.getTotalCount('blogs')).toEqual(dummyBlogCount);
    });
});
