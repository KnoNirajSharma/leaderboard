import {Component} from '@angular/core';
import {FormControl} from '@angular/forms';

import {AdminActionModel} from '../../../models/admin-action.model';
import {UserAccountDetailsModel} from '../../../models/user-account-details.model';

@Component({
    selector: 'app-user-management',
    templateUrl: './user-management.page.html',
})
export class UserManagementPage {
    searchBar = new FormControl('');
    addUserFormVisibility = false;
    mockUserDetailList: UserAccountDetailsModel[] = [
        {
            emailId: 'umang.goyal@knoldus.com',
            empId: 1220,
            name: 'umang Goyal',
            wordpressId: 'umang10',
            role: 'admin',
            accountActive: false,
        },
        {
            emailId: 'umang.goyal@knoldus.com',
            empId: 1220,
            name: 'rudar Goyal',
            wordpressId: 'umang10',
            role: 'admin',
            accountActive: false,
        },
        {
            emailId: 'umang.goyal@knoldus.com',
            empId: 1220,
            name: 'abhinav Goyal',
            wordpressId: 'umang10',
            role: 'admin',
            accountActive: true,
        },
        {
            emailId: 'umang.goyal@knoldus.com',
            empId: 1220,
            name: 'vikas Goyal',
            wordpressId: 'umang10',
            role: 'admin',
            accountActive: true,
        },
    ];

    onUserAction(event: AdminActionModel) {
        console.log(event);
    }

    onAddUser(event) {
        console.log(event);
    }

    controlUserFormVisibility(visibilityStatus: boolean) {
        this.addUserFormVisibility = visibilityStatus;
    }
}
