import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {IonicModule} from '@ionic/angular';

import {UserManagementTableRowComponent} from './user-management-table-row.component';

describe('UserManagementTableRowComponent', () => {
    let component: UserManagementTableRowComponent;
    let fixture: ComponentFixture<UserManagementTableRowComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [UserManagementTableRowComponent],
            imports: [IonicModule.forRoot()],
        }).compileComponents();

        fixture = TestBed.createComponent(UserManagementTableRowComponent);
        component = fixture.componentInstance;
    }));

    it('should set actionMenuList value', () => {
        spyOn(component, 'setActionMenuList').and.returnValue(['option1', 'option2']);
        component.ngOnInit();
        expect(component.actionsMenuList.length).toEqual(2);
    });

    it('should return actionMenu list with one item when user is not active', () => {
        component.userDetails = {emailId: '', name: '', accountActive: false, role: '', empId: 2, wordpressId: ''};
        expect(component.setActionMenuList()[0]).toEqual('Enable Account');
    });

    it('should return actionMenu list with remove admin option when user is admin', () => {
        component.userDetails = {emailId: '', name: '', accountActive: true, role: 'admin', empId: 2, wordpressId: ''};
        expect(component.setActionMenuList()[1]).toEqual('Remove Admin');
    });

    it('should return actionMenu list with disable account when user is regular', () => {
        component.userDetails = {emailId: '', name: '', accountActive: true, role: 'regular', empId: 2, wordpressId: ''};
        expect(component.setActionMenuList()[0]).toEqual('Disable Account');
    });

    it('should make dropDownMenu visibility false if action is close', () => {
        component.dropdownVisibility = true;
        component.controlActionsDropdown('close');
        expect(component.dropdownVisibility).toEqual(false);
    });

    it('should toggle dropDownMenu visibility if action is toggle', () => {
        component.dropdownVisibility = false;
        component.controlActionsDropdown('toggle');
        expect(component.dropdownVisibility).toEqual(true);
    });
});
