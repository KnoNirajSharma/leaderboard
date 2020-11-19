import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';

import {UserDetailsModel} from '../../../models/admin/user-management/user-details.model';

@Component({
    selector: 'app-user-management',
    templateUrl: './user-management.page.html',
    styleUrls: ['./user-management.page.scss'],
})
export class UserManagementPage implements OnInit {
    searchBar = new FormControl('');
    addUserFormVisibility = false;
    formIsNotValid = null;
    mockUserDetailList: UserDetailsModel[] = [
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

    onAddUser(event) {
        console.log(event);
    }

    controlUserFormVisibility(action) {
        switch (action) {
            case 'close':
                this.addUserFormVisibility = false;
                break;
            case 'open':
                this.addUserFormVisibility = true;
                break;
        }
    }
}
