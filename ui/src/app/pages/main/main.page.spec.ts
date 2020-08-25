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
import { By } from '@angular/platform-browser';
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

  it('should sort the list in descending order on the basis of prop given', () => {
    component.filteredEmpData = [...dummyReputationData.reputation];
    const eventMock = {newValue: 'desc', column: {prop: 'allTimeRank'}};
    component.sortTable(eventMock);
    expect(component.filteredEmpData[0].knolderId).toEqual(1);
  });

  it('should sort the list in descending order on the basis of prop given', () => {
    component.filteredEmpData = [...dummyReputationData.reputation];
    const eventMock = {newValue: 'desc', column: {prop: 'allTimeScore'}};
    component.sortTable(eventMock);
    expect(component.filteredEmpData[0].knolderId).toEqual(2);
  });

  it('should sort the list in ascending order on the basis of prop given', () => {
    component.filteredEmpData = [...dummyReputationData.reputation];
    const eventMock = {newValue: 'asc', column: {prop: 'monthlyScore'}};
    spyOn(component, 'comparisonBasedOnAllTimeScore').and.returnValue(false);
    component.sortTable(eventMock);
    expect(component.filteredEmpData[0].knolderId).toEqual(2);
  });

  it('should sort the list in ascending 2 order on the basis of prop given', () => {
    component.filteredEmpData = [...dummyReputationData.reputation];
    const eventMock = {newValue: 'asc', column: {prop: 'allTimeScore'}};
    spyOn(component, 'comparisonBasedOnAllTimeScore').and.returnValue(true);
    component.sortTable(eventMock);
    expect(component.filteredEmpData[0].knolderId).toEqual(1);
  });

  it('should compare on the given property if values are not equal', () => {
    const firstEmp = dummyReputationData.reputation[1];
    const secEmp = dummyReputationData.reputation[0];
    const result = component.comparisonBasedOnAllTimeScore(firstEmp, secEmp, 'allTimeScore');
    expect(result).toEqual(true);
  });

  it('should compare on allTime score if values are equal', () => {
    const firstEmp = dummyReputationData.reputation[1];
    const secEmp = dummyReputationData.reputation[0];
    const result = component.comparisonBasedOnAllTimeScore(firstEmp, secEmp, 'monthlyScore');
    expect(result).toEqual(false);
  });

  it('should sort list in asc on the basis of quarterly score', () => {
    component.filteredEmpData = [...dummyReputationData.reputation];
    const eventMock = {newValue: 'asc', column: {prop: 'quarterlyStreak'}};
    spyOn(component, 'compareQuarterlyScore').and.returnValue(true);
    component.sortTable(eventMock);
    expect(component.filteredEmpData[0].knolderId).toEqual(1);
  });

  it('should sort list in desc on the basis of quarterly score', () => {
    component.filteredEmpData = [...dummyReputationData.reputation];
    const eventMock = {newValue: 'asc', column: {prop: 'quarterlyStreak'}};
    spyOn(component, 'compareQuarterlyScore').and.returnValue(false);
    component.sortTable(eventMock);
    expect(component.filteredEmpData[0].knolderId).toEqual(2);
  });

  it('should return true or false depending on if the quarterly total score are in ascending order', () => {
    const firstEmpStreak = dummyReputationData.reputation[0].quarterlyStreak;
    const secEmpStreak = dummyReputationData.reputation[1].quarterlyStreak;
    const result = component.compareQuarterlyScore(firstEmpStreak, secEmpStreak, 'asc');
    expect(result).toEqual(true);
  });

  it('should return true or false depending on if the quarterly total score are in descending order', () => {
    const firstEmpStreak = dummyReputationData.reputation[0].quarterlyStreak;
    const secEmpStreak = dummyReputationData.reputation[1].quarterlyStreak;
    const result = component.compareQuarterlyScore(firstEmpStreak, secEmpStreak, 'desc');
    expect(result).toEqual(false);
  });

  it('should return the total sum of all month in 3 month streak', () => {
    const streakScore = '1-1-1';
    const sum = component.totalOfQuarterlyScore(streakScore);
    expect(sum).toEqual(3);
  });
});
