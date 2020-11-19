import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';

import {UserDetailsModel} from '../../../models/user-details.model';

@Component({
    selector: 'app-user-management',
    templateUrl: './user-management.page.html',
    styleUrls: ['./user-management.page.scss'],
})
export class UserManagementPage implements OnInit {
    searchBar = new FormControl('');
    addUserForm = new FormGroup({
        name: new FormControl(null, Validators.required),
        emailId: new FormControl(null, [Validators.required, Validators.email]),
        empId: new FormControl(null, Validators.required),
        wordpressId: new FormControl(null, Validators.required),
        role: new FormControl('regular'),
    });
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

    onAddUser() {
        console.log(this.addUserForm);
        if (this.addUserForm.status === 'INVALID') {
            this.formIsNotValid = true;
        } else {
            this.addUserForm.reset();
        }
    }

    controlUserFormVisibility(action) {
        switch (action) {
            case 'close':
                this.addUserFormVisibility = false;
                this.addUserForm.reset();
                break;
            case 'open':
                this.addUserFormVisibility = true;
                break;
        }
    }
}
