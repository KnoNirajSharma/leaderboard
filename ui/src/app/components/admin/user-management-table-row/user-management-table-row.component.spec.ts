import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {IonicModule} from '@ionic/angular';

import {UserManagementTableRowComponent} from './user-management-table-row.component';

describe('UserManagementTableRowComponent', () => {
    let component: UserManagementTableRowComponent;
    let fixture: ComponentFixture<UserManagementTableRowComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [UserManagementTableRowComponent],
            imports: [IonicModule.forRoot()]
        }).compileComponents();

        fixture = TestBed.createComponent(UserManagementTableRowComponent);
        component = fixture.componentInstance;
    }));

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
