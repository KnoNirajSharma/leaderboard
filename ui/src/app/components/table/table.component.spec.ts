import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {IonicModule} from '@ionic/angular';
import {TableComponent} from './table.component';
import {RouterTestingModule} from '@angular/router/testing';
import {FormsModule} from '@angular/forms';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {Component} from '@angular/core';
import {TableHeaderModel} from '../../models/tableHeader.model';
import {AuthorModel} from '../../models/author.model';

describe('TableComponent', () => {
    let component: TableComponent;
    let fixture: ComponentFixture<ParentComponent>;
    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [TableComponent, ParentComponent],
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
        {title: 'Author Name'},
        {title: 'Score'},
        {title: 'Rank'},
    ];
    employeeData: AuthorModel[] = [
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
    dataKeys = Object.keys(this.employeeData[0]);
}
