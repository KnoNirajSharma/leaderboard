import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {ReactiveFormsModule} from '@angular/forms';
import {IonicModule} from '@ionic/angular';

import {ComponentsModule} from '../../../components/components.module';
import {AdminActionModel} from '../../../models/admin-action.model';
import {UserManagementPage} from './user-management.page';

describe('UserManagementPage', () => {
    let component: UserManagementPage;
    let fixture: ComponentFixture<UserManagementPage>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [UserManagementPage],
            imports: [IonicModule.forRoot(), ReactiveFormsModule, ComponentsModule],
        }).compileComponents();

        fixture = TestBed.createComponent(UserManagementPage);
        component = fixture.componentInstance;
        fixture.detectChanges();
    }));

    it('should do something on admin action', () => {
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
        component.onUserAction(adminActionEvent);
    });
});
