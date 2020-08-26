import { async, ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';
import { MainPage } from './main.page';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { EmployeeActivityService } from '../../services/employee-activity.service';
import { of } from 'rxjs';
import { TableComponent } from '../../components/table/table.component';
import { EmployeeFilterPipe } from '../../pipe/employee-filter.pipe';
import { ReactiveFormsModule } from '@angular/forms';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { ComponentsModule } from '../../components/components.module';
import { ReputationModel } from '../../models/reputation.model';
import { LoadingControllerService } from '../../services/loading-controller.service ';
import { AngularFireModule } from '@angular/fire';
import { environment } from '../../../environments/environment';
import { AngularFirestoreModule } from '@angular/fire/firestore';
import { AngularFireAuthModule } from '@angular/fire/auth';

describe('MainPage', () => {
  let component: MainPage;
  let fixture: ComponentFixture<MainPage>;
  let mockEmployeeService: EmployeeActivityService;
  let loadingControllerService: LoadingControllerService;
  const dummyReputationData: ReputationModel = {
    monthlyBlogCount: 2,
    monthlyKnolxCount: 2,
    monthlyWebinarCount: 2,
    monthlyTechHubCount: 2,
    monthlyOsContributionCount: 4,
    allTimeBlogCount: 3,
    allTimeKnolxCount: 2,
    allTimeWebinarCount: 2,
    allTimeTechHubCount: 3,
    allTimeOsContributionCount: 7,
    reputation: [
      {
        knolderId: 1,
        knolderName: 'mark',
        allTimeScore: 10,
        allTimeRank: 7,
        quarterlyStreak: '5-6-7',
        monthlyScore: 10,
        monthlyRank: 1
      }, {
        knolderId: 2,
        knolderName: 'sam',
        allTimeScore: 20,
        allTimeRank: 6,
        quarterlyStreak: '5-6-8',
        monthlyScore: 10,
        monthlyRank: 1
      }
    ]
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [MainPage, TableComponent, EmployeeFilterPipe],
      imports: [
        HttpClientTestingModule,
        IonicModule.forRoot(),
        RouterTestingModule,
        ReactiveFormsModule,
        NgxDatatableModule,
        ComponentsModule,
        AngularFireModule.initializeApp(environment.firebaseConfig, 'angular-auth-firebase'),
        AngularFirestoreModule,
        AngularFireAuthModule
      ],
      providers: [EmployeeFilterPipe]
    }).compileComponents();

    fixture = TestBed.createComponent(MainPage);
    component = fixture.componentInstance;
    mockEmployeeService = TestBed.get(EmployeeActivityService);
    loadingControllerService = TestBed.get(LoadingControllerService);
    fixture.detectChanges();
  }));

  it('should return the authorData as per api call', () => {
    spyOn(mockEmployeeService, 'getData').and.returnValue(of(dummyReputationData));
    component.ngOnInit();
    expect(component.employeeData).toEqual(dummyReputationData.reputation);
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

  it('should compare on the allTimeRank property if values are not equal', () => {
    const firstEmp = dummyReputationData.reputation[1];
    const secEmp = dummyReputationData.reputation[0];
    expect(component.comparisonBasedOnAllTimeScore(firstEmp, secEmp, 'allTimeRank')).toEqual(false);
  });

  it('should compare on allTimeScore if values are equal', () => {
    const firstEmp = dummyReputationData.reputation[1];
    const secEmp = dummyReputationData.reputation[0];
    expect(component.comparisonBasedOnAllTimeScore(firstEmp, secEmp, 'monthlyScore')).toEqual(false);
  });

  it('should return the total sum of all month in 3 month streak', () => {
    const streakScore = '1-1-1';
    expect(component.totalOfQuarterlyScore(streakScore)).toEqual(3);
  });

  it('should return true or false depending on if the quarterly total score are in ascending order', () => {
    const firstEmpStreak = dummyReputationData.reputation[0].quarterlyStreak;
    const secEmpStreak = dummyReputationData.reputation[1].quarterlyStreak;
    expect(component.compareQuarterlyScore(firstEmpStreak, secEmpStreak, 'asc')).toEqual(true);
  });

  it('should return true or false depending on if the quarterly total score are in descending order', () => {
    const firstEmpStreak = dummyReputationData.reputation[0].quarterlyStreak;
    const secEmpStreak = dummyReputationData.reputation[1].quarterlyStreak;
    expect(component.compareQuarterlyScore(firstEmpStreak, secEmpStreak, 'desc')).toEqual(false);
  });

  it('should sort list in asc on the basis of quarterly score', () => {
    component.filteredEmpData = [...dummyReputationData.reputation];
    spyOn(component, 'compareQuarterlyScore').and.returnValue(true);
    component.sortTable({newValue: 'asc', column: {prop: 'quarterlyStreak'}});
    expect(component.filteredEmpData[0].knolderId).toEqual(1);
  });

  it('should sort list in desc on the basis of quarterly score', () => {
    component.filteredEmpData = [...dummyReputationData.reputation];
    spyOn(component, 'compareQuarterlyScore').and.returnValue(false);
    component.sortTable({newValue: 'asc', column: {prop: 'quarterlyStreak'}});
    expect(component.filteredEmpData[0].knolderId).toEqual(2);
  });

  it('should sort the list in descending order on the basis of allTimeRank', () => {
    component.filteredEmpData = [...dummyReputationData.reputation];
    component.sortTable({newValue: 'desc', column: {prop: 'allTimeRank'}});
    expect(component.filteredEmpData[0].knolderId).toEqual(1);
  });

  it('should sort the list in descending order on the basis of allTimeScore', () => {
    component.filteredEmpData = [...dummyReputationData.reputation];
    component.sortTable({newValue: 'desc', column: {prop: 'allTimeScore'}});
    expect(component.filteredEmpData[0].knolderId).toEqual(2);
  });

  it('should sort the list in ascending order on the basis of monthlyScore', () => {
    component.filteredEmpData = [...dummyReputationData.reputation];
    spyOn(component, 'comparisonBasedOnAllTimeScore').and.returnValue(false);
    component.sortTable({newValue: 'asc', column: {prop: 'monthlyScore'}});
    expect(component.filteredEmpData[0].knolderId).toEqual(2);
  });

  it('should sort the list in ascending order on the basis of allTimeScore', () => {
    component.filteredEmpData = [...dummyReputationData.reputation];
    spyOn(component, 'comparisonBasedOnAllTimeScore').and.returnValue(true);
    component.sortTable({newValue: 'asc', column: {prop: 'allTimeScore'}});
    expect(component.filteredEmpData[0].knolderId).toEqual(1);
  });
});
