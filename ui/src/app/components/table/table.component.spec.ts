import {async, ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';
import {IonicModule, ModalController} from '@ionic/angular';
import {TableComponent} from './table.component';
import {RouterTestingModule} from '@angular/router/testing';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {Component} from '@angular/core';
import {TableHeaderModel} from '../../models/tableHeader.model';
import {AuthorModel} from '../../models/author.model';
import {EmployeeFilterPipe} from '../../pipe/employee-filter.pipe';

describe('TableComponent', () => {
    let component: TableComponent;
    let fixture: ComponentFixture<ParentComponent>;
    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [TableComponent, ParentComponent, EmployeeFilterPipe],
            imports: [HttpClientTestingModule, IonicModule.forRoot(), RouterTestingModule, FormsModule]
        }).compileComponents();

        fixture = TestBed.createComponent(ParentComponent);
        component = fixture.debugElement.children[0].componentInstance;
        fixture.detectChanges();
    }));

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
@Component({
    selector: 'parent',
    template: '<app-table [dataKeys]="dataKeys" [tableHeaders]="tableHeaders" [tableRows]="employeeData"></app-table>'
})
class ParentComponent {
    tableHeaders: TableHeaderModel[] = [
        {title: 'Name'},
        {title: 'Score'},
        {title: 'Rank'},
        {title: '3 Month Streak'},
        {title: 'Monthly Score'},
        {title: 'Monthly Rank'}
    ];
    employeeData: AuthorModel[] = [
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
            allTimeScore: 10,
            allTimeRank: 2,
            quarterlyStreak: '5-6-7',
            monthlyScore: 7,
            monthlyRank: 1
        }
    ];
    dataKeys = (Object.keys(this.employeeData[0])).slice(1, 7);
}
