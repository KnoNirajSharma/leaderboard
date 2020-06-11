import {async, ComponentFixture, fakeAsync, flush, TestBed, tick} from '@angular/core/testing';
import {IonicModule} from '@ionic/angular';
import {TableComponent} from './table.component';
import {RouterTestingModule} from '@angular/router/testing';
import {FormsModule} from '@angular/forms';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {Component} from '@angular/core';
import {AuthorModel} from '../../models/author.model';
import {EmployeeFilterPipe} from '../../pipe/employee-filter.pipe';
import {NgxDatatableModule} from '@swimlane/ngx-datatable';
import {DetailsPage} from '../../pages/details/details.page';
import {Router} from '@angular/router';
import {Location} from '@angular/common';

describe('TableComponent', () => {
    let component: TableComponent;
    let fixture: ComponentFixture<ParentComponent>;
    let router: Router;
    let location: Location;
    const id = '2';
    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [TableComponent, ParentComponent, EmployeeFilterPipe, DetailsPage],
            imports: [HttpClientTestingModule, IonicModule.forRoot(), FormsModule,
                NgxDatatableModule, RouterTestingModule.withRoutes([{
                    path: 'details/:id',
                    component: DetailsPage
                }])],
        }).compileComponents();

        router = TestBed.get(Router);
        location = TestBed.get(Location);

        fixture = TestBed.createComponent(ParentComponent);
        component = fixture.debugElement.children[0].componentInstance;
        router.initialNavigation();
    }));

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it ('should open when clicked `onActivate` button', fakeAsync( () => {
        spyOn(component, 'onActivate');
        const button = fixture.debugElement.nativeElement.querySelector('ngx-datatable');
        fixture.detectChanges();
        button.click();
        tick();
        expect(component.onActivate).toBeTruthy('onActivate should now be true');
        fixture.destroy();
        flush();
    }));

    it('navigate to "details" takes you to /details', fakeAsync(() => {
        router.navigate(['/details', id]).then(() => {
            expect(location.path()).toBe('/details/2');
        });
        fixture.destroy();
        flush();
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
