import {Component, OnInit} from '@angular/core';
import {FormControl} from '@angular/forms';

import {AdminActionModel} from '../../../models/admin-action.model';
import {UserAccountDetailsModel} from '../../../models/user-account-details.model';

@Component({
    selector: 'app-user-management',
    templateUrl: './user-management.page.html',
    styleUrls: ['./user-management.page.scss'],
})
export class UserManagementPage implements OnInit {
    searchBar = new FormControl('');
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

    constructor() {
    }

    ngOnInit() {
    }

    onUserAction(event: AdminActionModel) {
        console.log(event);
    }

}
