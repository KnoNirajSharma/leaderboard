import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {DropdownMenuListComponent} from '../../../common/dropdown-menu-list/dropdown-menu-list.component';
import {DropdownWrapperComponent} from '../../../common/dropdown-wrapper/dropdown-wrapper.component';
import {UserManagementTableRowComponent} from './user-management-table-row.component';

describe('UserManagementTableRowComponent', () => {
    let component: UserManagementTableRowComponent;
    let fixture: ComponentFixture<UserManagementTableRowComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [UserManagementTableRowComponent, DropdownMenuListComponent, DropdownWrapperComponent],
        }).compileComponents();

        fixture = TestBed.createComponent(UserManagementTableRowComponent);
        component = fixture.componentInstance;
    }));

    it('should set actionMenuList value', () => {
        spyOn(component, 'setActionMenuList').and
            .returnValue([{value: 'option1'}, {value: 'option2'}]);
        component.ngOnInit();
        expect(component.actionsMenuList.length).toEqual(2);
    });

    it('should return actionMenu list with one item when user is not active', () => {
        component.userDetails = {emailId: '', name: '', accountActive: false, role: '', empId: 2, wordpressId: ''};
        expect(component.setActionMenuList()[0].value).toEqual('Enable Account');
    });

    it('should return actionMenu list with remove admin option when user is admin', () => {
        component.userDetails = {emailId: '', name: '', accountActive: true, role: 'admin', empId: 2, wordpressId: ''};
        expect(component.setActionMenuList()[1].value).toEqual('Remove Admin');
    });

    it('should return actionMenu list with disable account when user is regular', () => {
        component.userDetails = {emailId: '', name: '', accountActive: true, role: 'regular', empId: 2, wordpressId: ''};
        expect(component.setActionMenuList()[0].value).toEqual('Disable Account');
    });

    it('should emit actionForUserSelected with event and user details', () => {
        spyOn(component.userAction, 'emit');
        component.userDetails = {emailId: '', name: '', accountActive: true, role: 'regular', empId: 2, wordpressId: ''};
        component.onActionSelect('test');
        expect(component.userAction.emit).toHaveBeenCalledWith({action: 'test', userAccountDetails: component.userDetails});
    });
});
