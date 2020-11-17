import {Component, Input, OnInit} from '@angular/core';

import {UserDetailsModel} from '../../../models/user-details.model';

@Component({
    selector: 'app-user-management-table-row',
    templateUrl: './user-management-table-row.component.html',
    styleUrls: ['./user-management-table-row.component.scss'],
})
export class UserManagementTableRowComponent implements OnInit {
    @Input() userDetails: UserDetailsModel;
    actions = [];

    ngOnInit() {
        this.setAction();
    }

    setAction() {
        if (!this.userDetails.accountActive) {
            this.actions = ['Enable Account'];
        } else if (this.userDetails.role === 'admin') {
            this.actions = ['Disable Account', 'Remove Admin'];
        } else {
            this.actions = ['Disable Account', 'Make Admin'];
        }
    }
}
