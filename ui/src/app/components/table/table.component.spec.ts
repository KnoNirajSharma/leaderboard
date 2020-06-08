import {async, ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';
import {IonicModule, ModalController} from '@ionic/angular';
import {TableComponent} from './table.component';
import {RouterTestingModule} from '@angular/router/testing';
import {FormsModule} from '@angular/forms';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {Component} from '@angular/core';
import {AuthorModel} from '../../models/author.model';
import {EmployeeFilterPipe} from '../../pipe/employee-filter.pipe';
import {NgxDatatableModule} from '@swimlane/ngx-datatable';

describe('TableComponent', () => {
    let component: TableComponent;
    let fixture: ComponentFixture<ParentComponent>;
    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [TableComponent, ParentComponent, EmployeeFilterPipe],
            imports: [HttpClientTestingModule, IonicModule.forRoot(), RouterTestingModule, FormsModule, NgxDatatableModule],
            providers: [ModalController]
        }).compileComponents();

        fixture = TestBed.createComponent(ParentComponent);
        component = fixture.debugElement.children[0].componentInstance;
        fixture.detectChanges();
    }));

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it ('should open when clicked `presentModal` button', fakeAsync( () => {
        spyOn(component, 'presentModal');
        const button = fixture.debugElement.nativeElement.querySelector('ngx-datatable');
        fixture.detectChanges();
        button.click();
        tick();
        expect(component.presentModal).toBeTruthy('presentModal should now be true');
    }));
});
@Component({
    selector: 'parent',
    template: '<app-table [dataKeys]="dataKeys" [tableRows]="employeeData"></app-table>'
})
class ParentComponent {
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
