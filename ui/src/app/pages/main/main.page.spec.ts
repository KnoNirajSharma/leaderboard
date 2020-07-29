import {async, ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';
import {IonicModule} from '@ionic/angular';
import {MainPage} from './main.page';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {EmployeeActivityService} from '../../services/employee-activity.service';
import {of} from 'rxjs';
import {TableComponent} from '../../components/table/table.component';
import {EmployeeFilterPipe} from '../../pipe/employee-filter.pipe';
import {ReactiveFormsModule} from '@angular/forms';
import {By} from '@angular/platform-browser';
import {NgxDatatableModule} from '@swimlane/ngx-datatable';
import {ComponentsModule} from '../../components/components.module';
import {ReputationModel} from '../../models/reputation.model';
import {LoadingControllerService} from '../../services/loading-controller.service ';

describe('MainPage', () => {
    let component: MainPage;
    let fixture: ComponentFixture<MainPage>;
    let mockEmployeeService: EmployeeActivityService;
    let loadingControllerService: LoadingControllerService;
    const dummyReputationData: ReputationModel = {
        monthlyBlogCount: 2,
        monthlyKnolxCount: 2,
        monthlyWebinarCount:2,
        allTimeBlogCount: 3,
        allTimeKnolxCount: 2,
        allTimeWebinarCount:2,
        reputation: [
            {
                knolderId: 1,
                knolderName: 'mark',
                allTimeScore: 10,
                allTimeRank: 2,
                quarterlyStreak: '5-6-7',
                monthlyScore: 7,
                monthlyRank: 1
            }, {
                knolderId: 2,
                knolderName: 'sam',
                allTimeScore: 15,
                allTimeRank: 1,
                quarterlyStreak: '5-6-8',
                monthlyScore: 5,
                monthlyRank: 2
            }
        ]
    };

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [MainPage, TableComponent, EmployeeFilterPipe],
            imports: [HttpClientTestingModule,
                IonicModule.forRoot(),
                RouterTestingModule,
                ReactiveFormsModule,
                NgxDatatableModule,
                ComponentsModule],
            providers: [EmployeeFilterPipe]
        }).compileComponents();

        fixture = TestBed.createComponent(MainPage);
        component = fixture.componentInstance;
        mockEmployeeService = TestBed.get(EmployeeActivityService);
        loadingControllerService = TestBed.get(LoadingControllerService);
        fixture.detectChanges();
    }));

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should return the authorData as per api call', () => {
        spyOn(mockEmployeeService, 'getData').and.returnValue(of(dummyReputationData));
        component.ngOnInit();
        expect(component.employeeData).toEqual(dummyReputationData.reputation);
    });

    it('should call the filterEmp on keyup', () => {
        spyOn(component, 'filterEmp');
        fixture.detectChanges();
        const input = fixture.debugElement.query(By.css('input'));
        const inputElement = input.nativeElement;
        inputElement.dispatchEvent(new Event('keyup'));
        expect(component.filterEmp).toHaveBeenCalled();
    });

    it('should filter Employee', () => {
        component.empFilterPipe = new EmployeeFilterPipe();
        component.employeeData = dummyReputationData.reputation;
        component.searchBar.setValue('mark');
        component.filterEmp();
        expect(component.filteredEmpData).toEqual([dummyReputationData.reputation[0]]);
    });

    it('should invoke loader', fakeAsync(() => {
        spyOn(loadingControllerService, 'present').and.callThrough();
        component.ngOnInit();
        tick();
        fixture.detectChanges();
        expect(loadingControllerService.present).toHaveBeenCalled();
    }));
});
