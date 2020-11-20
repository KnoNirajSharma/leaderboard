import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {IonicModule} from '@ionic/angular';

import {AdminActionModel} from '../../../models/admin-action.model';
import {CustomPipesModule} from '../../../pipe/custom-pipes.module';
import {ComponentsModule} from '../../components.module';
import {UserManagementTableComponent} from './user-management-table.component';

describe('UserManagementTableComponent', () => {
    let component: UserManagementTableComponent;
    let fixture: ComponentFixture<UserManagementTableComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [],
            imports: [IonicModule.forRoot(), CustomPipesModule, ComponentsModule],
        }).compileComponents();

        fixture = TestBed.createComponent(UserManagementTableComponent);
        component = fixture.componentInstance;
    }));

    it('should emit adminActionTaken with event details', () => {
        spyOn(component.adminActionTaken, 'emit');
        const adminActionEvent: AdminActionModel = {
            action: 'test',
            userAccountDetails: {
                name: 'test test',
                emailId: 'test@test.com',
                empId: 123,
                wordpressId: 'testid',
                role: 'admin',
                accountActive: true,
            },
        };
        component.onActionForUserSelected(adminActionEvent);
        expect(component.adminActionTaken.emit).toHaveBeenCalledWith(adminActionEvent);
    });
});
