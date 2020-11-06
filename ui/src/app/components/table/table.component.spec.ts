import { Location } from '@angular/common';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { IonicModule } from '@ionic/angular';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';

import { AuthorModel } from '../../models/author.model';
import { TableComponent } from './table.component';

describe('TableComponent', () => {
  let component: TableComponent;
  let fixture: ComponentFixture<TableComponent>;
  let router: Router;
  let location: Location;
  const routerSpy = jasmine.createSpyObj('Router', ['navigate']);
  const dummyKnolderList: AuthorModel[] = [
      {
        knolderId: 1,
        knolderName: 'mark',
        allTimeScore: 10,
        allTimeRank: 7,
        quarterlyStreak: '5-6-7',
        monthlyScore: 10,
        monthlyRank: 1,
      }, {
        knolderId: 2,
        knolderName: 'sam',
        allTimeScore: 20,
        allTimeRank: 6,
        quarterlyStreak: '5-6-8',
        monthlyScore: 10,
        monthlyRank: 1,
      },
    ];

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        TableComponent,
      ],
      imports: [
        IonicModule.forRoot(),
        NgxDatatableModule,
        RouterTestingModule,
      ],
      providers: [{ provide: Router, useValue: routerSpy }],
    }).compileComponents();

    router = TestBed.get(Router);
    location = TestBed.get(Location);

    fixture = TestBed.createComponent(TableComponent);
    component = fixture.componentInstance;
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should change route on click of row', () => {
    component.currentDate.set({ year: 2020, month: 0 });
    const event = { type: 'click', row: { knolderId: 2 } };
    component.onActivate(event);
    expect(routerSpy.navigate).toHaveBeenCalledWith(
        ['/details'],
        { queryParams: { id : event.row.knolderId, year: 2020, month: 'January' } },
      );
  });

  it('should compare on the allTimeRank property if values are not equal', () => {
    const firstEmp = dummyKnolderList[1];
    const secEmp = dummyKnolderList[0];
    expect(component.comparisonBasedOnAllTimeScore(firstEmp, secEmp, 'allTimeRank')).toEqual(false);
  });

  it('should compare on allTimeScore if values are equal', () => {
    const firstEmp = dummyKnolderList[1];
    const secEmp = dummyKnolderList[0];
    expect(component.comparisonBasedOnAllTimeScore(firstEmp, secEmp, 'monthlyScore')).toEqual(false);
  });

  it('should return the total sum of all month in 3 month streak', () => {
    const streakScore = '1-1-1';
    expect(component.totalOfQuarterlyScore(streakScore)).toEqual(3);
  });

  it('should return true or false depending on if the quarterly total score are in ascending order', () => {
    const firstEmpStreak = dummyKnolderList[0].quarterlyStreak;
    const secEmpStreak = dummyKnolderList[1].quarterlyStreak;
    expect(component.compareQuarterlyScore(firstEmpStreak, secEmpStreak, 'asc')).toEqual(true);
  });

  it('should return true or false depending on if the quarterly total score are in descending order', () => {
    const firstEmpStreak = dummyKnolderList[0].quarterlyStreak;
    const secEmpStreak = dummyKnolderList[1].quarterlyStreak;
    expect(component.compareQuarterlyScore(firstEmpStreak, secEmpStreak, 'desc')).toEqual(false);
  });

  it('should sort list in asc on the basis of quarterly score', () => {
    component.tableRows = [...dummyKnolderList];
    spyOn(component, 'compareQuarterlyScore').and.returnValue(true);
    component.onSort({newValue: 'asc', column: {prop: 'quarterlyStreak'}});
    expect(component.tableRows[0].knolderId).toEqual(1);
  });

  it('should sort list in desc on the basis of quarterly score', () => {
    component.tableRows = [...dummyKnolderList];
    spyOn(component, 'compareQuarterlyScore').and.returnValue(false);
    component.onSort({newValue: 'asc', column: {prop: 'quarterlyStreak'}});
    expect(component.tableRows[0].knolderId).toEqual(2);
  });

  it('should sort the list in descending order on the basis of allTimeRank', () => {
    component.tableRows = [...dummyKnolderList];
    component.onSort({newValue: 'desc', column: {prop: 'allTimeRank'}});
    expect(component.tableRows[0].knolderId).toEqual(1);
  });

  it('should sort the list in descending order on the basis of allTimeScore', () => {
    component.tableRows = [...dummyKnolderList];
    component.onSort({newValue: 'desc', column: {prop: 'allTimeScore'}});
    expect(component.tableRows[0].knolderId).toEqual(2);
  });

  it('should sort the list in ascending order on the basis of monthlyScore', () => {
    component.tableRows = [...dummyKnolderList];
    spyOn(component, 'comparisonBasedOnAllTimeScore').and.returnValue(false);
    component.onSort({newValue: 'asc', column: {prop: 'monthlyScore'}});
    expect(component.tableRows[0].knolderId).toEqual(2);
  });

  it('should sort the list in ascending order on the basis of allTimeScore', () => {
    component.tableRows = [...dummyKnolderList];
    spyOn(component, 'comparisonBasedOnAllTimeScore').and.returnValue(true);
    component.onSort({newValue: 'asc', column: {prop: 'allTimeScore'}});
    expect(component.tableRows[0].knolderId).toEqual(1);
  });
});
